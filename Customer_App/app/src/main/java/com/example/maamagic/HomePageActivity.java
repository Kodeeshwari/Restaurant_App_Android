package com.example.maamagic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.maamagic.fragments.CartItemFragment;
import com.example.maamagic.fragments.HomeFragment;
import com.example.maamagic.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView; // Remove ButterKnife binding for this view

 //   @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation); // Initialize the view here
   //     bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Set HomeFragment as the initial fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), "HomeFragment");
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                loadFragment(new HomeFragment(), "HomeFragment");
                return true;
            case R.id.menu_cart:
                loadFragment(new CartItemFragment(), "CartItemFragment");
                return true;
            case R.id.menu_profile:
                loadFragment(new ProfileFragment(), "ProfileFragment");
                return true;
        }
        return false;
    }

    private void loadFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment, tag)
                .commit();
    }

}