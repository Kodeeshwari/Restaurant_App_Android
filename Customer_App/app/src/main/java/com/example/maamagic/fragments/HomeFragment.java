package com.example.maamagic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maamagic.R;
import com.example.maamagic.adapter.CategoryAdapter;
import com.example.maamagic.adapter.PopularAdapter;
import com.example.maamagic.firebase_manager.CategoryFirebaseManager;
import com.example.maamagic.firebase_manager.ProductFirebaseManager;
import com.example.maamagic.firebase_manager.Utility;
import com.example.maamagic.models.CategoryModel;
import com.example.maamagic.models.ProductDetailModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewCategories, recyclerViewProducts;
    private CategoryModel categoryModel;
    private ProductDetailModel productDetailModel;

    private ArrayList<ProductDetailModel> products;

    CategoryFirebaseManager categoryFirebaseManager;
    ProductFirebaseManager productFirebaseManager;
    private DatabaseReference mDatabaseCategory, mDatabaseProduct;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseCategory = FirebaseDatabase.getInstance().getReference().child("categories");
        mDatabaseProduct = FirebaseDatabase.getInstance().getReference().child("products");
        categoryFirebaseManager = new CategoryFirebaseManager();
        productFirebaseManager = new ProductFirebaseManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewCategories = view.findViewById(R.id.recyclerViewCategory);
        recyclerViewProducts = view.findViewById(R.id.recyclerViewPopular);
        fetchCategories();
        fetchProducts();

        return view;
    }

    private void fetchProducts() {
        productFirebaseManager.fetchProducts(new ProductFirebaseManager.ProductFetchListener() {
            @Override
            public void onProductsFetched(ArrayList<ProductDetailModel> products) {
                initializeProductRecyclerView(recyclerViewProducts, products);

            }

            @Override
            public void onFetchProductsError(String errorMessage) {
                Utility.showToastShort(getActivity().getApplicationContext(), errorMessage);
                Log.e("FirebaseUtility", "Failed to fetch categories: " + errorMessage);
            }
        });

    }

    private void initializeCategoryRecyclerView(RecyclerView recyclerView, ArrayList<CategoryModel> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(requireActivity().getApplicationContext(),data);
        recyclerView.setAdapter(categoryAdapter);
    }

    private void initializeProductRecyclerView(RecyclerView recyclerView, ArrayList<ProductDetailModel> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        PopularAdapter productAdapter = new PopularAdapter(requireActivity().getApplicationContext(),data);
        recyclerView.setAdapter(productAdapter);
    }


    private void fetchCategories() {
        categoryFirebaseManager.fetchCategories(new CategoryFirebaseManager.CategoryFetchListener() {
            @Override
            public void onCategoriesFetched(ArrayList<CategoryModel> categories) {
                initializeCategoryRecyclerView(recyclerViewCategories, categories);
            }

            @Override
            public void onFetchCategoriesError(String errorMessage) {
                Utility.showToastShort(getActivity().getApplicationContext(), errorMessage);
                Log.e("FirebaseUtility", "Failed to fetch categories: " + errorMessage);
            }
        });
    }
}