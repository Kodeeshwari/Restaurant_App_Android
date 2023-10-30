package com.example.admin_app.utils;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.admin_app.Fragments.ProductFragment;
import com.example.admin_app.Models.Category;
import com.example.admin_app.Models.Product;
import com.example.admin_app.ProductDetailActivity;
import com.example.admin_app.interfaces.firebase.ProductEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductFirebaseManager extends FirebaseManager{

    public void fetchProductList(ProductEventListener listener, String categoryId) {
        mDatabase.child("products").orderByChild("categoryId").equalTo(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Product> productList = new ArrayList<>();

                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    if (product != null) {
                        productList.add(product);
                    }
                }
                listener.onProductFetched(productList);
           //     Log.d(TAG,  "Product:  " +"onDataChange: " + productList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG,  "Product:  " +"onDataChange: " + error.toString());
            }
        });

    }

    public void deleteProduct(String productId,ProductEventListener listener) {
        DatabaseReference productRef = mDatabase.child("products").child(productId);

        productRef.removeValue()
                .addOnSuccessListener(aVoid -> {
                    listener.onProductDeleted();
                    Log.e("Firebase", " delete product: ");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Failed to delete product: " + e.getMessage());
                    listener.onProductDeleteError("Failed to delete product from the database");
                });

    }

    public void fetchProductById(String productId, ProductEventListener listener) {
        mDatabase.child("products").child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                Log.d(TAG, "PRODUCT"+"onDataChange: "+ product);
                listener.onProductFetchedByID(product);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFetchProductError(error.getMessage());
            }
        });
    }

    public void deleteExtraById(String productId,String extraId ,ProductEventListener listener) {
        DatabaseReference productRef = mDatabase.child("products").child(productId).child("productExtras").child(extraId);

        productRef.removeValue()
                .addOnSuccessListener(aVoid -> {
                    listener.onExtraItemDeleted();
                    Log.e("Firebase", " delete product: ");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Failed to delete product: " + e.getMessage());
                    listener.onExtraDeleteError("Failed to delete product from the database");
                });
    }
}
