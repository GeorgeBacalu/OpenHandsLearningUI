package com.project.ohl.domain.model;

public final class TextSignPair {
    private final String letter;
    private final String mediaKey;

    public TextSignPair(String letter, String mediaKey) {
        this.letter = letter;
        this.mediaKey = mediaKey;
    }

    public String getLetter() { return letter; }
    public String getMediaKey() { return mediaKey; }
}
