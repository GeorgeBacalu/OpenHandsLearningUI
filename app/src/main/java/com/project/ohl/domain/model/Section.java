package com.project.ohl.domain.model;

public class Section {

    private SectionType type;
    private String mediaKey;
    private String iconKey;
    private String name;
    private transient int icon;
    private transient int imageSource;

    public Section(SectionType type, String name, int icon, int imageSource) {
        this.type = type;
        this.name = name;
        this.icon = icon;
        this.imageSource = imageSource;
    }

    public SectionType getType() {
        return type;
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
        return "Section{" + "type=" + type + ", name='" + name + '\'' + ", icon=" + icon + ", imageSource=" + imageSource + '}';
    }

    public String getMediaKey() { return mediaKey; }
    public void setMediaKey(String value) { mediaKey = value; }

    public String getIconKey() { return iconKey; }
    public void setIconKey(String value) { iconKey = value; }

}
