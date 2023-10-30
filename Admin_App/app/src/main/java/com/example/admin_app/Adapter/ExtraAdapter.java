package com.example.admin_app.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_app.Models.Extra;
import com.example.admin_app.ProductDetailActivity;
import com.example.admin_app.R;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class ExtraAdapter extends RecyclerView.Adapter<ExtraAdapter.ViewHolder> {

    private ArrayList<Extra> extrasList;
    private Context context;
    private OnExtraAdapterListener onExtraAdapterListener;


    public ExtraAdapter(Context context, ArrayList<Extra> productExtras) {
        this.context = context;
        this.extrasList = productExtras;
    }

    @NonNull
    @Override
    public ExtraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_extra,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtraAdapter.ViewHolder holder, int position) {
    Extra extra = extrasList.get(position);
    holder.txtExtraName.setText(extra.getTitle());
    holder.txtExtraPrice.setText(String.valueOf(extra.getPrice()));

    holder.imgDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "Delete Extra :"+"onClick: "+ extra.getExtraID());
            if (onExtraAdapterListener!= null && extra.getExtraID()!= null)
                onExtraAdapterListener.onDeleteClick(extra.getExtraID());
        }
    });
    }

    @Override
    public int getItemCount() {
        return extrasList.size();
    }

    public void setOnDeleteClickListener(OnExtraAdapterListener listener) {
        this.onExtraAdapterListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtExtraName,txtExtraPrice;
        private ImageView imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtExtraName = itemView.findViewById(R.id.txtExtraName);
            txtExtraPrice = itemView.findViewById(R.id.txtExtraPrice);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
    public interface OnExtraAdapterListener {
        void onDeleteClick(String extraId);
    }
}
