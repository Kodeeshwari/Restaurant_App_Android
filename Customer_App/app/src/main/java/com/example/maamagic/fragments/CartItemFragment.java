package com.example.maamagic.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maamagic.R;
public class CartItemFragment extends Fragment {


    public CartItemFragment() {
        // Required empty public constructor
    }


    public static CartItemFragment newInstance(String param1, String param2) {
        CartItemFragment fragment = new CartItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart_item, container, false);
    }
}