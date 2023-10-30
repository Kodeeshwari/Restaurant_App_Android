package com.example.admin_app.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin_app.Adapter.CategoryAdapter;
import com.example.admin_app.AddCategoryActivity;
import com.example.admin_app.Models.Category;
import com.example.admin_app.R;
import com.example.admin_app.interfaces.firebase.CategoryEventListener;
import com.example.admin_app.utils.CategoryFirebaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CategoryFragment extends Fragment implements CategoryAdapter.OnCategoryAdapterListener, CategoryEventListener{

    private FloatingActionButton fabAddCategory;
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

        // Initialize the FAB
        fabAddCategory = view.findViewById(R.id.fab_add_category);
        fabAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                startActivity(intent);
            }
        });

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewCategory);
        fetchCategories();

        return view;
    }


    private void initializeRecyclerView(RecyclerView recyclerView, ArrayList<Category> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(requireActivity().getApplicationContext(), data);
        categoryAdapter.setOnDeleteClickListener(this);
        recyclerView.setAdapter(categoryAdapter);
    }


    private void fetchCategories() {
        categoryFirebaseManager.fetchCategories(this);
    }

    @Override
    public void onDeleteClick(String categoryID) {

        // Display an AlertDialog for confirmation
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this category?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform the delete action here
                        // You can call a method to delete the category, update the dataset, etc.

                        performDeleteCategory(categoryID);
                        // Display a toast message to confirm the deletion
                        Toast.makeText(requireContext(), "Category deleted" + categoryID, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel the delete action
                        dialog.dismiss();
                    }
                })
                .show();

    }

    @Override
    public void onEditClick(String categoryID) {
        Intent intent = new Intent(requireContext(), AddCategoryActivity.class);
        intent.putExtra("IS_EDIT_KEY", true);
        intent.putExtra("CATEGORY_ID",categoryID);
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String categoryId) {
        ProductFragment productFragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString("categoryId", categoryId);

        productFragment.setArguments(args);

        // Perform the fragment transaction to navigate to ProductFragment
        FragmentTransaction transaction = null;
        if (getFragmentManager() != null) {
            transaction = getFragmentManager().beginTransaction();
        }
        transaction.replace(R.id.frame_container, productFragment);
        transaction.addToBackStack(null); // Optionally, add to the back stack
        transaction.commit();
    }

    private void performDeleteCategory(String categoryId) {
        Toast.makeText(requireContext(), "" + categoryId, Toast.LENGTH_SHORT).show();
        categoryFirebaseManager.deleteCategory(categoryId,this);
    }



    @Override
    public void onCategoryDeleted() {
        Toast.makeText(requireContext(), "Category Delete", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCategoryDeleteError(String errorMessage) {

    }

    @Override
    public void onCategoriesFetched(ArrayList<Category> categories) {
        initializeRecyclerView(recyclerView, categories);
    }

    @Override
    public void onFetchCategoriesError(String errorMessage) {

    }
}