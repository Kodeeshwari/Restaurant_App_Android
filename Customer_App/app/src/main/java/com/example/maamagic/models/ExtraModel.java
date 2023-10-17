package com.example.maamagic.models;

import java.io.Serializable;

public class ExtraModel implements Serializable {
    private String title;
    private double price;

    public ExtraModel(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }
}
