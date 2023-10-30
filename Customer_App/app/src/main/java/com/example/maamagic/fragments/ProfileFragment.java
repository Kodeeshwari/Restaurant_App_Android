package com.example.maamagic.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.maamagic.R;
import com.example.maamagic.firebase_manager.UserFirebaseManager;
import com.example.maamagic.firebase_manager.Utility;
import com.example.maamagic.interfaces.UserFetchListener;
import com.example.maamagic.models.User;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    private AppCompatButton btnSave,btnDelete;
    EditText edtFirstName,edtLastName,edtEmail,edtPassword,edtConfirmPassword,edtAddress,edtPhoneNumber;

    private UserFirebaseManager userFirebaseManager;
    String name,email,password,phone,address;


    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
    //    ButterKnife.bind(requireActivity());

        btnDelete = view.findViewById(R.id.btnDelete);
        btnSave = view.findViewById(R.id.btnSave);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtConfirmPassword = view.findViewById(R.id.edtConfirmPassword);
        edtAddress = view.findViewById(R.id.edtAddress);
        edtPhoneNumber = view.findViewById(R.id.edtPhoneNumber);
        edtFirstName = view.findViewById(R.id.edtFirstName);
        edtLastName = view.findViewById(R.id.edtLastName);

        initUI();
        setListener();
        return view;
    }

    private void setListener() {
        btnDelete.setOnClickListener(this::onDelete);
        btnSave.setOnClickListener(this::saveUserData);
    }

    private void saveUserData(View view) {
        name = edtFirstName.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        address = edtAddress.getText().toString().trim();
        phone = edtPhoneNumber.getText().toString().trim();
        User user = new User(name,email,password,address,phone);
        userFirebaseManager.updateUserProfile(user);
    }

    private void onDelete(View view) {
        userFirebaseManager.deleteUserProfile();
    }


    private void initUI() {
        edtFirstName.setText("");
        edtLastName.setText("");
        edtEmail.setText("");
       edtPassword.setText("");
       edtConfirmPassword.setText("");
       edtAddress.setText("");
        edtPhoneNumber.setText("");
        userFirebaseManager = new UserFirebaseManager();

        fetchUserInfo();
    }

    private void fetchUserInfo() {
        userFirebaseManager.fetchUserInfo(new UserFetchListener() {
            @Override
            public void onUserFetched(User user) {
                setUI(user);
            }

            @Override
            public void onUserFetchedError(String error) {
                Utility.showToastShort(getContext(),"Error to fetch user data.");
            }
        });
    }

    private void setUI(User user) {
        edtFirstName.setText(user.getName());
        edtLastName.setText("");
        edtEmail.setText(user.getEmail());
        edtPassword.setText(user.getPassword());
        edtConfirmPassword.setText("");
        edtAddress.setText(user.getAddress());
        edtPhoneNumber.setText(user.getPhoneNumber());
    }


}