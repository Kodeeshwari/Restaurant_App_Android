package com.example.maamagic.models;

public class SliderItem {
    private String imageUrl; // If you are loading images from URLs, use this field


    public SliderItem() {
    }

    public SliderItem(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getImageUrl() {
        return imageUrl;
    }
}
