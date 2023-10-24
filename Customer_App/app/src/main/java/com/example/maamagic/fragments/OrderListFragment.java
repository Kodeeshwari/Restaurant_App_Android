package com.example.maamagic.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maamagic.R;
import com.example.maamagic.adapter.OrderListAdapter;
import com.example.maamagic.models.OrderItemModel;

import java.util.ArrayList;
import java.util.List;


public class OrderListFragment extends Fragment {

    private RecyclerView recyclerViewOrders;
    private List<OrderItemModel> orderItemList;
    private OrderListAdapter orderListAdapter;
    public OrderListFragment() {
        // Required empty public constructor
    }
    String cartId;

    public static OrderListFragment newInstance(String cartId) {
        OrderListFragment fragment = new OrderListFragment();
        cartId = cartId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            cartId = bundle.getString("Cart");
            Log.d(TAG, "onCreate: "+cartId);

         }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        recyclerViewOrders = view.findViewById(R.id.recyclerViewOrders);
        // Initialize orderItemList and orderListAdapter here, and set up the RecyclerView

        // Example initialization of orderItemList and orderListAdapter
        orderItemList = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(orderItemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewOrders.setLayoutManager(layoutManager);
        recyclerViewOrders.setAdapter(orderListAdapter);

        return view;    }
}