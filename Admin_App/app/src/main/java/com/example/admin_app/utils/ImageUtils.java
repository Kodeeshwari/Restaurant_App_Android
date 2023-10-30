package com.example.admin_app.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtils {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private Activity activity;
    private Uri photoUri;

    public ImageUtils(Activity activity) {
        this.activity = activity;
    }

    public void capturePhoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "MyPhoto");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by CameraX");

        photoUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void pickPhoto() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
    }

    public Uri getPhotoUri() {
        return photoUri;
    }


    public static Uri compressAndResizeImage(Context context, Uri originalImageUri, int maxWidth, int maxHeight) {
        try {
            InputStream input = context.getContentResolver().openInputStream(originalImageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            // Calculate the new dimensions while preserving the aspect ratio
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scale = (float) maxWidth / width;
            if (height * scale > maxHeight) {
                scale = (float) maxHeight / height;
            }

            int newWidth = (int) (width * scale);
            int newHeight = (int) (height * scale);

            // Create a new resized bitmap
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);

            // Compress the resized bitmap into a ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);

            // Create a temporary file to store the compressed image
            File tempFile = File.createTempFile("compressed_image", ".jpg", context.getCacheDir()); // Store in the app's cache directory
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(outputStream.toByteArray());
            fileOutputStream.close();

            return Uri.fromFile(tempFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
