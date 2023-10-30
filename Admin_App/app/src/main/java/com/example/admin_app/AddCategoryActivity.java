package com.example.admin_app;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admin_app.Models.Category;
import com.example.admin_app.interfaces.firebase.CategoryEventListener;
import com.example.admin_app.utils.CategoryFirebaseManager;
import com.example.admin_app.utils.ImageUtils;


public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener, CategoryEventListener {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE_REQUEST = 2;
    private ImageView categoryImageView;
    private EditText categoryNameEditText;
    private EditText categoryDescriptionEditText;
    private Button selectImageButton;
    private Button addCategoryButton;
    private Button editCategoryButton;
    private Uri imageUri;
    private CategoryFirebaseManager categoryFirebaseManager;
    AlertDialog progressDialog;

    private String categoryId;


    ImageUtils photoUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        categoryFirebaseManager = new CategoryFirebaseManager();

        // Initialize views and UI components
        initUI();


        // Initialize CategoryFirebaseManager

        photoUtil = new ImageUtils(this);


    }


    private void initUI() {
        categoryImageView = findViewById(R.id.imageSelection);
        categoryNameEditText = findViewById(R.id.editTextCategoryName);
        categoryDescriptionEditText = findViewById(R.id.editTextCategoryDesc);
        selectImageButton = findViewById(R.id.selectImageButton);
        addCategoryButton = findViewById(R.id.buttonAddCategory);
        editCategoryButton = findViewById(R.id.buttonEditCategory);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = getLayoutInflater().inflate(R.layout.custom_progress_dialog, null);
        builder.setView(customLayout);
        builder.setCancelable(false); // Prevent user from dismissing the dialog by tapping outside

        progressDialog = builder.create();

        // Call the method to show the image source dialog

        boolean intentBooleanData = getIntent().getBooleanExtra("IS_EDIT_KEY", false); // Replace "false" with the default value if needed
        categoryId = getIntent().getStringExtra("CATEGORY_ID");

        if (intentBooleanData) {
            addCategoryButton.setVisibility(View.GONE);
            editCategoryButton.setVisibility(View.VISIBLE);
        }

        if (categoryId != null && !categoryId.isEmpty()) {
            Toast.makeText(this, "THIS IS "+categoryId, Toast.LENGTH_SHORT).show();
            categoryFirebaseManager.fetchCategoryById(categoryId, this);
        }


        // Set click listeners
        selectImageButton.setOnClickListener(this);
        addCategoryButton.setOnClickListener(this);
        editCategoryButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.selectImageButton) {
            showImageSourceDialog();
        } else if (v.getId() == R.id.buttonAddCategory) {
            performInsertOperationWithImage(imageUri);
        } else if (v.getId() == R.id.buttonEditCategory) {
            if (categoryId != null && !categoryId.isEmpty()) {
                performEditOperationWithImage(imageUri);
            }
        }

    }

    private void performEditOperationWithImage(Uri pickedPhotoUri) {
        progressDialog.show();

        final Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryNameEditText.getText().toString());
        category.setCategoryDescription(categoryDescriptionEditText.getText().toString());

        categoryFirebaseManager.uploadCategoryWithImage(category, pickedPhotoUri, this, true);


    }

    private void showImageSourceDialog() {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Handle the captured photo using photoUtil.getPhotoUri()
            Uri capturedPhotoUri = photoUtil.getPhotoUri();
            Log.e("IMAGE", capturedPhotoUri + "");


            // Do something with the captured photo URI
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            // Handle the picked photo using data.getData()
            if (data.getData() != null) {
                imageUri = ImageUtils.compressAndResizeImage(this, data.getData(), 1080, 1080);

                Log.e("IMAGE", imageUri + "");

                categoryImageView.setImageURI(imageUri);
            } else {
                Toast.makeText(this, "Error to load image", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void performInsertOperationWithImage(Uri pickedPhotoUri) {
        progressDialog.show();

        final Category category = new Category();
        category.setCategoryName(categoryNameEditText.getText().toString());
        category.setCategoryDescription(categoryDescriptionEditText.getText().toString());

        categoryFirebaseManager.uploadCategoryWithImage(category, pickedPhotoUri, this, false);
    }

    @Override
    public void onCategoryAdded(String categoryId) {
        CategoryEventListener.super.onCategoryAdded(categoryId);
        Log.e("IMAGE", categoryId);
        progressDialog.hide();
        finish();
    }

    @Override
    public void onCategoryAddError(String errorMessage) {
        CategoryEventListener.super.onCategoryAddError(errorMessage);
    }

    @Override
    public void onCategoryEdited(String categoryId) {
        CategoryEventListener.super.onCategoryEdited(categoryId);
        Log.e("IMAGE", categoryId);
        progressDialog.hide();
        finish();

    }

    @Override
    public void onCategoryEditError(String errorMessage) {
        CategoryEventListener.super.onCategoryEditError(errorMessage);
    }

    @Override
    public void onCategoryFetched(Category category) {
        CategoryEventListener.super.onCategoryFetched(category);
        categoryNameEditText.setText(category.getCategoryName());
        categoryDescriptionEditText.setText(category.getCategoryDescription());
//        categoryImageView.setImageURI(Uri.parse(category.getCategoryImage()));
    }
}