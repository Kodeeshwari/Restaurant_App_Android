package com.example.maamagic.firebase_manager;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Utility {
    public static void showToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .into(imageView);
    }
}
