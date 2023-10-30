package com.example.maamagic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.maamagic.firebase_manager.UserFirebaseManager;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity extends AppCompatActivity {

    @BindView(R.id.btnStart)
    AppCompatButton btnStart;
    private UserFirebaseManager userFirebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        userFirebaseManager = new UserFirebaseManager(); // Initialize UserFirebaseManager


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                userFirebaseManager.checkAuthenticationState(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        String username = userFirebaseManager.getCurrentUsername();
                        if (username != null) {
                            // User is logged in, start HomeActivity and pass username if needed
                            Intent intent = new Intent(IntroActivity.this, HomePageActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });


            }
        });
    }
}