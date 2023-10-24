package com.example.maamagic;

import android.app.Application;
import com.stripe.android.PaymentConfiguration;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PaymentConfiguration.init(getApplicationContext(), "pk_test_your_publishable_key");
    }
}
