package com.example.maamagic;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.maamagic.interfaces.PaymentKeyListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class StripePaymentUtility {

    private static final String TAG = "StripePaymentUtility";
    private static final String BACKEND_URL = "https://stripe-payment-server-4nvx.onrender.com";


    public static void fetchPaymentIntent(Context context, double paymentAmount, PaymentKeyListener paymentKeyListener) {
        String shoppingCartContent = "{\"items\": [ {\"id\":\"xl-tshirt\", \"amount\":" + paymentAmount + "}]}";

        final RequestBody requestBody = RequestBody.create(
                shoppingCartContent,
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(BACKEND_URL + "/payment-sheet")
                .post(requestBody)
                .build();

        new OkHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        showAlert(context, "Failed to load data", "Error: " + e.toString());
                    }

                    @Override
                    public void onResponse(@NonNull Call call,@NonNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            showAlert(
                                    context,
                                    "Failed to load page",
                                    "Error: " + response.toString()
                            );
                        } else {
                            final JSONObject responseJson = parseResponse(response.body());
                            String paymentIntentClientSecret = responseJson.optString("paymentIntent");
                            paymentKeyListener.onPaymentTokenFetchResult(paymentIntentClientSecret);
                        }
                    }
                });
    }

    private static JSONObject parseResponse(ResponseBody responseBody) {
        if (responseBody != null) {
            try {
                return new JSONObject(responseBody.string());
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error parsing response", e);
            }
        }

        return new JSONObject();
    }

    private static void showAlert(Context context, String title, @Nullable String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .show();
    }


}
