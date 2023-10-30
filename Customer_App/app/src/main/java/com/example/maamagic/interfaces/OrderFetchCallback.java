package com.example.maamagic.interfaces;

import com.example.maamagic.models.OrderModel;

public interface OrderFetchCallback {
    void onOrderFetched(OrderModel orderModel);
    void onFetchError(String error);
}
