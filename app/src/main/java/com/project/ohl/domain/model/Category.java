package com.project.ohl.domain.model;

import java.util.UUID;

public class Category {

    private UUID id;
    private String mediaKey;
    private String imageUrl;
    private String videoUrl;

    private String name;


    private Boolean isExpanded = false;

    private Boolean isFavorite = false;

    private UUID parentId;


    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.isExpanded = false;
    }

    public Category(UUID id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.isExpanded = false;
    }

    public Category() {
    }

    public Category(UUID id, String name, String imageUrl, Boolean isExpanded, Boolean isFavorite, UUID parentId) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.isExpanded = isExpanded;
        this.isFavorite = isFavorite;
        this.parentId = parentId;
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

    @Override
    public String toString() {
        return "CategoryDto{" +
             "id=" + id +
             ", name='" + name + '\'' +
             ", isExpanded=" + isExpanded +
             ", isFavorite=" + isFavorite +
             ", parentId=" + parentId +
             '}';
    }

    public String getMediaKey() {
        if (imageUrl != null) {
            String filename = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
            return filename.endsWith(".webp") ? filename.substring(0, filename.length() - 5) : filename;
        }
        return mediaKey;
    }
    public void setMediaKey(String value) { mediaKey = value; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String value) { imageUrl = value; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String value) { videoUrl = value; }

}
