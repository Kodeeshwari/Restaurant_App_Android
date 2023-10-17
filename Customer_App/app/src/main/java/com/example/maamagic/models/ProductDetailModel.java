package com.example.maamagic.models;

import java.io.Serializable;

public class ProductDetailModel implements Serializable {

    private String productId;
    private String categoryId;
    private String title;
    private String image_url;
    private String description;
    private double price;
    private boolean is_available;

    public  ProductDetailModel(){};
    public ProductDetailModel(String productId, String categoryId, String title, String image_url, String description, double price, boolean is_available) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.title = title;
        this.image_url = image_url;
        this.description = description;
        this.price = price;
        this.is_available = is_available;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }
}
