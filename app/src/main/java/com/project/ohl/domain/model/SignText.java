package com.project.ohl.domain.model;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SignText {
    private static final Map<Integer, String> DICTIONARY = createDictionary();

    private SignText() {}

    public static Map<Integer, String> dictionary() {
        return DICTIONARY;
    }

    private static Map<Integer, String> createDictionary() {
        var signs = new HashMap<Integer, String>();
        for (char letter = 'A'; letter <= 'Z'; letter++)
            signs.put((int) letter, "dactilema_" + Character.toLowerCase(letter));
        signs.put((int) 'Ă', "dactilema_a");
        signs.put((int) 'Â', "dactilema_a");
        signs.put((int) 'Î', "dactilema_i");
        signs.put((int) 'Ș', "dactilema_s");
        signs.put((int) 'Ț', "dactilema_t");
        signs.put((int) ' ', "space");
        return Map.copyOf(signs);
    }

    public static List<TextSignPair> translate(String text) {
        var result = new ArrayList<TextSignPair>();
        Normalizer.normalize(text, Normalizer.Form.NFC).codePoints().forEach(value -> {
            int letter = Character.isWhitespace(value) ? ' ' : Character.toUpperCase(value);
            letter = switch (letter) {
                case 'Ş' -> 'Ș';
                case 'Ţ' -> 'Ț';
                default -> letter;
            };
            String mediaKey = DICTIONARY.get(letter);
            if (mediaKey != null)
                result.add(new TextSignPair(new String(Character.toChars(value)), mediaKey));
        });
        return result;
    }
}
