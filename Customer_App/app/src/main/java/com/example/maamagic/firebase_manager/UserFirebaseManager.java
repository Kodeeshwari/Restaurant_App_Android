package com.example.maamagic.firebase_manager;

import android.util.Log;

import com.example.maamagic.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class UserFirebaseManager extends FirebaseManager {

    public void signUp(String name, String email, String password, String address, String phoneNumber, OnCompleteListener<AuthResult> onCompleteListener) {
        Log.d("FirebaseUtility", "Sign-up firebase started: ");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        Log.d("FirebaseUtility", "Sign-up done: " + user);
                        addUserToDatabase(user.getUid(), name, email, password, address, phoneNumber);
                    }
                }).addOnFailureListener(e -> {
                    // Handle the sign-up failure here
                    Log.e("FirebaseUtility", "Sign-up failed: " + e.getMessage());
                });
    }

    public void signIn(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener);
    }

    private void addUserToDatabase(String userId, String name, String email, String password, String address, String phoneNumber) {
        User user = new User(name, email, password, address, phoneNumber);
        Log.d("FirebaseUtility", "User added started to db");

        mDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(aVoid -> Log.d("FirebaseUtility", "User added to database"))
                .addOnFailureListener(e -> Log.e("FirebaseUtility", "Failed to add user to database: " + e.getMessage()));
    }


}
