package com.example.maamagic.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maamagic.R;
import com.example.maamagic.adapter.CategoryAdapter;
import com.example.maamagic.adapter.PopularAdapter;
import com.example.maamagic.models.FoodDetail;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewCategories, recyclerViewPopular;
    private String[] categoryNames;

    private ArrayList<FoodDetail> popularFoodList;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryNames = getResources().getStringArray(R.array.category_names);
        popularFoodList = new ArrayList<>();
        popularFoodList.add(new FoodDetail("Pepperoni Pizza", "pop_1", "",9.76,0));
        popularFoodList.add(new FoodDetail("Cheese Burger", "pop_2","", 8.79, 1));
        popularFoodList.add(new FoodDetail("Vegetable Pizza", "pop_3", "",8.5,2));
        // ... add more popular food items as needed

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewCategories = view.findViewById(R.id.recyclerViewCategory);
        recyclerViewPopular = view.findViewById(R.id.recyclerViewPopular);

        initializeRecyclerView(recyclerViewCategories, categoryNames);
        initializePopularRecyclerView(recyclerViewPopular, popularFoodList);
        return view;
    }

    private void initializeRecyclerView(RecyclerView recyclerView, String[] data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(data);
        recyclerView.setAdapter(categoryAdapter);
    }
    private void initializePopularRecyclerView(RecyclerView recyclerView, ArrayList<FoodDetail> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

      PopularAdapter popularAdapter = new PopularAdapter(data);
      recyclerView.setAdapter(popularAdapter);
    }
}