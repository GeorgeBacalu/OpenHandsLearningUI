package com.project.ohl;

import com.project.ohl.domain.model.SignText;
import com.project.ohl.domain.model.TextSignPair;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SignTextTest {
    @Test public void resolvesCaseDiacriticsAndSpacesWithoutDuplicateCatalogRows() {
        var catalog = List.of(new TextSignPair("A", 1), new TextSignPair("Ă", 2),
                new TextSignPair("ă", 3), new TextSignPair("Ș", 4), new TextSignPair(" ", 5));
        var signs = SignText.translate("aĂă ş", catalog);
        assertEquals(List.of(1, 2, 3, 5, 4), signs.stream().map(TextSignPair::getImageSource).toList());
        assertEquals("aĂă ş".replace('ş', 'ș'),
                signs.stream().map(TextSignPair::getLetter).reduce("", String::concat));
    }

    @Test public void handlesEmptyAndUnsupportedInputWithoutCrashing() {
        assertTrue(SignText.translate("", List.of()).isEmpty());
        assertTrue(SignText.translate("🙂", List.of(new TextSignPair("A", 1))).isEmpty());
    }
}
