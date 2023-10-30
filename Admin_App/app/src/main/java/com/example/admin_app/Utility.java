package com.example.admin_app;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URI;

public class Utility {

    public static void loadImage(String imgUrl, ImageView imgView) {

        Picasso.get()
                .load(imgUrl)  // Image URL from Firebase
                .placeholder(R.drawable.pizza)  // Placeholder image while loading
                .resize(100, 100)
                .centerCrop()
                .error(R.drawable.pizza)  // Image to show in case of an error
                .into(imgView);
    }

}
