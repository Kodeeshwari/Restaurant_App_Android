package com.example.admin_app.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {
    protected FirebaseAuth mAuth;
    protected DatabaseReference mDatabase;

    // Singleton instance

    // Private constructor for Singleton pattern
    protected FirebaseManager() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }




}
