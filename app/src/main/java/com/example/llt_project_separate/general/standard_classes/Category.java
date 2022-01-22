package com.example.llt_project_separate.general.standard_classes;

public class Category {
    private int id = 0;
    private String name;
    private int imageSource;
    private boolean isExpanded;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        this.isExpanded = false;
    }

    public Category(int id, String name, int imageSource) {
        this.id = id;
        this.name = name;
        this.imageSource = imageSource;
        this.isExpanded = false;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getImageSource() { return imageSource; }

    public void setImageSource(int imageSource) { this.imageSource = imageSource; }

    public boolean isExpanded() { return isExpanded; }

    public void setExpanded(boolean expanded) { isExpanded = expanded; }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name='" + name + '\'' + ", imageSource=" + imageSource + ", isExpanded=" + isExpanded + '}';
    }
}
