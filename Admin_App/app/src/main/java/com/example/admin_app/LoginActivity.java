package com.example.admin_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin_app.utils.FirebaseUtility;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.usernameEditText) EditText usernameEditText;
    @BindView(R.id.passwordEditText) EditText passwordEditText;
    @BindView(R.id.loginButton) Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

//       auth.signOut();
        if (user != null) {
            // User is already logged in, open the dashboard activity
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // Required for starting activity outside of an Activity context
            startActivity(intent);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "login clicked", Toast.LENGTH_SHORT).show();
                String email = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                FirebaseUtility.getInstance().signIn(email, password, new FirebaseUtility.LoginCallback() {
                    @Override
                    public void onLoginComplete(boolean success) {
                        if (success) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            // Login successful, move to the dashboard activity
                            if(user != null){
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle login failure
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}