package com.project.ohl;

import com.project.ohl.domain.model.SignText;
import com.project.ohl.domain.model.TextSignPair;
import java.util.List;
import java.util.Locale;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class SignTextTest {
    @Test public void preservesCaseRomanianAliasesAndSpaces() {
        var signs = SignText.translate("aĂâÎșț ŞŢ");
        assertEquals(List.of("dactilema_a", "dactilema_a", "dactilema_a", "dactilema_i",
                "dactilema_s", "dactilema_t", "space", "dactilema_s", "dactilema_t"),
                signs.stream().map(TextSignPair::getMediaKey).toList());
    }

    @Test public void handlesCanonicalUnicodeAndWhitespace() {
        assertEquals(List.of("dactilema_a", "space", "dactilema_s", "space", "dactilema_t"),
                SignText.translate("a\u0306 s\u0326\tţ").stream().map(TextSignPair::getMediaKey).toList());
        assertEquals(List.of("dactilema_a", "space", "space", "dactilema_b"),
                SignText.translate("a \nb").stream().map(TextSignPair::getMediaKey).toList());
    }

    @Test public void isIndependentOfTheDeviceLocale() {
        Locale original = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            assertEquals(List.of("dactilema_i", "dactilema_i", "dactilema_i"),
                    SignText.translate("iIî").stream().map(TextSignPair::getMediaKey).toList());
        } finally {
            Locale.setDefault(original);
        }
    }

    @Test public void handlesEmptyAndUnsupportedCodePoints() {
        assertTrue(SignText.translate("").isEmpty());
        assertTrue(SignText.translate("0123!?🙂").isEmpty());
        assertEquals(List.of("a", "b"), SignText.translate("a🙂b").stream().map(TextSignPair::getLetter).toList());
    }

    @Test public void buildsOneImmutableDictionaryWithoutCaseDuplicates() {
        assertSame(SignText.dictionary(), SignText.dictionary());
        assertEquals(32, SignText.dictionary().size());
        assertThrows(UnsupportedOperationException.class, () -> SignText.dictionary().put((int) 'A', "changed"));
        assertEquals("dactilema_a", SignText.dictionary().get((int) 'Ă'));
    }

    @Test public void conversionResultsDoNotShareMutableDisplayModels() {
        var first = SignText.translate("a");
        first.get(0).setImageSource(42);
        assertEquals(0, SignText.translate("a").get(0).getImageSource());
    }
}
