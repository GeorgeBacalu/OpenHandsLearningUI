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

    @Test public void onlyLogosAreBundledAndEverySignHasAnS3Object() throws Exception {
        var resources = Path.of("src/main/res");
        try (var files = Files.list(resources.resolve("drawable-nodpi"))) {
            assertEquals(Set.of("openhandslearning_mark.png", "openhandslearning_logo_transparent.png"),
                    files.map(path -> path.getFileName().toString()).collect(Collectors.toSet()));
        }
        try (var files = Files.list(resources.resolve("raw"))) {
            assertEquals(Set.of("splash.json"), files.map(path -> path.getFileName().toString()).collect(Collectors.toSet()));
        }
        var factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        try (var layouts = Files.list(resources.resolve("layout"))) {
            for (var layout : layouts.toList()) {
                var views = factory.newDocumentBuilder().parse(layout.toFile())
                        .getElementsByTagName("com.project.ohl.presentation.S3ImageView");
                for (int index = 0; index < views.getLength(); index++) {
                    String name = views.item(index).getAttributes().getNamedItem("app:mediaName").getNodeValue();
                    assertTrue("Static S3 artwork exists: " + name,
                            com.project.ohl.presentation.MediaCatalog.IMAGES.containsKey(name));
                }
            }
        }
        assertEquals(251, com.project.ohl.presentation.MediaCatalog.IMAGES.size());
        assertEquals(184, com.project.ohl.presentation.MediaCatalog.VIDEOS.size());
        for (String key : SignText.dictionary().values()) {
            String url = MediaResources.voiceUrl(key);
            assertTrue(url != null && url.contains("/voice/") && url.endsWith(".webp"));
        }
        assertTrue(MediaResources.videoUrl("ă").endsWith("/alfabet/a_breve.mp4"));
        assertTrue(MediaResources.imageUrl("a").endsWith("/alfabet/a.webp"));
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
