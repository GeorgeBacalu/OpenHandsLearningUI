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
        assertEquals(2, arrays.getLength());
        for (int index = 0; index < arrays.getLength(); index++) {
            var array = arrays.item(index);
            String kind = array.getAttributes().getNamedItem("name").getNodeValue()
                    .equals("catalog_drawables") ? "drawable" : "raw";
            var actual = new HashSet<String>();
            for (var item = array.getFirstChild(); item != null; item = item.getNextSibling()) {
                if (item.getNodeName().equals("item"))
                    assertTrue(actual.add(item.getTextContent().strip().replace("@" + kind + "/", "")));
            }
            Set<String> expected;
            try (var files = Files.walk(resources, 2)) {
                expected = files.filter(Files::isRegularFile)
                        .filter(path -> path.getParent().getFileName().toString().split("-")[0].equals(kind))
                        .map(path -> path.getFileName().toString().split("\\.")[0]).collect(Collectors.toSet());
            }
            assertEquals(expected, actual);
            if (kind.equals("drawable")) assertTrue(actual.containsAll(SignText.dictionary().values()));
        }
    }
}
