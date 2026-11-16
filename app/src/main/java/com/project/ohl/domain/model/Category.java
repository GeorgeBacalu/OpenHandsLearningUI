package com.project.ohl.domain.model;

import java.util.UUID;
import org.jspecify.annotations.NonNull;

public class Category {

    private UUID id;
    private String imageUrl;
    private String videoUrl;

    private String name;

    private Boolean isFavorite = false;

    private UUID parentId;

    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(UUID id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Category() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public @NonNull String toString() {
        return "CategoryDto{" +
             "id=" + id +
             ", name='" + name + '\'' +
             ", isFavorite=" + isFavorite +
             ", parentId=" + parentId +
             '}';
    }

    public String getImageUrl() { return imageUrl; }
    public String getVideoUrl() { return videoUrl; }

}
