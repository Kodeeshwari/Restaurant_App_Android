package com.example.maamagic.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maamagic.ProductDetailActivity;
import com.example.maamagic.R;
import com.example.maamagic.adapter.BannerSliderAdapter;
import com.example.maamagic.adapter.CategoryAdapter;
import com.example.maamagic.adapter.ProductCardListAdapater;
import com.example.maamagic.firebase_manager.CategoryFirebaseManager;
import com.example.maamagic.firebase_manager.ProductFirebaseManager;
import com.example.maamagic.firebase_manager.SliderFirebaseManager;
import com.example.maamagic.firebase_manager.UserFirebaseManager;
import com.example.maamagic.firebase_manager.Utility;
import com.example.maamagic.interfaces.ProductCardEventListener;
import com.example.maamagic.models.CategoryModel;
import com.example.maamagic.models.ProductDetailModel;
import com.example.maamagic.models.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ProductCardEventListener {

    private RecyclerView recyclerViewCategories;
    private RecyclerView productReclyView;
    private UserFirebaseManager userFirebaseManager;
    private CategoryFirebaseManager categoryFirebaseManager;
    private ProductFirebaseManager productFirebaseManager;
    private SliderFirebaseManager sliderFirebaseManager;
    private ViewPager2 sliderImageViewPager;
    private TextView userNameTv;


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
        categoryFirebaseManager = new CategoryFirebaseManager();
        productFirebaseManager = new ProductFirebaseManager();
        sliderFirebaseManager = new SliderFirebaseManager();
        userFirebaseManager =new UserFirebaseManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewCategories = view.findViewById(R.id.recyclerViewCategory);
        productReclyView = view.findViewById(R.id.productGridView);

        sliderImageViewPager = view.findViewById(R.id.viewPager);
        userNameTv = view.findViewById(R.id.txtUsername);

        userNameTv.setText("Welcome Back, "+userFirebaseManager.getCurrentUsername());
        fetchCategories();
        fetchProducts();
        fetchSlider();

        return view;
    }

    private void fetchSlider() {

        sliderFirebaseManager.fetchSliderItems(new SliderFirebaseManager.SliderFetchListener() {
            @Override
            public void onSliderItemsFetched(List<SliderItem> sliderItems) {
                BannerSliderAdapter bannerSliderAdapter = new BannerSliderAdapter(sliderItems);
                sliderImageViewPager.setAdapter(bannerSliderAdapter);
            }

            @Override
            public void onFetchSliderItemsError(String errorMessage) {

            }
        });


    }

    private void fetchProducts() {
        productFirebaseManager.fetchProducts(new ProductFirebaseManager.ProductFetchListener() {
            @Override
            public void onProductsFetched(ArrayList<ProductDetailModel> products) {
                initializeProductRecyclerView(productReclyView, products);

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

        CategoryAdapter categoryAdapter = new CategoryAdapter(requireActivity().getApplicationContext(), data);
        recyclerView.setAdapter(categoryAdapter);
    }

    private void initializeProductRecyclerView(RecyclerView productReclyView, ArrayList<ProductDetailModel> data) {
        productReclyView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        ProductCardListAdapater adapter = new ProductCardListAdapater(requireContext(),data,this);
        productReclyView.setAdapter(adapter);
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

    @Override
    public void onItemClick(String productId) {
        final Intent intent = new Intent(requireContext(), ProductDetailActivity.class);
        intent.putExtra("productId",productId);
        startActivity(intent);
    }

    @Override
    public void onAddButtonClick(String productId) {
        final Intent intent = new Intent(requireContext(), ProductDetailActivity.class);
        intent.putExtra("productId",productId);
        startActivity(intent);
    }
}