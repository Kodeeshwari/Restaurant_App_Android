package com.example.maamagic.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CategoryModel {

    String categoryId;
    String categoryName;
    String categoryImage;
    String categoryDescription;

    public CategoryModel(){}

    public CategoryModel(String categoryId, String categoryName, String categoryImage, String categoryDescription) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("categoryId", categoryId);
        result.put("categoryName", categoryName);
        result.put("categoryImage", categoryImage);
        result.put("categoryDescription", categoryDescription);
        return result;
    }
}
