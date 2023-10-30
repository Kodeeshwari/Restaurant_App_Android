package com.example.maamagic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maamagic.R;
import com.example.maamagic.firebase_manager.Utility;
import com.example.maamagic.interfaces.ProductCardEventListener;
import com.example.maamagic.models.ProductDetailModel;

import java.util.ArrayList;

public class ProductCardListAdapater extends RecyclerView.Adapter<ProductCardListAdapater.ViewHolder> {

    ArrayList<ProductDetailModel> productList;
    Context context;
    private ProductCardEventListener productCardEventListener;

    public ProductCardListAdapater(Context applicationContext, ArrayList<ProductDetailModel> productList, ProductCardEventListener listener) {
        this.productList = productList;
        this.context = applicationContext;
        this.productCardEventListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent. getContext()).inflate(R.layout.viewholder_popular, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDetailModel product = productList.get(position);

        holder.title.setText(productList.get(position).getProductTitle());
        holder.price.setText(String.valueOf(productList.get(position).getProductPrice()));
        Utility.loadImageWithCircle(context,productList.get(position).getProductImageURL(),holder.imgFood);

        holder.llyProduct.setOnClickListener(v -> {
            if (productCardEventListener != null) {
                productCardEventListener.onItemClick(product.getProductId());
            }
        });

        holder.btnAdd.setOnClickListener(v -> {
            if (productCardEventListener != null) {
                productCardEventListener.onAddButtonClick(product.getProductId());
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
        TextView btnAdd;
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




