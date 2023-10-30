package com.example.admin_app.utils;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.admin_app.Models.Category;
import com.example.admin_app.interfaces.firebase.CategoryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class CategoryFirebaseManager extends FirebaseManager {


    public void uploadCategoryWithImage(Category category, Uri imageUri, CategoryEventListener listener, Boolean isEditCategory) {
        // Generate a unique name for the image using UUID
        String imageName = UUID.randomUUID().toString();
        StorageReference imageRef = mStorageRef.child("categoryImages").child(imageName);

        UploadTask uploadTask = imageRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                // Image uploaded successfully, get the download URL
                String imageUrl = uri.toString();
                category.setCategoryImage(imageUrl);
                if (isEditCategory) {
                    updateCategoryInDatabase(category, listener);
                } else {
                    addCategoryToDatabase(category, listener);
                }
            });
        }).addOnFailureListener(e -> {
            Log.e("Firebase", "Image upload failed: " + e.getMessage());
            listener.onCategoryAddError("Failed to upload image");
        });
    }

    private void addCategoryToDatabase(Category category, CategoryEventListener listener) {

        final String categoryId = getKey();

        DatabaseReference categoryRef = mDatabase.child("categories").child(categoryId); // Generates a unique category ID

        // Set the category data in the Firebase Realtime Database
        categoryRef.setValue(category)
                .addOnSuccessListener(aVoid -> {
                    listener.onCategoryAdded(categoryId);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Failed to add category: " + e.getMessage());
                    listener.onCategoryAddError("Failed to add category to database");
                });
    }

    private void updateCategoryInDatabase(Category category, CategoryEventListener listener) {
        DatabaseReference categoryRef = mDatabase.child("categories").child(category.getCategoryId());

        // Set the category data in the Firebase Realtime Database
        categoryRef.setValue(category)
                .addOnSuccessListener(aVoid -> {
                    listener.onCategoryEdited(category.getCategoryId());
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Failed to edit category: " + e.getMessage());
                    listener.onCategoryEditError("Failed to edit category in the database");
                });
    }


    public void fetchCategories(CategoryEventListener listener) {

        mDatabase.child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Category> categories = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Category category = snapshot.getValue(Category.class);
                    String categoryId = snapshot.getKey();
                    if (category != null) {
                        category.setCategoryId(categoryId);
                        categories.add(category);
                    }
                }
                listener.onCategoriesFetched(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFetchCategoriesError(error.getMessage());
            }
        });


    }

    public void fetchCategoryById(String categoryId, CategoryEventListener listener) {
Log.e("TESTTEST",categoryId);

        mDatabase.child("categories").child(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Category category = dataSnapshot.getValue(Category.class);
                listener.onCategoryFetched(category);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFetchCategoryError(error.getMessage());
            }
        });


    }

    public String getKey() {
        return mDatabase.push().getKey();
    }


    public void deleteCategory(String categoryId, CategoryEventListener listener) {
        // Reference to the category you want to delete
        DatabaseReference categoryRef = mDatabase.child("categories").child(categoryId);

        categoryRef.removeValue()
                .addOnSuccessListener(aVoid -> {
                    // Category deleted successfully
                    listener.onCategoryDeleted();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Failed to delete category: " + e.getMessage());
                    listener.onCategoryDeleteError("Failed to delete category from the database");
                });
    }


}
