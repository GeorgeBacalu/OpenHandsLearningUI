package com.project.ohl;

import com.project.ohl.domain.model.SignText;
import com.project.ohl.presentation.MediaCatalog;
import com.project.ohl.presentation.MediaResources;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;

import static java.util.Objects.requireNonNull;
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
        if (Files.isDirectory(resources.resolve("raw"))) {
            try (var files = Files.list(resources.resolve("raw"))) {
                assertEquals(Set.of(), files.map(path -> path.getFileName().toString()).collect(Collectors.toSet()));
            }
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
                            MediaCatalog.IMAGES.containsKey(name));
                }
            }
        }
        assertEquals(47, MediaCatalog.IMAGES.size());
        assertTrue(requireNonNull(MediaResources.imageUrl("overlay")).endsWith("/categories/overlay.webp"));
        assertTrue(MediaCatalog.IMAGES.values().stream().noneMatch(path -> path.startsWith("categories/application/")));
        MediaCatalog.IMAGES.values().stream().filter(path -> path.startsWith("categories/") && !path.equals("categories/header.webp") && !path.equals("categories/overlay.webp"))
                .forEach(path -> assertTrue(path, path.endsWith("/main.webp")));
        for (var sign : SignText.translate("ABCDEFGHIJKLMNOPQRSTUVWXYZĂÂÎȘȚ ")) {
            String url = MediaResources.voiceUrl(sign.mediaKey());
            assertTrue(url != null && url.contains("/voice/") && url.endsWith(".webp"));
        }
        assertTrue(requireNonNull(MediaResources.voiceUrl("a")).endsWith("/voice/a.webp"));
        assertTrue(requireNonNull(MediaResources.voiceUrl("ă")).endsWith("/voice/a_breve.webp"));
        assertTrue(requireNonNull(MediaResources.imageUrl("video_section")).endsWith("/categories/main.webp"));
        assertTrue(requireNonNull(MediaResources.imageUrl("video_section_header")).endsWith("/categories/header.webp"));
        assertTrue(requireNonNull(MediaResources.imageUrl("voice_to_sign_section")).endsWith("/voice/main.webp"));
        assertTrue(requireNonNull(MediaResources.imageUrl("voice_to_sign_section_header")).endsWith("/voice/header.webp"));
        assertTrue(MediaCatalog.IMAGES.values().stream().noneMatch(path -> path.startsWith("voice/voice_")));
    }

    @Test public void preservesABreveWhenResolvingAndroidMediaNames() {
        assertEquals("a", MediaResources.resourceName("a"));
        assertEquals("a_breve", MediaResources.resourceName("ă"));
        assertEquals("a_breve", MediaResources.resourceName("A\u0306"));
        assertEquals("a_breve", MediaResources.resourceName("a_breve"));
    }

    @Test public void preservesOtherRomanianDactylsBeforeRemovingAccents() {
        var aliases = Map.of("â", "a_circumflex", "î", "i_circumflex",
                "ș", "s_comma", "ş", "s_comma", "ț", "t_comma", "ţ", "t_comma");
        aliases.forEach((letter, suffix) -> {
                        assertEquals(suffix, MediaResources.resourceName(letter));
            assertEquals(suffix, MediaResources.resourceName(Normalizer.normalize(
                    letter.toUpperCase(Locale.ROOT), Normalizer.Form.NFD)));
            assertEquals(suffix, MediaResources.resourceName(suffix));
        });
    }
}
