package com.example.admin_app.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_app.Models.Category;
import com.example.admin_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<Category> categories;
    private Context context;
    private OnCategoryAdapterListener onCategoryAdapterListener;


    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }


    public void setOnDeleteClickListener(OnCategoryAdapterListener listener) {
        this.onCategoryAdapterListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);

        Picasso.get()
                .load(category.getCategoryImage())  // Image URL from Firebase
                .placeholder(R.drawable.pizza)  // Placeholder image while loading
                .resize(100, 100)
                .centerCrop()
                .error(R.drawable.pizza)  // Image to show in case of an error
                .into(holder.categoryImage);
        Log.d("Firebase", "onBindViewHolder: " + category.getCategoryImage());

        holder.categoryName.setText(category.getCategoryName());
        holder.categoryDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCategoryAdapterListener != null) {
                    onCategoryAdapterListener.onDeleteClick(category.getCategoryId());
                }
            }
        });

        holder.categoryEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCategoryAdapterListener!=null){
                    onCategoryAdapterListener.onEditClick(category.getCategoryId());
                }
            }
        });

        holder.categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCategoryAdapterListener != null) {
                    onCategoryAdapterListener.onCategoryClick(category.getCategoryId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName;
        ImageView categoryDelete;
        ImageView categoryEdit;
        ConstraintLayout categoryLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryDelete = itemView.findViewById(R.id.cat_delete);
            categoryEdit = itemView.findViewById(R.id.cat_edit);
            categoryLayout = itemView.findViewById(R.id.categoryLinearLayout);
        }

    }

    // Define an interface to handle delete actions
    public interface OnCategoryAdapterListener {
        void onDeleteClick(String categoryId);
        void onEditClick(String categoryId);
        void onCategoryClick(String categoryId);
    }

}
