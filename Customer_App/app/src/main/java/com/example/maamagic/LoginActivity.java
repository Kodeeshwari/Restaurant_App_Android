package com.example.maamagic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.maamagic.firebase_manager.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.edtEmail)
    EditText editTextEmail;

    @BindView(R.id.edtPassword)
    EditText editTextPassword;

    @BindView(R.id.btnLogin)
    AppCompatButton btnLogin;

    @BindView(R.id.chbRememberMe)
    CheckBox checkBoxRememberMe;

    @BindView(R.id.txtSignup)
    TextView txtSignup;

    private  String email, password;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this); // ButterKnife binding

        firebaseAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(this);

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SignupActivity when "Sign Up" text is clicked
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void signInWithEmailAndPassword(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);
                            startActivity(intent);
                        } else {
                            Utility.showToastShort(LoginActivity.this, "Authentication failed.");
                        }
                    }
                });
    }



    @Override
    public void onClick(View v) {
//         email = editTextEmail.getText().toString().trim();
//            password = editTextPassword.getText().toString().trim();

        email = "vi@gmail.com";
        password = "qwerty";



        if (email.isEmpty() || password.isEmpty()) {
            Utility.showToastShort(LoginActivity.this,"Please fill in all the fields");
        } else {
            signInWithEmailAndPassword(email, password);
        }
    }
}