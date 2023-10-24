package com.example.maamagic.fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.maamagic.R;
import com.example.maamagic.adapter.CartItemAdapter;
import com.example.maamagic.firebase_manager.CartFirebaseManager;
import com.example.maamagic.firebase_manager.Utility;
import com.example.maamagic.models.CartItem;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.payments.paymentlauncher.PaymentLauncher;
import com.stripe.android.payments.paymentlauncher.PaymentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartItemFragment extends Fragment {

private CartFirebaseManager cartFirebaseManager;
    private RecyclerView recyclerViewCart;
    private  AppCompatButton btnPayment;
    private CartItemAdapter cartItemAdapter;
    private List<CartItem> cartItemList;
    private  String cartId;
    private OnBottomNavigationListener bottomNavigationListener;
    private  Context context;

    public CartItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnBottomNavigationListener) {
            bottomNavigationListener = (OnBottomNavigationListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnBottomNavigationListener");
        }
    }

    public interface OnBottomNavigationListener {
        void onBottomNavigationItemSelected(int itemId);
    }

    private void moveToOrderFragment() {
        loadFragment(new OrderListFragment(), "OrderListFragment");
        bottomNavigationListener.onBottomNavigationItemSelected(R.id.menu_order);
    }


    private void loadFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
    private void navigateToOrderListFragment() {
        // Create a new instance of OrderListFragment
        OrderListFragment orderListFragment = OrderListFragment.newInstance(cartId);

        // Navigate to the OrderListFragment
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_container, orderListFragment)
                .addToBackStack(null)
                .commit();

        // Notify the activity about the bottom navigation item change
//        navigationChangedListener.onBottomNavigationChanged(R.id.menu_order);
    }

    public static CartItemFragment newInstance(String param1, String param2) {
        CartItemFragment fragment = new CartItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_item, container, false);
        recyclerViewCart = view.findViewById(R.id.recyclerViewCart);
        btnPayment = view.findViewById(R.id.btnPayment);
        cartFirebaseManager = new CartFirebaseManager();
        cartItemList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewCart.setLayoutManager(layoutManager);
        recyclerViewCart.setAdapter(cartItemAdapter);
        context = requireActivity().getApplicationContext();

        retrieveCartItemsFromFirebase();

        btnPayment.setOnClickListener(v -> {
        //    moveToOrderFragment();

        });

        return  view;
    }


    // Get PaymentMethod ID after user enters card details using Stripe Elements



    private void initializeCartRecyclerView(RecyclerView recyclerView, List<CartItem> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        CartItemAdapter cartItemAdapter = new CartItemAdapter(data);
        recyclerView.setAdapter(cartItemAdapter);

        double totalPrice = cartItemAdapter.calculateTotalPrice();
        updatePaymentButton(totalPrice);
    }
    private void updatePaymentButton(double totalPrice) {
        String totalPriceText = getString(R.string.price_format, totalPrice);
        btnPayment.setText(getString(R.string.payment_button_text, totalPriceText));
    }
    private void retrieveCartItemsFromFirebase() {
        cartFirebaseManager.fetchCartItem(new CartFirebaseManager.CartFetchListener() {
            @Override
            public void onCartsFetched(List<CartItem> carts,String cartId) {
                Log.d(TAG, "onCartsFetched: "+ carts);
                initializeCartRecyclerView(recyclerViewCart, carts);
                cartId = cartId;


            }

            @Override
            public void onFetchCartsError(String errorMessage) {
                Utility.showToastShort(getActivity().getApplicationContext(), errorMessage);
                Log.e("FirebaseUtility", "Failed to fetch categories: " + errorMessage);
            }
        });

    }
}