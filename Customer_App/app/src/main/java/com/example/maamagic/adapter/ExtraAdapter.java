package com.example.maamagic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maamagic.R;
import com.example.maamagic.models.ExtraModel;

import java.util.List;
import java.util.Map;

public class ExtraAdapter extends RecyclerView.Adapter<ExtraAdapter.ViewHolder> {

    private List<ExtraModel> extraList;
    private ExtraSelectionListener extraSelectionListener;


    public ExtraAdapter(List<ExtraModel> extras, ExtraSelectionListener listener) {
        this.extraList = extras;
        this.extraSelectionListener = listener;
    }

    public void toggleSelection(int position) {
        ExtraModel extraModel = extraList.get(position);
        extraModel.setSelected(!extraModel.isSelected());
        notifyItemChanged(position);
        extraSelectionListener.onExtraSelected(extraModel); // Inform the listener about the selection change
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_extra, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExtraModel extraModel = extraList.get(position);

        if (extraModel != null) {
            holder.extraTitle.setText(extraModel.getTitle());
            holder.extraPrice.setText("$" + extraModel.getPrice());
            holder.checkBox.setChecked(extraModel.isSelected());

            holder.itemView.setOnClickListener(v -> {
                holder.checkBox.setChecked(!extraModel.isSelected());
                extraModel.setSelected(!extraModel.isSelected());
                extraSelectionListener.onExtraSelected(extraModel);
            });

            holder.checkBox.setOnClickListener(v -> {
                holder.checkBox.setChecked(!extraModel.isSelected());
                extraModel.setSelected(!extraModel.isSelected());
                extraSelectionListener.onExtraSelected(extraModel);
            });

        }

    }
    public interface ExtraSelectionListener {
        void onExtraSelected(ExtraModel extraModel);
    }

    @Override
    public int getItemCount() {
        return extraList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView extraTitle, extraPrice;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            extraTitle = itemView.findViewById(R.id.txtExtraTitle);
            extraPrice = itemView.findViewById(R.id.txtExtraPrice);
            checkBox = itemView.findViewById(R.id.checkBox);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    toggleSelection(position);
                }
            });

            checkBox.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    toggleSelection(position);
                }
            });
        }
    }
}
