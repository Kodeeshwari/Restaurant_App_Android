package com.example.admin_app.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin_app.Adapter.CategoryAdapter;
import com.example.admin_app.Models.Category;
import com.example.admin_app.R;
import com.example.admin_app.utils.CategoryFirebaseManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> categoryList;

    CategoryFirebaseManager categoryFirebaseManager;
    private DatabaseReference mDatabase;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("categories");
        categoryFirebaseManager = new CategoryFirebaseManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewCategory);
        fetchCategories();

        return view;
    }


    private void initializeRecyclerView(RecyclerView recyclerView, ArrayList<Category> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(requireActivity().getApplicationContext(),data);
        recyclerView.setAdapter(categoryAdapter);
    }
    private void fetchCategories() {
        categoryFirebaseManager.fetchCategories(new CategoryFirebaseManager.CategoryFetchListener() {
            @Override
            public void onCategoriesFetched(ArrayList<Category> categories) {
                initializeRecyclerView(recyclerView, categories);
            }

            @Override
            public void onFetchCategoriesError(String errorMessage) {
                Log.e("FirebaseUtility", "Failed to fetch categories: " + errorMessage);
            }
        });
    }
}