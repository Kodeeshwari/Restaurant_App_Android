package com.example.maamagic;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.maamagic.adapter.OrderCartListAdapter;
import com.example.maamagic.adapter.OrderListAdapter;
import com.example.maamagic.firebase_manager.OrderFirebaseManager;
import com.example.maamagic.firebase_manager.Utility;
import com.example.maamagic.interfaces.OrderFetchCallback;
import com.example.maamagic.models.CartItem;
import com.example.maamagic.models.OrderModel;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {


    private TextView txtReceiptNo, txtOrderTimeStamp, txtOrderStatus, txtPaymentStatus, txtPaymentType, txtCardNumber, txtPaymentTime, txtFinalAmount;
    private RecyclerView recyclerViewCart;
    private  Intent intent;
    private  String orderId;
    private OrderFirebaseManager orderFirebaseManager;
    private ArrayList<CartItem> cartItemsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        txtReceiptNo = findViewById(R.id.txtReceiptNo);
        txtOrderTimeStamp = findViewById(R.id.txtTimeStamp);
        txtOrderStatus = findViewById(R.id.txtOrderStatus);
        txtPaymentStatus = findViewById(R.id.txtPaymentStatus);
        txtPaymentType = findViewById(R.id.txtPaymentType);
        txtCardNumber = findViewById(R.id.txtCardNumber);
        txtPaymentTime = findViewById(R.id.txtPaymentTime);
        txtFinalAmount = findViewById(R.id.txtFinalAmount);
        recyclerViewCart = findViewById(R.id.recyclerViewOrderCart);

        initView();

    }

    private void initView() {
        intent = new Intent();
        intent = getIntent();
        cartItemsList = new ArrayList<>();
        orderFirebaseManager = new OrderFirebaseManager();
        if (intent!=null){
            orderId = intent.getStringExtra("ORDER_ID");
            fetchOrderDetail();
        }

    }

    private void fetchOrderDetail() {

        orderFirebaseManager.fetchSingleNodeOrder(orderId, new OrderFetchCallback() {
            @Override
            public void onOrderFetched(OrderModel orderModel) {
                setUI(orderModel);
            }

            @Override
            public void onFetchError(String error) {
                Utility.showToastShort(OrderDetailActivity.this,error);
            }
        });

    }

    private void setUI(OrderModel orderModel) {
        txtReceiptNo.setText(orderModel.getReceiptNo());
        txtOrderTimeStamp.setText(orderModel.getOrderCollectedTime());
        txtOrderStatus.setText(orderModel.getOrderStatus());

        txtPaymentStatus.setText(orderModel.getPaymentStatus());
        txtPaymentType.setText(orderModel.getPaymentType());
        txtCardNumber.setText(orderModel.getCardNo());
        txtPaymentTime.setText(orderModel.getOrderCollectedTime());
        String finalPrice = String.valueOf(orderModel.getFinalPrice());
        txtFinalAmount.setText(finalPrice);
        ArrayList<CartItem> cartItemsList = new ArrayList<>(orderModel.getCartItems().values());
        initialiseOrderCartAdapter(cartItemsList);


    }

    private void initialiseOrderCartAdapter(ArrayList<CartItem> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(layoutManager);

        OrderCartListAdapter orderCartAdapter = new OrderCartListAdapter(this,data);
        recyclerViewCart.setAdapter(orderCartAdapter);


    }
}