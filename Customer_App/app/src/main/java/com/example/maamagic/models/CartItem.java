package com.example.maamagic.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class CartItem implements Serializable, Parcelable {
    private String cartId;
    private String itemName;
    private int quantity;
    private double totalPrice;
    private double itemPrice;

    private HashMap<String,ExtraModel>  selectedExtras; // List of selected ExtraModel objects


    public  CartItem(){}

    public CartItem(String cartId, String itemName, int quantity, double totalPrice, double itemPrice, HashMap<String, ExtraModel> selectedExtras) {
        this.cartId = cartId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.itemPrice = itemPrice;
        this.selectedExtras = selectedExtras;
    }

    protected CartItem(Parcel in) {
        cartId = in.readString();
        itemName = in.readString();
        quantity = in.readInt();
        totalPrice = in.readDouble();
        itemPrice = in.readDouble();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public HashMap<String, ExtraModel> getSelectedExtras() {
        return selectedExtras;
    }

    public void setSelectedExtras(HashMap<String, ExtraModel> selectedExtras) {
        this.selectedExtras = selectedExtras;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(cartId);
        dest.writeString(itemName);
        dest.writeInt(quantity);
        dest.writeDouble(totalPrice);
        dest.writeDouble(itemPrice);
    }
}
