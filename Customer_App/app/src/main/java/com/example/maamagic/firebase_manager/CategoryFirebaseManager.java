package com.example.maamagic.firebase_manager;

import android.icu.util.ULocale;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.maamagic.models.CategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryFirebaseManager extends FirebaseManager {

    public void fetchCategories(CategoryFetchListener listener) {
        mDatabase.child("categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<CategoryModel> categories = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CategoryModel category = snapshot.getValue(CategoryModel.class);
                    if (category != null) {
                        categories.add(category);
                    }
                }
                notifyListenerSuccess(listener, categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifyListenerError(listener, databaseError.getMessage());
            }
        });
    }

    private void notifyListenerSuccess(CategoryFetchListener listener, ArrayList<CategoryModel> categories) {
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
        void onCategoriesFetched(ArrayList<CategoryModel> categories);
        void onFetchCategoriesError(String errorMessage);
    }
}
