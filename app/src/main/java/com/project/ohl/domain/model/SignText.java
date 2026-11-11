package com.project.ohl.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public final class SignText {
    private SignText() {}

    public static List<TextSignPair> translate(String text, List<TextSignPair> catalog) {
        var byLetter = new HashMap<String, TextSignPair>();
        for (TextSignPair sign : catalog) byLetter.putIfAbsent(sign.getLetter(), sign);
        var result = new ArrayList<TextSignPair>();
        text.codePoints().forEach(value -> {
            String letter = new String(Character.toChars(value)).replace('ş', 'ș').replace('ţ', 'ț')
                    .replace('Ş', 'Ș').replace('Ţ', 'Ț');
            var sign = byLetter.get(letter);
            if (sign == null) sign = byLetter.get(letter.toUpperCase(Locale.ROOT));
            if (sign != null) result.add(new TextSignPair(letter, sign.getImageSource()));
        });
        return result;
    }
}
