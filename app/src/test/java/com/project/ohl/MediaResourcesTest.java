package com.project.ohl;

import com.project.ohl.presentation.MediaResources;
import java.util.Locale;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

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
}
