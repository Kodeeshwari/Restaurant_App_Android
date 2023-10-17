package com.example.admin_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.admin_app.Fragments.CategoryFragment;
import com.example.admin_app.Fragments.OrderFragment;
import com.example.admin_app.Fragments.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(navListener);
        // Set the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new OrderFragment()).commit();
    }

    private BottomNavigationView.OnItemSelectedListener navListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            Toast.makeText(DashboardActivity.this, String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();
            switch (item.getItemId()) {
                case R.id.menu_order:
                    selectedFragment = new OrderFragment();
                    break;
                case R.id.menu_catogory:
                    selectedFragment = new CategoryFragment();
                    break;
                case R.id.menu_user:
                    selectedFragment = new UserFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
            return true;
        }
    };

}