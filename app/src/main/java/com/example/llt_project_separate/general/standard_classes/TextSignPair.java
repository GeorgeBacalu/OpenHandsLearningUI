package com.example.llt_project_separate.general.standard_classes;

public class TextSignPair {
    private String letter;
    private int imageSource;

    public TextSignPair(String letter, int imageSource) {
        this.letter = letter;
        this.imageSource = imageSource;
    }

    public String getLetter() { return letter; }

    public void setLetter(String letter) { this.letter = letter; }

    public int getImageSource() { return imageSource; }

    public void setImageSource(int imageSource) { this.imageSource = imageSource; }

    @Override
    public String toString() {
        return "TextSignPair{" + "letter='" + letter + '\'' + ", imageSource=" + imageSource + '}';
    }
}
