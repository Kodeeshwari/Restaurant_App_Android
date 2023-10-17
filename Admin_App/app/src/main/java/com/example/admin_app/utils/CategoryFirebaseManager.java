package com.example.admin_app.utils;

import androidx.annotation.NonNull;

import com.example.admin_app.Models.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryFirebaseManager extends FirebaseManager {


    public void fetchCategories(CategoryFetchListener listener) {

        mDatabase.child("categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Category> categories = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Category category = snapshot.getValue(Category.class);
                    if (category != null) {
                        categories.add(category);
                    }
                }
                notifyListenerSuccess(listener, categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                notifyListenerError(listener, error.getMessage());
            }

        });
    }

    private void notifyListenerSuccess(CategoryFetchListener listener, ArrayList<Category> categories) {
        if (listener != null) {
            listener.onCategoriesFetched(categories);
        }
    }

    private void notifyListenerError(CategoryFetchListener listener, String errorMessage) {
        if (listener != null) {
            listener.onFetchCategoriesError(errorMessage);
        }
    }

    // Interface to handle category fetching and error callbacks
    public interface CategoryFetchListener {
        void onCategoriesFetched(ArrayList<Category> categories);
        void onFetchCategoriesError(String errorMessage);
    }
}
