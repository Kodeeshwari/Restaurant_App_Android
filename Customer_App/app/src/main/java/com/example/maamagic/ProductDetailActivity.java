package com.example.maamagic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maamagic.adapter.ExtraAdapter;
import com.example.maamagic.firebase_manager.CartFirebaseManager;
import com.example.maamagic.firebase_manager.ProductFirebaseManager;
import com.example.maamagic.firebase_manager.Utility;
import com.example.maamagic.models.CartItem;
import com.example.maamagic.models.ExtraModel;
import com.example.maamagic.models.ProductDetailModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity implements ExtraAdapter.ExtraSelectionListener {

    public static final int REQUEST_CODE_FOOD_DETAIL = 1;

    private TextView txtTitle, txtPrice, txtDescription, txtCount;
    private RecyclerView recyclerViewExtras;
    private Button btnAddToCart;
    private ProductDetailModel productDetail;
    private ArrayList<ExtraModel> extrasList;
    private String productName,imgProductUrl;
    private double productPrice = 0.0;

    private double totalPriceWithExtra=0.0;
    private int itemCount = 1;
    private String userId;


    private ImageView imgPlus, imgMinus,imgProduct;
    private ProductFirebaseManager productFirebaseManager;
    private CartFirebaseManager cartFirebaseManager;

    private List<ExtraModel> selectedExtras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        txtTitle = findViewById(R.id.txtTitle);
        txtCount = findViewById(R.id.txtCount);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        recyclerViewExtras = findViewById(R.id.recyclerViewExtras);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        imgMinus = findViewById(R.id.imgMinus);
        imgPlus = findViewById(R.id.imgPlus);
        imgProduct = findViewById(R.id.imgFood);
        productFirebaseManager = new ProductFirebaseManager();
        cartFirebaseManager = new CartFirebaseManager();

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        selectedExtras = new ArrayList<>();


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("productId")) {
            String productId = intent.getStringExtra("productId");
            fetchProductData(productId);
        } else if (intent != null && intent.hasExtra("cartItem")) {
            CartItem cartItem = (CartItem) getIntent().getSerializableExtra("cartItem");
            assert cartItem != null;
            displayCartItemDetails(cartItem);
        }

    }

    private void displayCartItemDetails(CartItem cartItem) {
        txtTitle.setText(cartItem.getItemName());
        txtCount.setText(String.valueOf(cartItem.getQuantity()));
        txtPrice.setText(String.valueOf(cartItem.getTotalPrice()));
    }

    private void fetchProductData(String productId) {

        productFirebaseManager.fetchProductById(productId, new ProductFirebaseManager.SingleProductFetchListener() {
            @Override
            public void onSingleProductFetched(ProductDetailModel product) {
                displayProductDetails(product);
            }

            @Override
            public void onFetchSingleProductError(String errorMessage) {
                // Handle error when fetching product data fails
                Log.e("Prod", "onFetchProductError: " + errorMessage);
                // Show an error message to the user or handle the error accordingly
            }
        });
    }

    private void displayProductDetails(ProductDetailModel product) {
        if (product != null) {
            updateUI(product);
            setupButtonClickListeners(product);
        } else {
            handleProductNotFound();
        }
    }

    private void updateUI(ProductDetailModel product) {
        productPrice = product.getProductPrice();
        productName = product.getProductTitle();
        imgProductUrl = product.getProductImageURL();
        txtTitle.setText(productName);
        txtPrice.setText(getString(R.string.price_format, product.getProductPrice())); // Assuming you have a string resource for price format
        txtDescription.setText(product.getProductDescription());
        Utility.loadImage(this,product.getProductImageURL(),imgProduct);


        HashMap<String, ExtraModel> extras = product.getProductExtras();
        if (extras != null && !extras.isEmpty()) {
            List<ExtraModel> extraList = new ArrayList<>(extras.values());
            ExtraAdapter extraAdapter = new ExtraAdapter(extraList, ProductDetailActivity.this);
            recyclerViewExtras.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerViewExtras.setAdapter(extraAdapter);
        }
    }


    @Override
    public void onExtraSelected(ExtraModel extraModel) {
        double extraPrice = extraModel.getPrice();
        if (itemCount >= 1) {
            if (extraModel.isSelected()) {
                // If extra is selected, add its price multiplied by itemCount to the total price
                productPrice += (extraPrice * itemCount);
                selectedExtras.add(extraModel);
            } else {
                // If extra is deselected, subtract its price multiplied by itemCount from the total price
                productPrice -= (extraPrice * itemCount);
                selectedExtras.remove(extraModel);
            }
        } else {
            // If no items are selected, set the total price to 0
            productPrice = 0.0;
        }
        updatePriceAndCount();
    }

    private void setupButtonClickListeners(ProductDetailModel product) {
        imgPlus.setOnClickListener(v -> {
            productPrice += product.getProductPrice();
            updateExtrasPrice();
            itemCount++;
            String count = String.valueOf(itemCount);
            txtCount.setText(count);
            updatePriceAndCount();
        });

        imgMinus.setOnClickListener(v -> {
            if (itemCount > 1) {
                productPrice -= product.getProductPrice();
                updateExtrasPrice();
                itemCount--;
                String count = String.valueOf(itemCount);
                txtCount.setText(count);
                updatePriceAndCount();
            }
        });

        if (extrasList != null) {
            ExtraAdapter extraAdapter = new ExtraAdapter(extrasList, this);
            recyclerViewExtras.setAdapter(extraAdapter);
        }
        btnAddToCart.setOnClickListener(v -> {
            if (itemCount > 0) {
                // Create a CartItem object representing the item to be added to the cart
                CartItem cartItem = new CartItem();
                cartItem.setItemName(productName); // Set the product ID
                cartItem.setQuantity(itemCount); // Set the quantity
                cartItem.setTotalPrice(productPrice); // Set the total price
                cartItem.setImgProduct(imgProductUrl);
                double newPrice = productPrice / itemCount;
                String formattedPrice = String.format("%.2f", newPrice);
                double roundedPrice = Double.parseDouble(formattedPrice);
                cartItem.setItemPrice(roundedPrice);

                // add extras with key
                // Create a map to store selected extras with unique keys
                HashMap<String, ExtraModel> extrasMap = new HashMap<>();
                Log.d("CARTKEY", "TEST" + selectedExtras.size());

                for (ExtraModel extra : selectedExtras) {
                    // Generate a unique key for each extra item
                    String extraKey = cartFirebaseManager.generateUniqueExtraId();
                    Log.d("CARTKEY", extraKey + extra.getTitle());
                    extrasMap.put(extraKey, extra);
                }

                cartItem.setSelectedExtras(extrasMap);

                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                cartFirebaseManager.addCartItem(userId, cartItem);
                finish();
            } else {
                Utility.showToastShort(ProductDetailActivity.this, "data did not found.");
            }
        });


    }

    private void updateExtrasPrice() {
        if (extrasList != null) {
            for (ExtraModel extraModel : extrasList) {
                if (extraModel.isSelected()) {
                    productPrice += extraModel.getPrice() * itemCount;
                }
            }
        }
    }

    private void updatePriceAndCount() {
        txtPrice.setText(getString(R.string.price_format, productPrice));
        btnAddToCart.setText("Add Item $"+getString(R.string.price_format, productPrice));

    }

    private void handleProductNotFound() {
        Utility.showToastShort(ProductDetailActivity.this, "data did not found.");
    }



}