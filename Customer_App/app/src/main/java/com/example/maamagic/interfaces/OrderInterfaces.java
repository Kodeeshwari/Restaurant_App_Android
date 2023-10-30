package com.example.maamagic.interfaces;

import com.example.maamagic.models.OrderModel;

import java.util.ArrayList;

public interface OrderInterfaces {
    void onOrderFetched(ArrayList<OrderModel> order);
    void onFetchError(String errorMessage);
}
