package com.example.admin_app.Models;

public class Category {
    private String categoryID;
    private String categoryName;
    private String image_url;
    private String description;

    public Category() {

    }

    public Category(String categoryID, String categoryName, String image_url, String description) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.image_url = image_url;
        this.description = description;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
