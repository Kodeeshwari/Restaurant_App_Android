package com.example.maamagic.models;
import java.util.ArrayList;
import java.util.Map;

public class OrderModel {
    private String cardNo;
    private String orderId;
    private Map<String, CartItem> cartItems;
    private Map<String, ExtraModel> extraItems;
    private double finalPrice;
    private String orderCollectedTime;
    private String orderStatus;
    private String paymentStatus;
    private String paymentType;
    private String receiptNo;
    private String transactionId;
    private String transactionTime;
    private String userId;

    public OrderModel() {
        // Default constructor required for Firebase
    }

    public OrderModel(String cardNo, String orderId, Map<String, CartItem> cartItems, Map<String, ExtraModel> extraItems, double finalPrice, String orderCollectedTime, String orderStatus, String paymentStatus, String paymentType, String receiptNo, String transactionId, String transactionTime, String userId) {
        this.cardNo = cardNo;
        this.orderId = orderId;
        this.cartItems = cartItems;
        this.extraItems = extraItems;
        this.finalPrice = finalPrice;
        this.orderCollectedTime = orderCollectedTime;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
        this.receiptNo = receiptNo;
        this.transactionId = transactionId;
        this.transactionTime = transactionTime;
        this.userId = userId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Map<String, CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<String, CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Map<String, ExtraModel> getExtraItems() {
        return extraItems;
    }

    public void setExtraItems(Map<String, ExtraModel> extraItems) {
        this.extraItems = extraItems;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }


    public String getOrderCollectedTime() {
        return orderCollectedTime;
    }

    public void setOrderCollectedTime(String orderCollectedTime) {
        this.orderCollectedTime = orderCollectedTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
