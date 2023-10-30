package com.example.maamagic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maamagic.OrderDetailActivity;
import com.example.maamagic.R;
import com.example.maamagic.models.OrderItemModel;
import com.example.maamagic.models.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private ArrayList<OrderModel> orderItemList;
    private  Context context;

    public OrderListAdapter(Context context, ArrayList<OrderModel> orderItemList){
        this.orderItemList = orderItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel orderModel  = orderItemList.get(position);

        holder.txtReceiptNo.setText(orderModel.getReceiptNo());
        holder.txtOrderStatus.setText(orderModel.getOrderStatus());
        holder.txtOrderTime.setText(orderModel.getOrderCollectedTime());

        holder.llyOrderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderModelReceiptNo = orderModel.getOrderId(); // Replace 'getOrderId()' with the actual method to get orderId from OrderModel

                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ORDER_ID", orderModelReceiptNo);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView  txtReceiptNo,txtOrderStatus,txtOrderTime;
        LinearLayout llyOrderCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            txtReceiptNo = itemView.findViewById(R.id.txtReceiptNo);
            txtOrderTime = itemView.findViewById(R.id.txtOrderTime);
            llyOrderCard = itemView.findViewById(R.id.llyOrderCard);

        }
    }
}
