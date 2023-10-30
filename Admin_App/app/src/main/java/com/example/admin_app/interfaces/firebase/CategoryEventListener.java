package com.example.admin_app.interfaces.firebase;

import com.example.admin_app.Models.Category;

import java.util.ArrayList;

public interface CategoryEventListener {

    default void onCategoryEdited(String categoryId) {

    }

    default void onCategoryEditError(String errorMessage) {

    }

    default void onCategoryAdded(String categoryId) {

    }

    default void onCategoryAddError(String errorMessage) {

    }

    default void onCategoryDeleted() {

    }

    default void onCategoryDeleteError(String errorMessage) {

    }

    default void onCategoriesFetched(ArrayList<Category> categories) {

    }

    default void onFetchCategoriesError(String errorMessage) {

    }

    default void onCategoryFetched(Category category) {

    }

    default void onFetchCategoryError(String errorMessage) {

    }
}
