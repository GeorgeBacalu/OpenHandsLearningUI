package com.project.ohl.domain.model;

import java.util.UUID;

public class Section {

    private UUID id;
    private String code;
    private String mediaKey;
    private String iconKey;
    private String name;
    private int icon;
    private int imageSource;

    public Section(UUID id, String name, int icon, int imageSource) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.imageSource = imageSource;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
        return "Section{" + "id=" + id + ", name='" + name + '\'' + ", icon=" + icon + ", imageSource=" + imageSource + '}';
    }
    public String getCode() { return code; }
    public void setCode(String value) { code = value; }

    public String getMediaKey() { return mediaKey; }
    public void setMediaKey(String value) { mediaKey = value; }

    public String getIconKey() { return iconKey; }
    public void setIconKey(String value) { iconKey = value; }

}
