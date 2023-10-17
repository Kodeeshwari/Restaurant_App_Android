package com.example.maamagic.models;

public class ExtraItemModel {
    String extraId;
    String extraTitle;
    String extraPrice;

    public ExtraItemModel(String extraId, String extraTitle, String extraPrice) {
        this.extraId = extraId;
        this.extraTitle = extraTitle;
        this.extraPrice = extraPrice;
    }

    public String getExtraId() {
        return extraId;
    }

    public void setExtraId(String extraId) {
        this.extraId = extraId;
    }

    public String getExtraTitle() {
        return extraTitle;
    }

    public void setExtraTitle(String extraTitle) {
        this.extraTitle = extraTitle;
    }

    public String getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(String extraPrice) {
        this.extraPrice = extraPrice;
    }
}
