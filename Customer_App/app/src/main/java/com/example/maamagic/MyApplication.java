package com.example.maamagic;

import android.app.Application;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PaymentConfiguration.init(
                getApplicationContext(),
                getString(R.string.stripe_publish_key)
        );
    }
}