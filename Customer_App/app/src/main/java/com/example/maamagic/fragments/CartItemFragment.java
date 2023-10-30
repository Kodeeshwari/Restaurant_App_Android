package com.example.maamagic.fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maamagic.R;
import com.example.maamagic.StripePaymentUtility;
import com.example.maamagic.adapter.CartItemAdapter;
import com.example.maamagic.firebase_manager.CartFirebaseManager;
import com.example.maamagic.firebase_manager.OrderFirebaseManager;
import com.example.maamagic.firebase_manager.UserFirebaseManager;
import com.example.maamagic.firebase_manager.Utility;
import com.example.maamagic.interfaces.OrderInsertCallback;
import com.example.maamagic.interfaces.PaymentKeyListener;
import com.example.maamagic.models.CartItem;
import com.example.maamagic.models.OrderModel;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CartItemFragment extends Fragment implements PaymentKeyListener {

    private CartFirebaseManager cartFirebaseManager;
    private UserFirebaseManager userFirebaseManager;
    private RecyclerView recyclerViewCart;
    private AppCompatButton btnPayment;
    private CartItemAdapter cartItemAdapter;
    private List<CartItem> cartItemList;
    private String cartId;
    private OnBottomNavigationListener bottomNavigationListener;
    private Context context;
    private PaymentSheet paymentSheet;
    private HashMap<String, CartItem> cartItemMapData;

    private OrderFirebaseManager orderFirebaseManager;

    private String totalPriceText;
    private String paymentId;


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

    @Override
    public void onPaymentTokenFetchResult(String paymentTokenResult) {
        PaymentSheet.Configuration configuration = new PaymentSheet.Configuration("Maa_s magic");
        paymentId = paymentTokenResult;
        // Present Payment Sheet
        paymentSheet.presentWithPaymentIntent(paymentTokenResult, configuration);

    }

    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Utility.showToastShort(requireContext(),"Payment Success");
            insertOrderDetails(paymentSheetResult);
            Log.i(TAG, "Payment complete!");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Log.i(TAG, "Payment canceled!");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Throwable error = ((PaymentSheetResult.Failed) paymentSheetResult).getError();
            Log.i(TAG, "Payment failed" + error.getLocalizedMessage());
        }
    }

    private void insertOrderDetails(PaymentSheetResult paymentSheetResult) {

        OrderModel orderModel = new OrderModel();
        orderModel.setPaymentStatus("success");
        orderModel.setCartItems(cartItemMapData);
        orderModel.setOrderStatus("Pending");
        orderModel.setUserId(userFirebaseManager.getCurrentUserUID());
        orderModel.setOrderCollectedTime("");
        orderModel.setPaymentType("CARD");
        orderModel.setCardNo("**** **** **** 4242");
        orderModel.setFinalPrice(Double.parseDouble(totalPriceText));
        orderModel.setReceiptNo(generateReceiptNumber());
        orderModel.setTransactionId(paymentId);
        orderModel.setTransactionTime(getCurrentDateTime());
        orderModel.setOrderCollectedTime(getTimeAfter60Minutes());

        orderFirebaseManager.insertOrder(orderModel, new OrderInsertCallback() {
            @Override
            public void onOrderInserted(String orderId) {
                Utility.showToastShort(requireContext(),"Thank you for Order");
clearCart();
            }

            @Override
            public void onInsertOrderError(String errorMessage) {

            }
        });
    }

    private void clearCart() {
        cartFirebaseManager.removeCartItem(userFirebaseManager.getCurrentUserUID());
    }


    public interface OnBottomNavigationListener {
        void onBottomNavigationItemSelected(int itemId);
    }


    public static CartItemFragment newInstance(String param1, String param2) {
        CartItemFragment fragment = new CartItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        orderFirebaseManager = new OrderFirebaseManager();
        userFirebaseManager = new UserFirebaseManager();

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

            StripePaymentUtility.fetchPaymentIntent(requireContext(), 300, this);
        });

        return view;
    }


    private void initializeCartRecyclerView(RecyclerView recyclerView, List<CartItem> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        CartItemAdapter cartItemAdapter = new CartItemAdapter(requireActivity().getApplicationContext(), data);
        recyclerView.setAdapter(cartItemAdapter);

        double totalPrice = cartItemAdapter.calculateTotalPrice();
        updatePaymentButton(totalPrice);
    }

    private void updatePaymentButton(double totalPrice) {
        totalPriceText = getString(R.string.price_format, totalPrice);
        btnPayment.setText(getString(R.string.payment_button_text, totalPriceText));
    }

    private void retrieveCartItemsFromFirebase() {
        cartFirebaseManager.fetchCartItem(new CartFirebaseManager.CartFetchListener() {
            @Override
            public void onCartsFetched(List<CartItem> carts, String cartId, HashMap<String, CartItem> cartItemMap) {
                Log.d(TAG, "onCartsFetched: " + carts);
                initializeCartRecyclerView(recyclerViewCart, carts);
                cartId = cartId;
                cartItemMapData = cartItemMap;
            }

            @Override
            public void onFetchCartsError(String errorMessage) {
                Utility.showToastShort(getActivity().getApplicationContext(), errorMessage);
                Log.e("FirebaseUtility", "Failed to fetch categories: " + errorMessage);
            }
        });

    }


    // Function to get current time in "MM/dd/yyyy HH:mm" format
    public String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    // Function to generate a unique receipt number
    public String generateReceiptNumber() {
        // Assuming a unique receipt number can be generated using random and current time
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmm");
        Date currentDate = new Date();
        String dateTimePart = dateFormat.format(currentDate);

        // Generate a random 4-digit number
        Random random = new Random();
        int randomNumber = random.nextInt(10000);

        // Format the random number as a 4-digit string
        String randomPart = String.format("%04d", randomNumber);

        // Concatenate the date/time part and random part to create the receipt number
        return dateTimePart + randomPart;
    }

    // Function to get the time 60 minutes from the current time in "MM/dd/yyyy HH:mm" format
    public String getTimeAfter60Minutes() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date currentDate = new Date();

        // Calculate 60 minutes in milliseconds (60 * 60 * 1000)
        long timeAfter60MinutesInMillis = currentDate.getTime() + (60 * 60 * 1000);
        Date timeAfter60Minutes = new Date(timeAfter60MinutesInMillis);

        return dateFormat.format(timeAfter60Minutes);
    }


}


