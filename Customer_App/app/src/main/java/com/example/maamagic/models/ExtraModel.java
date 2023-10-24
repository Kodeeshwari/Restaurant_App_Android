package com.example.maamagic.models;

import java.io.Serializable;

public class ExtraModel implements Serializable {
    private String title;
    private double price;

    private boolean isSelected;

    public ExtraModel(){}

    public ExtraModel(String title, double price, boolean isSelected) {
        this.title = title;
        this.price = price;
        this.isSelected = isSelected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
