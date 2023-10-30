package com.example.maamagic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maamagic.R;
import com.example.maamagic.firebase_manager.CartFirebaseManager;
import com.example.maamagic.firebase_manager.Utility;
import com.example.maamagic.models.CartItem;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private List<CartItem> cartItemList;
    private CartFirebaseManager cartFirebaseManager = new CartFirebaseManager();

    private Context context;

    public CartItemAdapter(Context context,List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
        cartFirebaseManager = new CartFirebaseManager();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        if (cartItem != null){
//        holder.itemView.setOnClickListener(v -> {
//            // Create an Intent to open FoodDetailActivity
//            Intent intent = new Intent(v.getContext(), FoodDetailActivity.class);
//            intent.putExtra("cartItem", cartItem); // Pass the cart item details to FoodDetailActivity
//            ((Activity) v.getContext()).startActivityForResult(intent, FoodDetailActivity.REQUEST_CODE_FOOD_DETAIL);
//        });
        holder.bind(cartItem);
        }
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (CartItem cartItem : cartItemList) {
            total += cartItem.getTotalPrice();
        }
        return total;
    }
    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtProductName, txtProductPrice,txtProductQuantity;
        private ImageView plusCartBtn, minusCartBtn,imgCartProduct;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            txtProductQuantity = itemView.findViewById(R.id.txtProductQuantity);
            plusCartBtn = itemView.findViewById(R.id.imgPlusCart);
            minusCartBtn = itemView.findViewById(R.id.imgMinusCart);
            imgCartProduct = itemView.findViewById(R.id.imgCartProduct);

            plusCartBtn.setOnClickListener(v -> {
                int quantity = Integer.parseInt(txtProductQuantity.getText().toString());
                quantity++;
                txtProductQuantity.setText(String.valueOf(quantity));
                updateTotalPrice(quantity);
            });

            minusCartBtn.setOnClickListener(v -> {
                int quantity = Integer.parseInt(txtProductQuantity.getText().toString());
                if (quantity > 1) {
                    quantity--;
                    txtProductQuantity.setText(String.valueOf(quantity));
                    updateTotalPrice(quantity);
                } else {
                    // Handle removing the item from the cart or show a message
                }
            });

        }


        private void updateTotalPrice(int quantity) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                CartItem cartItem = cartItemList.get(position);
                double pricePerItem = cartItem.getItemPrice();
                double totalPrice = quantity * pricePerItem;
                txtProductPrice.setText(String.valueOf(totalPrice));

                // Update the CartItem object with the new quantity and total price
                cartItem.setQuantity(quantity);
                cartItem.setTotalPrice(totalPrice);

                // Update the item in the database
                cartFirebaseManager.updateCartItem(cartItem.getCartId(), cartItem);
            }
        }


        void bind(CartItem cartItem) {
            txtProductName.setText(cartItem.getItemName());
            txtProductPrice.setText(String.valueOf(cartItem.getTotalPrice()));
            txtProductQuantity.setText(String.valueOf(cartItem.getQuantity()));
            Utility.loadImage(context,cartItem.getImgProduct(),imgCartProduct);
        }
    }
}
