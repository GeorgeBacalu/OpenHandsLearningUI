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
        assertEquals(List.of("a", "a_breve", "a_circumflex", "i_circumflex",
                "s_comma", "t_comma", "space", "s_comma", "t_comma"),
                signs.stream().map(TextSignPair::getMediaKey).toList());
    }

    @Test public void handlesCanonicalUnicodeAndWhitespace() {
        assertEquals(List.of("a_breve", "space", "s_comma", "space", "t_comma"),
                SignText.translate("a\u0306 s\u0326\tţ").stream().map(TextSignPair::getMediaKey).toList());
        assertEquals(List.of("a", "space", "space", "b"),
                SignText.translate("a \nb").stream().map(TextSignPair::getMediaKey).toList());
    }

    @Test public void isIndependentOfTheDeviceLocale() {
        Locale original = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            assertEquals(List.of("i", "i", "i_circumflex"),
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
        assertEquals(32, new java.util.HashSet<>(SignText.dictionary().values()).size());
        assertThrows(UnsupportedOperationException.class, () -> SignText.dictionary().put((int) 'A', "changed"));
        assertEquals("a_breve", SignText.dictionary().get((int) 'Ă'));
    }

    @Test public void conversionResultsDoNotShareMutableDisplayModels() {
        var first = SignText.translate("a");
        first.clear();
        assertEquals("a", SignText.translate("a").get(0).getMediaKey());
    }

    @Test public void distinguishesAAndABreveInBothCasesAndUnicodeForms() {
        var signs = SignText.translate("AaĂăA\u0306a\u0306");
        assertEquals(List.of("A", "a", "Ă", "ă", "Ă", "ă"),
                signs.stream().map(TextSignPair::getLetter).toList());
        assertEquals(List.of("a", "a", "a_breve",
                "a_breve", "a_breve", "a_breve"),
                signs.stream().map(TextSignPair::getMediaKey).toList());
    }

    @Test public void preservesDistinctRomanianDactylsInCanonicalAndLegacySpellings() {
        var expected = List.of("a_circumflex", "i_circumflex",
                "s_comma", "t_comma");
        for (String text : List.of("ÂÎȘȚ", "âîșț", "A\u0302I\u0302S\u0326T\u0326", "âîŞŢ", "âîşţ")) {
            assertEquals(expected, SignText.translate(text).stream().map(TextSignPair::getMediaKey).toList());
        }
        assertEquals(List.of("a", "i", "s", "t"),
                SignText.translate("AIST").stream().map(TextSignPair::getMediaKey).toList());
    }
}
