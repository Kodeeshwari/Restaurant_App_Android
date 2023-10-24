package com.example.maamagic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maamagic.FoodDetailActivity;
import com.example.maamagic.R;
import com.example.maamagic.models.ProductDetailModel;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    ArrayList<ProductDetailModel> productList;
    public PopularAdapter(Context applicationContext, ArrayList<ProductDetailModel> productList){
        this.productList = productList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent. getContext()).inflate(R.layout.viewholder_popular, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(productList.get(position).getProductTitle());
        holder.price.setText(String.valueOf(productList.get(position).getProductPrice()));

     //   int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(productList.get(position).getImage_url(), "drawable", holder.itemView.getContext().getPackageName());

        holder.llyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    String productId = productList.get(adapterPosition).getProductId();
                    Intent intent = new Intent(holder.itemView.getContext(), FoodDetailActivity.class);
            //        intent.putExtra("Object", productList.get(position).getProductId());
                    intent.putExtra("productId", productId);
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int adapterPosition = holder.getAdapterPosition();
//                if (adapterPosition != RecyclerView.NO_POSITION) {
//                    Intent intent = new Intent(holder.itemView.getContext(), FoodDetailActivity.class);
//                    intent.putExtra("Object", (Parcelable) productList.get(adapterPosition));
//                    holder.itemView.getContext().startActivity(intent);
               // }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        TextView title, price;
        ImageView imgFood;
        AppCompatButton btnAdd;
        LinearLayout llyProduct;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            imgFood = itemView.findViewById(R.id.imgFood);
            price = itemView.findViewById(R.id.txtPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            llyProduct = itemView.findViewById(R.id.llyProduct);

        }
    }
}




