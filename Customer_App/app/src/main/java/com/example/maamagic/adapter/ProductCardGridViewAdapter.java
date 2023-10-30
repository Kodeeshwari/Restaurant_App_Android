package com.example.maamagic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maamagic.ProductDetailActivity;
import com.example.maamagic.R;
import com.example.maamagic.firebase_manager.Utility;
import com.example.maamagic.models.ProductDetailModel;

import java.util.ArrayList;

public class ProductCardGridViewAdapter extends BaseAdapter {

    private ArrayList<ProductDetailModel> productList;
    private Context context;

    public ProductCardGridViewAdapter(Context context, ArrayList<ProductDetailModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.viewholder_popular, parent, false);
        }

        TextView title = convertView.findViewById(R.id.txtTitle);
        ImageView imgFood = convertView.findViewById(R.id.imgFood);
        TextView price = convertView.findViewById(R.id.txtPrice);
        TextView btnAdd = convertView.findViewById(R.id.btnAdd);
        View llyProduct = convertView.findViewById(R.id.llyProduct);

        title.setText(productList.get(position).getProductTitle());
        price.setText(String.valueOf(productList.get(position).getProductPrice()));
        Utility.loadImage(context, productList.get(position).getProductImageURL(), imgFood);

        llyProduct.setOnClickListener(v -> {
            String productId = productList.get(position).getProductId();
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("productId", productId);
            context.startActivity(intent);
        });

        btnAdd.setOnClickListener(v -> {
            // Handle add button click event if needed
            Toast.makeText(context, "Add button clicked for position: " + position, Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
