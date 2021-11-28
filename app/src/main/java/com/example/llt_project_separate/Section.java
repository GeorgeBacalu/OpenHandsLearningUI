package com.example.llt_project_separate;

public class Section {
    private int id;
    private String name;
    private int icon;
    private int imageSource;

    public Section(int id, String name, int icon, int imageSource) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.imageSource = imageSource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon=" + icon +
                ", imageSource=" + imageSource +
                '}';
    }
}
