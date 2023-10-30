package com.example.admin_app.Models;

public class Category {
    private String categoryId;
    private String categoryName;
    private String categoryImage;
    private String categoryDescription;

    public Category() {

    }

    public Category(String categoryId, String categoryName, String image_url, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = image_url;
        this.categoryDescription = description;
    }

    public Category(String categoryName, String categoryDescription) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

}
