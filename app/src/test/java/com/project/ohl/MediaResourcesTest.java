package com.project.ohl;

import com.project.ohl.presentation.MediaResources;
import com.project.ohl.domain.model.SignText;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MediaResourcesTest {
    @Test public void resolvesRomanianDiacriticsAndSeparatorsIndependentlyOfDeviceLocale() {
        var original = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            assertEquals("sta_jos", MediaResources.resourceName("Stă jos"));
            assertEquals("scoala_tara", MediaResources.resourceName("Școală/Țară"));
            assertEquals("indurerat", MediaResources.resourceName("ÎNDURERAT"));
        } finally { Locale.setDefault(original); }
    }

    @Test public void mediaCatalogsCoverEveryPackagedImageAndVideo() throws Exception {
        var resources = Path.of("src/main/res");
        var factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        var arrays = factory.newDocumentBuilder().parse(resources.resolve("values/media_catalog.xml").toFile())
                .getElementsByTagName("array");
        assertEquals(3, arrays.getLength());
        var allDrawables = new HashSet<String>();
        var catalogs = new java.util.HashMap<String, Set<String>>();
        for (int index = 0; index < arrays.getLength(); index++) {
            var array = arrays.item(index);
            String name = array.getAttributes().getNamedItem("name").getNodeValue();
            String kind = name.equals("catalog_videos") ? "raw" : "drawable";
            var actual = new HashSet<String>();
            for (var item = array.getFirstChild(); item != null; item = item.getNextSibling()) {
                if (item.getNodeName().equals("item"))
                    assertTrue(actual.add(item.getTextContent().strip().replace("@" + kind + "/", "")));
            }
            assertTrue(catalogs.put(name, actual) == null);
            if (kind.equals("drawable")) {
                for (String image : actual)
                    assertTrue("Voice and category catalogs must not share " + image, allDrawables.add(image));
            } else {
                assertEquals(packagedNames(resources, "raw"), actual);
            }
        }
        assertEquals(packagedNames(resources, "drawable"), allDrawables);
        assertEquals(Set.of("catalog_drawables", "catalog_voice_signs", "catalog_videos"), catalogs.keySet());
        var voiceKeys = catalogs.get("catalog_voice_signs").stream()
                .map(name -> name.startsWith("voice_") ? name.substring("voice_".length()) : name)
                .collect(Collectors.toSet());
        assertEquals(new HashSet<>(SignText.dictionary().values()), voiceKeys);
        assertEquals(voiceKeys.size(), catalogs.get("catalog_voice_signs").size());
    }

    private static Set<String> packagedNames(Path resources, String kind) throws Exception {
        try (var files = Files.walk(resources, 2)) {
            return files.filter(Files::isRegularFile)
                    .filter(path -> path.getParent().getFileName().toString().split("-")[0].equals(kind))
                    .map(path -> path.getFileName().toString().split("\\.")[0]).collect(Collectors.toSet());
        }
    }

    @Test public void preservesABreveWhenResolvingAndroidMediaNames() {
        assertEquals("a", MediaResources.resourceName("a"));
        assertEquals("a_breve", MediaResources.resourceName("ă"));
        assertEquals("a_breve", MediaResources.resourceName("A\u0306"));
        assertEquals("a_breve", MediaResources.resourceName("a_breve"));
    }

    @Test public void preservesOtherRomanianDactylsBeforeRemovingAccents() {
        var aliases = java.util.Map.of("â", "a_circumflex", "î", "i_circumflex",
                "ș", "s_comma", "ş", "s_comma", "ț", "t_comma", "ţ", "t_comma");
        aliases.forEach((letter, suffix) -> {
            String key = suffix;
            assertEquals(key, MediaResources.resourceName(letter));
            assertEquals(key, MediaResources.resourceName(java.text.Normalizer.normalize(
                    letter.toUpperCase(Locale.ROOT), java.text.Normalizer.Form.NFD)));
            assertEquals(key, MediaResources.resourceName(key));
        });
    }
}
