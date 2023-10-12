package com.example.maamagic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.maamagic.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class HomePageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener {
    HomeFragment homeFragment;
    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        bottomNavigationView.setSelectedItemId(R.id.menu_home);


       homeFragment = new HomeFragment();
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container,homeFragment)
                        .commit();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
    }
}