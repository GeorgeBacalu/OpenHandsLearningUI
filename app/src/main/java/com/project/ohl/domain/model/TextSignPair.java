package com.project.ohl.domain.model;

import java.util.UUID;

public class TextSignPair {

    private UUID id;
    private String letter;
    private String mediaKey;
    private transient int imageSource;

    public TextSignPair(String letter, int imageSource) {
        this.letter = letter;
        this.imageSource = imageSource;
    }

    public TextSignPair(UUID id, String letter, int imageSource) {
        this.id = id;
        this.letter = letter;
        this.imageSource = imageSource;
    }

    public String getMediaKey() { return mediaKey; }

    public void setMediaKey(String mediaKey) { this.mediaKey = mediaKey; }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLetter() { return letter; }

    public void setLetter(String letter) { this.letter = letter; }

    public int getImageSource() { return imageSource; }

    public void setImageSource(int imageSource) { this.imageSource = imageSource; }

    @Override
    public String toString() {
        return "TextSignPair{" +
             "id=" + id +
             ", letter='" + letter + '\'' +
             ", imageSource=" + imageSource +
             '}';
    }
}
