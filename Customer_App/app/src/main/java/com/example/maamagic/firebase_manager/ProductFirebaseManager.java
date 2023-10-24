package com.example.maamagic.firebase_manager;

import androidx.annotation.NonNull;

import com.example.maamagic.models.ProductDetailModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductFirebaseManager extends FirebaseManager {

    // Fetch single product by ID
    public void fetchProductById(String productId, SingleProductFetchListener listener) {
        mDatabase.child("products").child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductDetailModel product = dataSnapshot.getValue(ProductDetailModel.class);
                if (product != null) {
                    product.setProductId(productId);
                    notifySingleProductFetchSuccess(listener, product);
                } else {
                    notifySingleProductFetchError(listener, "Product not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifySingleProductFetchError(listener, databaseError.getMessage());
            }
        });
    }

    // Fetch multiple products
    public void fetchProducts(ProductFetchListener listener) {
        mDatabase.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ProductDetailModel> products = new ArrayList<>();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    String productId = productSnapshot.getKey(); // Get the product ID
                    ProductDetailModel product = productSnapshot.getValue(ProductDetailModel.class);
                    if (product != null) {
                        product.setProductId(productId); // Set the product ID
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

    private void notifySingleProductFetchSuccess(SingleProductFetchListener listener, ProductDetailModel product) {
        if (listener != null) {
            listener.onSingleProductFetched(product);
        }
    }

    private void notifySingleProductFetchError(SingleProductFetchListener listener, String errorMessage) {
        if (listener != null) {
            listener.onFetchSingleProductError(errorMessage);
        }
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

    // Interface to handle single product fetching and error callbacks
    public interface SingleProductFetchListener {
        void onSingleProductFetched(ProductDetailModel product);
        void onFetchSingleProductError(String errorMessage);
    }

    // Interface to handle multiple product fetching and error callbacks
    public interface ProductFetchListener {
        void onProductsFetched(ArrayList<ProductDetailModel> products);
        void onFetchProductsError(String errorMessage);
    }
}
