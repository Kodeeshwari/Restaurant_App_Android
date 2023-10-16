package com.example.maamagic.adapter;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maamagic.FoodDetailActivity;
import com.example.maamagic.R;
import com.example.maamagic.models.FoodDetail;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    ArrayList<FoodDetail> popularFood;
    public PopularAdapter(ArrayList<FoodDetail> popularFood){
        this.popularFood = popularFood;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent. getContext()).inflate(R.layout.viewholder_popular, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(popularFood.get(position).getTitle());
        holder.price.setText(String.valueOf(popularFood.get(position).getPrice()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(popularFood.get(position).getImgFood(), "drawable", holder.itemView.getContext().getPackageName());

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(holder.itemView.getContext(), FoodDetailActivity.class);
                    intent.putExtra("Object", (Parcelable) popularFood.get(adapterPosition));
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularFood.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        TextView title, price;
        ImageView imgFood;
        AppCompatButton btnAdd;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            imgFood = itemView.findViewById(R.id.imgFood);
            price = itemView.findViewById(R.id.txtPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);

        }
    }
}




