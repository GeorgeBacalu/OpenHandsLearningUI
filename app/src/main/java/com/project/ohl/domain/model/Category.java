package com.project.ohl.domain.model;

import java.util.UUID;

public class Category {

    private UUID id;
    private String code;
    private String mediaKey;

    private String name;

    private transient Integer imageSource;

    private Boolean isExpanded = false;

    private Boolean isFavorite = false;

    private UUID parentId;

    private UUID sectionId;

    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.isExpanded = false;
    }

    public Category(UUID id, String name, int imageSource) {
        this.id = id;
        this.name = name;
        this.imageSource = imageSource;
        this.isExpanded = false;
    }

    public Category() {
    }

    public Category(UUID id, String name, Integer imageSource, Boolean isExpanded, Boolean isFavorite, UUID parentId, UUID sectionId) {
        this.id = id;
        this.name = name;
        this.imageSource = imageSource;
        this.isExpanded = isExpanded;
        this.isFavorite = isFavorite;
        this.parentId = parentId;
        this.sectionId = sectionId;
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

    public Boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public UUID getSectionId() {
        return sectionId;
    }

    public void setSectionId(UUID sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getImageSource() {
        return imageSource;
    }

    public void setImageSource(Integer imageSource) {
        this.imageSource = imageSource;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
             "id=" + id +
             ", name='" + name + '\'' +
             ", imageSource=" + imageSource +
             ", isExpanded=" + isExpanded +
             ", isFavorite=" + isFavorite +
             ", parentId=" + parentId +
             ", sectionId=" + sectionId +
             '}';
    }
    public String getCode() { return code; }
    public void setCode(String value) { code = value; }

    public String getMediaKey() { return mediaKey; }
    public void setMediaKey(String value) { mediaKey = value; }

}
