package com.example.admin_app.Models;
import java.util.HashMap;
import java.util.Map;

public class Product {
    private String productId;
    private String title;
    private String description;
    private double price;

    private String categoryID;
    private String categoryTitle;
    private Map<String, Extra> extras;
    private String image_url;
    private boolean is_available;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String category) {
        this.categoryTitle = category;
    }

    public Map<String, Extra> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, Extra> extras) {
        this.extras = extras;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }
}

