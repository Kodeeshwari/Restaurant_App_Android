package com.example.admin_app.Fragments;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin_app.Adapter.CategoryAdapter;
import com.example.admin_app.Adapter.ProductAdapter;
import com.example.admin_app.AddCategoryActivity;
import com.example.admin_app.Models.Category;
import com.example.admin_app.Models.Product;
import com.example.admin_app.ProductDetailActivity;
import com.example.admin_app.R;
import com.example.admin_app.interfaces.firebase.ProductEventListener;
import com.example.admin_app.utils.ProductFirebaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ProductFragment extends Fragment  implements ProductEventListener,ProductAdapter.OnProductAdapterListener {


    private ProductFirebaseManager productFirebaseManager;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddProduct;
    private ArrayList<Product> productList;
    private String categoryId;
    public ProductFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productFirebaseManager = new ProductFirebaseManager();
        productList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewProduct);
        fabAddProduct = view.findViewById(R.id.fab_add_product);
        initPage();

        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                startActivity(intent);
            }
        });
        return  view;
    }

    private void initPage() {
        categoryId = getArguments().getString("categoryId");
        fetchedProductList(categoryId);
    }

    private void fetchedProductList(String categoryId) {
        productFirebaseManager.fetchProductList(this, categoryId);
    }

    @Override
    public void onProductFetched(ArrayList<Product> products) {
        initializeRecyclerView(recyclerView,products);
    }

    private void initializeRecyclerView(RecyclerView recyclerView, ArrayList<Product> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ProductAdapter productAdapter = new ProductAdapter(requireActivity().getApplicationContext(), data);
        productAdapter.setOnDeleteClickListener(this);
        recyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onDeleteClick(String productId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this Product?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performDeleteProduct(productId);
                        Toast.makeText(requireContext(), "Product deleted" + productId, Toast.LENGTH_SHORT).show();
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

    private void performDeleteProduct(String productId) {
        productFirebaseManager.deleteProduct(productId,this);
    }

    @Override
    public void onEditClick(String productId) {
        Intent intent = new Intent(requireContext(), ProductDetailActivity.class);
        intent.putExtra("IS_EDIT_KEY", true);
        intent.putExtra("PRODUCT_ID",productId);
        startActivity(intent);
    }

    @Override
    public void onProductClick(String productId) {
        Intent intent = new Intent(requireContext(), ProductDetailActivity.class);
        intent.putExtra("IS_EDIT_KEY", true);
        intent.putExtra("PRODUCT_ID",productId);
        startActivity(intent);

    }
}