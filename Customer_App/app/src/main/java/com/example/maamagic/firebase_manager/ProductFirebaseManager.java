package com.example.maamagic.firebase_manager;

import androidx.annotation.NonNull;

import com.example.maamagic.models.ProductDetailModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductFirebaseManager extends FirebaseManager {

    public void fetchProducts(ProductFetchListener listener) {
        mDatabase.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ProductDetailModel> products = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ProductDetailModel product = snapshot.getValue(ProductDetailModel.class);
                    if (product != null) {
                        products.add(product);
                    }
                }
                notifyProductFetchSuccess(listener, products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifyProductFetchError(listener, databaseError.getMessage());
            }
        });
    }

    private void notifyProductFetchSuccess(ProductFetchListener listener, ArrayList<ProductDetailModel> products) {
        if (listener != null) {
            listener.onProductsFetched(products);
        }
    }

    private void notifyProductFetchError(ProductFetchListener listener, String errorMessage) {
        if (listener != null) {
            listener.onFetchProductsError(errorMessage);
        }
    }

    // Interface to handle product fetching and error callbacks
    public interface ProductFetchListener {
        void onProductsFetched(ArrayList<ProductDetailModel> products);
        void onFetchProductsError(String errorMessage);
    }

}
