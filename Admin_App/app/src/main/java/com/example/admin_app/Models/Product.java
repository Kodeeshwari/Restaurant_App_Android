package com.example.admin_app.Models;
import java.util.HashMap;
import java.util.Map;

public class Product {
    private String categoryId;
    private String productDescription;
    private Map<String, Extra> productExtras;
    private String productId;
    private String productImageURL;
    private boolean productIsAvailable;
    private double productPrice;
    private String productTitle;

    public Product() {
    }

    public Product(String categoryId, String productDescription, Map<String, Extra> productExtras, String productId, String productImageURL, boolean productIsAvailable, double productPrice, String productTitle) {
        this.categoryId = categoryId;
        this.productDescription = productDescription;
        this.productExtras = productExtras;
        this.productId = productId;
        this.productImageURL = productImageURL;
        this.productIsAvailable = productIsAvailable;
        this.productPrice = productPrice;
        this.productTitle = productTitle;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Map<String, Extra> getProductExtras() {
        return productExtras;
    }

    public void setProductExtras(Map<String, Extra> productExtras) {
        this.productExtras = productExtras;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }

    public boolean isProductIsAvailable() {
        return productIsAvailable;
    }

    public void setProductIsAvailable(boolean productIsAvailable) {
        this.productIsAvailable = productIsAvailable;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
}

