package com.example.llt_project_separate.general.standard_classes;

public class TextSignPair {

    private Long id;
    private String letter;
    private int imageSource;

    public TextSignPair(String letter, int imageSource) {
        this.letter = letter;
        this.imageSource = imageSource;
    }

    public TextSignPair(Long id, String letter, int imageSource) {
        this.id = id;
        this.letter = letter;
        this.imageSource = imageSource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
