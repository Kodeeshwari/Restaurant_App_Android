package com.example.maamagic.firebase_manager;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.maamagic.models.SliderItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SliderFirebaseManager extends FirebaseManager {
    private DatabaseReference sliderItemsRef;
    private List<SliderItem> sliderItemList;
    private static final String TAG = "SliderFirebaseManager";

    public SliderFirebaseManager() {
        sliderItemList = new ArrayList<>();
        sliderItemsRef = mDatabase.child("slider");
    }

    public void fetchSliderItems(SliderFetchListener listener) {
        sliderItemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sliderItemList.clear();
                for (DataSnapshot sliderItemSnapshot : dataSnapshot.getChildren()) {
                    SliderItem sliderItem = sliderItemSnapshot.getValue(SliderItem.class);
                    if (sliderItem != null) {
                        sliderItemList.add(sliderItem);
                    }
                }
                notifySliderListenerSuccess(listener, sliderItemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                notifySliderListenerError(listener, databaseError.getMessage());
            }
        });
    }

    private void notifySliderListenerSuccess(SliderFetchListener listener, List<SliderItem> sliderItems) {
        if (listener != null) {
            listener.onSliderItemsFetched(sliderItems);
        }
    }

    private void notifySliderListenerError(SliderFetchListener listener, String errorMessage) {
        if (listener != null) {
            listener.onFetchSliderItemsError(errorMessage);
        }
    }

    public interface SliderFetchListener {
        void onSliderItemsFetched(List<SliderItem> sliderItems);
        void onFetchSliderItemsError(String errorMessage);
    }
}
