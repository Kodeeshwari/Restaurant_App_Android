package com.example.maamagic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maamagic.R;
import com.example.maamagic.models.CartItem;

import java.util.ArrayList;

public class OrderCartListAdapter extends RecyclerView.Adapter<OrderCartListAdapter.ViewHolder> {

    private ArrayList<CartItem> cartItemArrayList;
    private Context context;

    public OrderCartListAdapter(Context context, ArrayList<CartItem> cartItemArrayList){
        this.context = context;
        this.cartItemArrayList = cartItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_order_cart, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItemArrayList.get(position);

        holder.txtProductName.setText(cartItem.getItemName());
        String finalPrice = String.valueOf(cartItem.getTotalPrice());
        holder.txtProductPrice.setText(finalPrice);
        String quantity = String.valueOf(cartItem.getQuantity());
        holder.txtProductQuantity.setText(quantity);

    }

    @Override
    public int getItemCount() {
        return cartItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductName, txtProductQuantity, txtProductPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtOrderProductName);
            txtProductQuantity = itemView.findViewById(R.id.txtOrderProductQuantity);
            txtProductPrice = itemView.findViewById(R.id.txtOrderProductPrice);
        }
    }
}
