package com.example.maamagic.firebase_manager;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.maamagic.models.CartItem;
import com.example.maamagic.models.CategoryModel;
import com.example.maamagic.models.ExtraModel;
import com.example.maamagic.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CartFirebaseManager extends FirebaseManager{
    private DatabaseReference cartItemsRef;
    private ArrayList<CartItem> cartItemList;
    String userId;
    String cartId;

    public CartFirebaseManager() {
        cartItemList = new ArrayList<>(); // Initialize the cartItemList here
        cartItemsRef = mDatabase.child("carts");
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

    }

    public String generateUniqueExtraId() {
        // Implement your logic to generate a unique extra ID, for example, using UUID.randomUUID().
        // Return the generated unique extra ID.
        return cartItemsRef.push().getKey();
    }

    public void saveExtraItem(String uniqueExtraId, ExtraModel extraModel) {
    }

    public void addCartItem(String userId, CartItem cartItem) {
        // Add a new cart item to the user's cart in Firebase Database
        cartItemsRef.child(userId).push().setValue(cartItem);
    }

    public void fetchCartItem(CartFetchListener listener){

        cartItemsRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartItemList.clear();
                for (DataSnapshot cartItemSnapshot : dataSnapshot.getChildren()) {
                    CartItem cartItem = cartItemSnapshot.getValue(CartItem.class);
                    if (cartItem != null) {
                        cartId = cartItemSnapshot.getKey();
                        cartItem.setCartId(cartItemSnapshot.getKey());
                        cartItemList.add(cartItem);
                        Log.d(TAG, "onDataChange: "+ cartItemList.toString());
                    }
                }
                notifyCartListenerSuccess(listener, cartItemList,cartId);
                // Here, you can pass the cartId to the listener

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifyCartListenerError(listener, databaseError.getMessage());
            }
        });
    }

    public void updateCartItem(String cartItemId, CartItem updatedCartItem) {
        // Update the cart item in the user's cart in Firebase Database
        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put("itemName", updatedCartItem.getItemName());
        updateFields.put("quantity", updatedCartItem.getQuantity());
        updateFields.put("totalPrice", updatedCartItem.getTotalPrice());
        updateFields.put("itemPrice", updatedCartItem.getItemPrice());

        cartItemsRef.child(userId).child(cartItemId).updateChildren(updateFields);
    }
    private void notifyCartListenerSuccess(CartFetchListener listener, List<CartItem> carts,String cartId) {
        if (listener != null) {
            listener.onCartsFetched(carts,cartId);
        }
    }

    private void notifyCartListenerError(CartFetchListener listener, String errorMessage) {
        if (listener != null) {
            listener.onFetchCartsError(errorMessage);
        }
    }

    public interface CartFetchListener {
        void onCartsFetched(List<CartItem> carts,String cartId);
        void onFetchCartsError(String errorMessage);
    }

    public DatabaseReference getCartItemsRef(String userId) {
        // Get DatabaseReference to the user's cart items in Firebase Database
        return cartItemsRef.child(userId);
    }



}
