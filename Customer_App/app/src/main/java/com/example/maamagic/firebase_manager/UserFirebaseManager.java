package com.example.maamagic.firebase_manager;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.maamagic.interfaces.UserFetchListener;
import com.example.maamagic.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserFirebaseManager extends FirebaseManager {

    public void signUp(String name, String email, String password, String address, String phoneNumber, OnCompleteListener<AuthResult> onCompleteListener) {
        Log.d("FirebaseUtility", "Sign-up firebase started: ");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        Log.d("FirebaseUtility", "Sign-up done: " + user);

                        // Set display name (username) for the FirebaseUser
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Username updated successfully
                                            addUserToDatabase(user.getUid(), name, email, password, address, phoneNumber);
                                        } else {
                                            // Handle the failure to update the username
                                            Log.e("FirebaseUtility", "Failed to update username: " + task.getException().getMessage());
                                        }
                                    }
                                });
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


    public void fetchUserInfo(UserFetchListener userFetchListener) {
        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    User user = snapshot.getValue(User.class);
                    Log.d(TAG, "User: " + user);
                    userFetchListener.onUserFetched(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                userFetchListener.onUserFetchedError(error.toString());
            }
        });
    }

    public void deleteUserProfile() {
        String userId = mAuth.getCurrentUser().getUid();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // User deleted from Firebase Authentication
                                deleteUserDataFromRealtimeDatabase(userId);
                            } else {
                                // Handle failure
                                Log.w(TAG, "Error deleting user account", task.getException());
                            }
                        }
                    });

        }
    }

    private void deleteUserDataFromRealtimeDatabase(String userId) {
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        dbReference.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // User data deleted from Realtime Database successfully
                        Log.d(TAG, "User data deleted from Realtime Database");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                        Log.w(TAG, "Error deleting user data from Realtime Database", e);
                    }
                });
    }

    public void updateUserProfile(User user) {
        String userId = mAuth.getCurrentUser().getUid(); // Assuming you have the user's unique ID
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        // Create a Map to store updated user details
        Map<String, Object> updatedDetails = new HashMap<>();
        updatedDetails.put("name", user.getName());
        updatedDetails.put("email", user.getEmail());
        updatedDetails.put("address", user.getAddress());
        updatedDetails.put("phoneNumber", user.getPhoneNumber());

        // Update user details in the Realtime Database
        databaseReference.updateChildren(updatedDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // User details updated successfully
                        Log.d(TAG, "User details updated in Realtime Database");
                        updateAuthentication(user.getEmail(), user.getPassword());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                        Log.w(TAG, "Error updating user details in Realtime Database", e);
                    }
                });

    }

    private void updateAuthentication(String email, String password) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> emailTask) {
                        if (emailTask.isSuccessful()) {
                            // Email address updated successfully, now update the password
                            user.updatePassword(password)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> passwordTask) {
                                            if (passwordTask.isSuccessful()) {
                                                // Password updated successfully
                                                Log.d(TAG, "USER " + " Email and password updated successfully");
                                            } else {
                                                // If updating password fails, display a message to the user
                                                Log.w(TAG, "USER " + " Error updating password", passwordTask.getException());
                                            }
                                        }
                                    });
                        } else {
                            // If updating email fails, display a message to the user
                            Log.w(TAG, "Error updating email address", emailTask.getException());
                        }
                    }
                });

    }

    public String getCurrentUsername() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String username = user.getDisplayName();
            if (username != null) {
                return username;
            } else {
                // User does not have a display name set
                return null;
            }
        } else {
            // User is not logged in
            return null;
        }
    }
    public String getCurrentUserUID(){
        return Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
    }

    public void checkAuthenticationState(FirebaseAuth.AuthStateListener authStateListener) {
        mAuth.addAuthStateListener(authStateListener);
    }


}
