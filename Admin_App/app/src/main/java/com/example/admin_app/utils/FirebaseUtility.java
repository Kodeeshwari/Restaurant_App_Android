package com.example.admin_app.utils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.admin_app.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FirebaseUtility extends FirebaseManager {

    private static FirebaseUtility instance;

    public static synchronized FirebaseUtility getInstance() {
        if (instance == null) {
            instance = new FirebaseUtility();
        }
        return instance;
    }

    public interface LoginCallback {
        void onLoginComplete(boolean success);
    }
    public void signIn(String email, String password, final LoginCallback loginCallback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginCallback.onLoginComplete(true);
                        } else {
                            // Login failed
                            loginCallback.onLoginComplete(false);
                        }
                    }
                });
    }
}
