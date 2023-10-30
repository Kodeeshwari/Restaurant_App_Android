package com.example.maamagic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.maamagic.R;
import com.example.maamagic.models.SliderItem;

import java.util.List;

public class BannerSliderAdapter extends RecyclerView.Adapter<BannerSliderAdapter.SliderViewHolder> {

    private List<SliderItem> sliderItems;

    public BannerSliderAdapter(List<SliderItem> sliderItems) {
        this.sliderItems = sliderItems;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        SliderItem sliderItem = sliderItems.get(position);

        // Load image using Glide (assuming imageUrl is present in SliderItem)
        Glide.with(holder.itemView.getContext())
                .load(sliderItem.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.logo) // Optional placeholder image while loading
                .apply(new RequestOptions()
                .downsample(DownsampleStrategy.AT_MOST)
                .format(DecodeFormat.PREFER_RGB_565) // Prefer RGB_565 format to save memory
                .encodeQuality(70))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sliderImage);
        }
    }
}
