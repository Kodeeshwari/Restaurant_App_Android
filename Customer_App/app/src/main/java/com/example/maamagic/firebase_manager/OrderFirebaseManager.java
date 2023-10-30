package com.example.maamagic.firebase_manager;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.maamagic.interfaces.OrderFetchCallback;
import com.example.maamagic.interfaces.OrderInsertCallback;
import com.example.maamagic.interfaces.OrderInterfaces;
import com.example.maamagic.models.CategoryModel;
import com.example.maamagic.models.OrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class OrderFirebaseManager extends FirebaseManager{


    public void fetchOrder(OrderInterfaces orderInterfaces){
        mDatabase.child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Gson gson = new Gson();
                String data1 = gson.toJson(snapshot.getValue());
                Log.e("Order", "ALL" + data1);
                ArrayList<OrderModel> order = new ArrayList<>();
                OrderModel orderModel;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    orderModel = dataSnapshot.getValue(OrderModel.class);
                    assert orderModel != null;
                    orderModel.setOrderId(dataSnapshot.getKey());
                    order.add(orderModel);
                        String data = gson.toJson(orderModel.getCartItems());
                             Log.e("Order", "SINGLE" + data);
                }
            orderInterfaces.onOrderFetched(order);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            orderInterfaces.onFetchError(error.toString());
            }
        });
    }

    public void fetchSingleNodeOrder(String orderId, OrderFetchCallback callback){

        mDatabase.child("orders").child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    OrderModel orderModel = snapshot.getValue(OrderModel.class);
                    if (orderModel!= null){
                        callback.onOrderFetched(orderModel);
                    } else {
                        callback.onFetchError("Failed to parse order data");
                    }
                } else {
                    callback.onFetchError("Order not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFetchError(error.getMessage());
            }
        });

    }


    public void insertOrder(OrderModel orderModel, OrderInsertCallback callback) {
        DatabaseReference ordersRef = mDatabase.child("orders");
        String orderId = ordersRef.push().getKey();
        if (orderId != null) {
            ordersRef.child(orderId).setValue(orderModel)
                    .addOnSuccessListener(aVoid -> callback.onOrderInserted(orderId))
                    .addOnFailureListener(e -> callback.onInsertOrderError(e.getMessage()));
        } else {
            callback.onInsertOrderError("Failed to generate order ID");
        }
    }



}
