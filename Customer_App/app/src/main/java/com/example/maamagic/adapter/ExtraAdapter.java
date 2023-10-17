package com.example.maamagic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maamagic.R;
import com.example.maamagic.models.ExtraModel;

import java.util.ArrayList;

public class ExtraAdapter extends RecyclerView.Adapter<ExtraAdapter.ViewHolder> {

    private ArrayList<ExtraModel> extras;

    public ExtraAdapter(ArrayList<ExtraModel> extras) {
        this.extras = extras;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_extra, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExtraModel extra = extras.get(position);
        holder.extraTitle.setText(extra.getTitle());
        holder.extraPrice.setText("$" + extra.getPrice());
    }

    @Override
    public int getItemCount() {
        return extras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView extraTitle, extraPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            extraTitle = itemView.findViewById(R.id.txtExtraTitle);
            extraPrice = itemView.findViewById(R.id.txtExtraPrice);
        }
    }
}
