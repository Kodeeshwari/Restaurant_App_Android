package com.example.maamagic.interfaces;

public interface OrderInsertCallback {
    void onOrderInserted(String orderId);
    void onInsertOrderError(String errorMessage);
}
