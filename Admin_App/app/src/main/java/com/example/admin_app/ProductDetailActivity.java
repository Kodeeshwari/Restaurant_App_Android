package com.example.admin_app;

import static android.content.ContentValues.TAG;
import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin_app.Adapter.CategoryAdapter;
import com.example.admin_app.Adapter.ExtraAdapter;
import com.example.admin_app.Models.Extra;
import com.example.admin_app.Models.Product;
import com.example.admin_app.interfaces.firebase.ProductEventListener;
import com.example.admin_app.utils.ImageUtils;
import com.example.admin_app.utils.ProductFirebaseManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity implements ProductEventListener, ExtraAdapter.OnExtraAdapterListener {

    AlertDialog progressDialog;
    private String productId;
    private ImageUtils photoUtil;
    private Uri imageUri;

    private AppCompatButton selectImageButton, btnSave;
    private ImageView imgProduct;
    private RecyclerView recyclerView;
    private EditText edtProductName, edtPrice, edtDescription;
    private CheckBox chkProductAvailable;
    private ArrayList<Extra> extrasList;
    private LinearLayout llyExtraAdd;


    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE_REQUEST = 2;

    private ProductFirebaseManager productFirebaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        edtProductName = findViewById(R.id.edtProductName);
        edtDescription = findViewById(R.id.edtDescription);
        edtPrice = findViewById(R.id.edtProductPrice);
        btnSave = findViewById(R.id.btnSave);
        selectImageButton = findViewById(R.id.selectImageButton);
        imgProduct = findViewById(R.id.imageSelection);
        chkProductAvailable = findViewById(R.id.chkIsAvailable);
        recyclerView = findViewById(R.id.recyclerViewExtras);
        llyExtraAdd = findViewById(R.id.llyExtra);

        productFirebaseManager = new ProductFirebaseManager();
        photoUtil = new ImageUtils(this);

        llyExtraAdd.setOnClickListener(this::addExtras);


        initUI();
    }

    private void addExtras(View view) {
        if (productId != null) {
            AddExtraPopupWindow popupWindow = new AddExtraPopupWindow(ProductDetailActivity.this, productId);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }
    }

    private void initUI() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = getLayoutInflater().inflate(R.layout.custom_progress_dialog, null);
        builder.setView(customLayout);
        builder.setCancelable(false); // Prevent user from dismissing the dialog by tapping outside

        progressDialog = builder.create();

        // Call the method to show the image source dialog

        boolean intentBooleanData = getIntent().getBooleanExtra("IS_EDIT_KEY", false); // Replace "false" with the default value if needed
        productId = getIntent().getStringExtra("PRODUCT_ID");


        if (productId != null && !productId.isEmpty()) {
            Toast.makeText(this, "THIS IS " + productId, Toast.LENGTH_SHORT).show();
            productFirebaseManager.fetchProductById(productId, this);
        }

        selectImageButton.setOnClickListener(this::showImageSourceDialog);
        btnSave.setOnClickListener(this::OnSaveData);

    }

    private void showImageSourceDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select an option");
        String[] options = {"Take Photo", "Select Image"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        photoUtil.capturePhoto();
                        break;
                    case 1:
                        photoUtil.pickPhoto();
                        break;
                }
            }
        });
        builder.show();
    }

    private void OnSaveData(View view) {

    }

    @Override
    public void onProductFetchedByID(Product product) {
        //ProductEventListener.super.onProductFetchedByID(product);
        if (product != null) {
            edtProductName.setText(product.getProductTitle());
            String price = "$ " + String.valueOf(product.getProductPrice());
            edtPrice.setText(price);
            edtDescription.setText(product.getProductDescription());

            if (product.isProductIsAvailable()) {
                chkProductAvailable.setChecked(true);
            } else {
                chkProductAvailable.setChecked(false);
            }
            Utility.loadImage(product.getProductImageURL(), imgProduct);

            Log.d(TAG, "Delete Extra: " + product.getProductExtras().keySet());
            Map<String, Extra> extras = product.getProductExtras();
            if (extras != null && !extras.isEmpty()) {

//                Extra productExtra = extras.get(product.getProductId());
//
//                if (productExtra != null) {
//                    ArrayList<Extra> extraList = new ArrayList<>();
//                    extraList.add(productExtra);
//
//                    initRecyclerView(extraList);
//                }
                ArrayList<Extra> extraList = new ArrayList<>();

                for (Map.Entry<String, Extra> entry : extras.entrySet()) {
                    Extra extra = entry.getValue();
                    extra.setExtraID(entry.getKey());
                    extraList.add(extra);
                }
                initRecyclerView(extraList);
            }
        }
    }

    private void initRecyclerView(ArrayList<Extra> productExtras) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ExtraAdapter extraAdapter = new ExtraAdapter(this, productExtras);
        extraAdapter.setOnDeleteClickListener(this);
        recyclerView.setAdapter(extraAdapter);

    }

    @Override
    public void onDeleteClick(String extraId) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ProductDetailActivity.this);
        builder.setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this Product?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performDeleteExtra(extraId);
                        Toast.makeText(ProductDetailActivity.this, "extra deleted" + extraId, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel the delete action
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void performDeleteExtra(String extraId) {
        if (productId != null)
            productFirebaseManager.deleteExtraById(productId, extraId, this);
    }
}