package com.example.admin_app.interfaces.firebase;

import com.example.admin_app.Models.Category;
import com.example.admin_app.Models.Product;

import java.util.ArrayList;

public interface ProductEventListener {

    default void onProductFetched(ArrayList<Product> products) {

    }

    default void onProductDeleted() {

    }
    default void onExtraItemDeleted() {

    }
    default void onExtraDeleteError(String errorMessage) {

    }

    default void onProductDeleteError(String errorMessage) {

    }
    default void onProductFetchedByID(Product product) {

    }

    default void onFetchProductError(String message){};
}
