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
import com.example.maamagic.adapter.CartItemAdapter;
import com.example.maamagic.adapter.OrderListAdapter;
import com.example.maamagic.firebase_manager.OrderFirebaseManager;
import com.example.maamagic.interfaces.OrderInterfaces;
import com.example.maamagic.models.OrderItemModel;
import com.example.maamagic.models.OrderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;


public class OrderListFragment extends Fragment {

    private RecyclerView recyclerViewOrders;
    private List<OrderItemModel> orderItemList;
    private OrderListAdapter orderListAdapter;
    private OrderModel orderModel;
    private OrderFirebaseManager orderFirebaseManager;

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
        orderFirebaseManager = new OrderFirebaseManager();
        orderModel = new OrderModel();

        loadOrder();

        return view;    }

    private void loadOrder() {

        orderFirebaseManager.fetchOrder(new OrderInterfaces() {
            @Override
            public void onOrderFetched(ArrayList<OrderModel> order) {
                ArrayList<OrderModel> orderModels = new ArrayList<>(order);
                initialiseOrderListAdapter(orderModels);
            }

            @Override
            public void onFetchError(String errorMessage) {

            }
        });

    }

    private void initialiseOrderListAdapter(ArrayList<OrderModel> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewOrders.setLayoutManager(layoutManager);

        OrderListAdapter orderListAdapter = new OrderListAdapter(requireActivity().getApplicationContext(),data);
        recyclerViewOrders.setAdapter(orderListAdapter);

    }
}