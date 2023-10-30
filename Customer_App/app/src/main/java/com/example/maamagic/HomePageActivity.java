package com.example.maamagic;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.maamagic.databinding.ActivityHomePageBinding;
import com.example.maamagic.fragments.CartItemFragment;
import com.example.maamagic.fragments.HomeFragment;
import com.example.maamagic.fragments.OrderListFragment;
import com.example.maamagic.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Stack;

public class HomePageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener , CartItemFragment.OnBottomNavigationListener {

    private ActivityHomePageBinding binding;
    private FragmentManager fragmentManager;
    private Stack<Fragment> fragmentStack = new Stack<>();



    private BottomNavigationView bottomNavigationView; // Remove ButterKnife binding for this view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView = findViewById(R.id.bottom_navigation); // Initialize the view here
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentStack = new Stack<>();

        // Set HomeFragment as the initial fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), "HomeFragment");
        }
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.frame_container, new HomeFragment())
//                    .commit();
//        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment selectedFragment = null;
//
//        switch (item.getItemId()) {
//            case R.id.menu_home:
//                selectedFragment = new HomeFragment();
//                break;
//            case R.id.menu_cart:
//                selectedFragment = new CartItemFragment();
//                break;
//            case R.id.menu_order:
//                selectedFragment = new OrderListFragment();
//                break;
//            case R.id.menu_profile:
//                selectedFragment = new ProfileFragment();
//                break;
//        }
//
//        if (selectedFragment != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.frame_container, selectedFragment)
//                    .addToBackStack(null)
//                    .commit();
//            return true;
//        }
//
//        return false;
//    }
//}
//
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        String tag;
        int itemId = item.getItemId();

        switch (item.getItemId()) {
            case R.id.menu_cart:
                fragment = new CartItemFragment();
                tag = "CartFragment";
                break;
            case R.id.menu_order:
                fragment = new OrderListFragment();
                tag = "OrderFragment";
                break;
            case R.id.menu_profile:
                fragment = new ProfileFragment();
                tag = "ProfileFragment";
                break;
            default:
                fragment = new HomeFragment(); // Default fragment
                tag = "HomeFragment";
                break;
        }

        loadFragment(fragment, tag);
        bottomNavigationView.getMenu().findItem(itemId).setChecked(true);
        return true;

    }

    private void loadFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment, tag);
        transaction.addToBackStack(tag); // Add the fragment to the back stack with a tag
        transaction.commit();

        Log.d(TAG, "loadFragment: "+fragmentStack.size());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loadFragment(new HomeFragment(),"HomeFragment");
        bottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
    }

    @Override
    public void onBottomNavigationItemSelected(int itemId) {
        bottomNavigationView.getMenu().findItem(itemId).setChecked(true);
    }
}