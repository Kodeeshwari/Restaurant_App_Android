package com.example.maamagic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maamagic.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private String[] categoryNames;

    public CategoryAdapter(String[] categoryNames) {
        this.categoryNames = categoryNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String categoryName = categoryNames[position];
        holder.bind(categoryName);
    }

    @Override
    public int getItemCount() {
        return categoryNames.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.txtCategoryName);
        }

        public void bind(String categoryName) {
            categoryNameTextView.setText(categoryName);
        }
    }
}



