package com.example.maamagic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maamagic.R;
import com.example.maamagic.firebase_manager.Utility;
import com.example.maamagic.models.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<CategoryModel> categoryList;
    private  Context context;

    public CategoryAdapter(Context context , ArrayList categoryNames) {
        this.categoryList = categoryNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel categoryModel = categoryList.get(position);
        holder.bind(categoryModel,context);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryNameTextView;
        private ImageView categoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.txtCategoryName);
            categoryImage = itemView.findViewById(R.id.imgFood);
        }

        public void bind(CategoryModel category,Context context) {
            categoryNameTextView.setText(category.getCategoryName());
            if (category.getCategoryImage() != null) {
          //      Utility.loadImage(context, category.getCategoryImage(), categoryImage);
//                Glide.with(context).load(R.drawable.pizza).into(categoryImage);
            } else {
                Utility.showToastShort(context,"image did not find");
                Glide.with(context).load(R.drawable.pizza).into(categoryImage);
            }
    }
    }
}



