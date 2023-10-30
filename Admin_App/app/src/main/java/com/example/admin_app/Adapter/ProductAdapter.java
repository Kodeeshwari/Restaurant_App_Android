package com.example.admin_app.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_app.Fragments.ProductFragment;
import com.example.admin_app.Models.Product;
import com.example.admin_app.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> productList;
    private  OnProductAdapterListener onProductAdapterListener;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.productList = products;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getProductTitle().trim());
        holder.llyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductAdapterListener!=null){
                    onProductAdapterListener.onProductClick(product.getProductId());
                }
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Log.d(TAG, "product"+ "onClick: "+productId);
                if (onProductAdapterListener != null && product.getProductId() != null) {
                    onProductAdapterListener.onDeleteClick(product.getProductId().toString());
                }
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductAdapterListener.onEditClick(product.getProductId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setOnDeleteClickListener(OnProductAdapterListener listener) {
        this.onProductAdapterListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private ImageView imgEdit,imgDelete,imgProduct;
        private ConstraintLayout llyProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.txtProductName);
            imgEdit = itemView.findViewById(R.id.imgProductEdit);
            imgDelete = itemView.findViewById(R.id.imgProductDelete);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            llyProduct = itemView.findViewById(R.id.llyProduct);
        }
    }
    public interface OnProductAdapterListener {
        void onDeleteClick(String productId);
        void onEditClick(String productId);
        void onProductClick(String productId);
    }
}
