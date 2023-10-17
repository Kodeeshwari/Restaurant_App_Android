package com.example.maamagic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.maamagic.models.ProductDetailModel;

public class FoodDetailActivity extends AppCompatActivity {

    private TextView txtTitle, txtPrice, txtDescription;
    private RecyclerView recyclerViewExtras;
    private Button btnAddToCart;
    private ProductDetailModel productDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        txtTitle = findViewById(R.id.txtTitle);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        recyclerViewExtras = findViewById(R.id.recyclerViewExtras);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        // Receive the ProductDetailModel object from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Object")) {
            productDetail = (ProductDetailModel) intent.getSerializableExtra("Object");

            // Set data to your views
            if (productDetail != null) {
                txtTitle.setText(productDetail.getTitle());
                txtPrice.setText("$" + productDetail.getPrice());
                txtDescription.setText(productDetail.getDescription());

                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     }
                });
            }
        }

    }
}