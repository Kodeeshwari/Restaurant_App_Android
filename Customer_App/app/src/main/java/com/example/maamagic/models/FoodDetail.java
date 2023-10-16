package com.example.maamagic.models;

public class FoodDetail {
    private String title;
    private String imgFood;
    private String description;
    private double price;
    private int numberInCart;


    public FoodDetail(String title, String imgFood, String description, double price, int numberInCart) {
        this.title = title;
        this.imgFood = imgFood;
        this.description = description;
        this.price = price;
        this.numberInCart = numberInCart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgFood() {
        return imgFood;
    }

    public void setImgFood(String imgFood) {
        this.imgFood = imgFood;
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

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
