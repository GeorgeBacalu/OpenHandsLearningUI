package com.example.llt_project_separate.general.standard_classes;

public class Category {

    private int id;

    private String name;

    private Integer imageSource;

    private Boolean isExpanded = false;

    private Boolean isFavorite = false;

    private Long parentId;

    private Long sectionId;

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

    public Category() {
    }

    public Category(int id, String name, Integer imageSource, Boolean isExpanded, Boolean isFavorite, Long parentId, Long sectionId) {
        this.id = id;
        this.name = name;
        this.imageSource = imageSource;
        this.isExpanded = isExpanded;
        this.isFavorite = isFavorite;
        this.parentId = parentId;
        this.sectionId = sectionId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
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
}
