package com.example.maamagic.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductDetailModel implements Serializable {

    private String productId;
    private String categoryId;
    private String productTitle;
    private String productImageURL;
    private String productDescription;
    private double productPrice;
    private boolean productIsAvailable;


    private HashMap<String,ExtraModel> productExtras;

    public  ProductDetailModel(){};

    public ProductDetailModel(String productId, String categoryId, String productTitle, String productImageURL, String productDescription, double productPrice, boolean productIsAvailable, HashMap<String, ExtraModel> productExtras) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.productTitle = productTitle;
        this.productImageURL = productImageURL;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productIsAvailable = productIsAvailable;
        this.productExtras = productExtras;
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

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isProductIsAvailable() {
        return productIsAvailable;
    }

    public void setProductIsAvailable(boolean productIsAvailable) {
        this.productIsAvailable = productIsAvailable;
    }
    public HashMap<String, ExtraModel> getProductExtras() {
        return productExtras;
    }

    public void setProductExtras(HashMap<String, ExtraModel> productExtras) {
        this.productExtras = productExtras;
    }

    // Method to add an extra to the extras HashMap
    public void addExtra(String extraId, ExtraModel extra) {
        if (productExtras == null) {
            productExtras = new HashMap<>();
        }
        productExtras.put(extraId, extra);
    }


}
