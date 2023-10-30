package com.example.maamagic.firebase_manager;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.maamagic.R;
import com.bumptech.glide.request.RequestOptions;

public class Utility {
    public static void showToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void loadImageWithCircle(Context context, String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.cat_1)
                .centerCrop()
                .apply(requestOptions)// Placeholder image while the actual image is loading
                .error(R.drawable.cat_2) // Error image if the actual image fails to load
                .into(imageView);
    }
    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.cat_1)
                .centerCrop()
                .error(R.drawable.cat_2) // Error image if the actual image fails to load
                .into(imageView);
    }
}
