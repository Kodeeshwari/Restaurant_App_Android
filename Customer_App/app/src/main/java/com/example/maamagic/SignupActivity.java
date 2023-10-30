package com.example.maamagic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.maamagic.firebase_manager.UserFirebaseManager;
import com.example.maamagic.firebase_manager.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import android.text.TextUtils;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.edtFirstName)
    EditText edtFirstName;

    @BindView(R.id.edtLastName)
    EditText edtLastName;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    @BindView(R.id.edtAddress)
    EditText edtAddress;

    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;

    @BindView(R.id.btnSignUp)
    AppCompatButton btnSignUp;

    private UserFirebaseManager userFirebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this); // ButterKnife binding

        btnSignUp.setOnClickListener(this);
        userFirebaseManager = new UserFirebaseManager();


    }

    @Override
    public void onClick(View v) {
        String firstName = edtFirstName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String name = firstName + " " + lastName;

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Utility.showToastShort(SignupActivity.this, "Password fields cannot be empty");
        } else if (!password.equals(confirmPassword)) {
            Utility.showToastShort(SignupActivity.this, "Passwords do not match");
        } else {
            Log.e("FirebaseUtility", "Sign-up started: ");
            userFirebaseManager.signUp(name, email, password, address, phoneNumber, task -> {
                if (task.isSuccessful()) {
                    Log.d("FirebaseUtility", "Sign-up success: ");
                    Intent intent = new Intent(SignupActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("FirebaseUtility", "Sign-up failed: ");
                    Utility.showToastShort(SignupActivity.this, "Sign up failed");
                }
            });
        }
    }

}