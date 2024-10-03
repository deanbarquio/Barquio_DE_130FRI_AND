package com.capstone.navbarapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.capstone.navbarapplication.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static final int SERVICES_ACTIVITY_REQUEST_CODE = 1;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up full-screen mode and hide action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new ProfileFragment());

        binding.bottomNavigation.setSelectedItemId(R.id.nav_profile_item);

        binding.bottomNavigation.setVisibility(View.GONE);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.bottomNavigation.setVisibility(View.VISIBLE);
                // Handle navigation based on the intent extra
                handleIntentNavigation();
            }
        }, 1000);

        binding.bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_calculator_item) {
                    selectedFragment = new CalculatorFragment();
                } else if (item.getItemId() == R.id.nav_menu_item) {
                    selectedFragment = new MenuFragment();
                } else if (item.getItemId() == R.id.nav_profile_item) {
                    selectedFragment = new ProfileFragment();
                } else {
                    return false;
                }

                // Replace the current fragment with the selected one
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
        });
    }

    private void handleIntentNavigation() {
        String navigateTo = getIntent().getStringExtra("navigateTo");
        if (navigateTo != null) {
            switch (navigateTo) {
                case "CalculatorFragment":
                    replaceFragment(new CalculatorFragment());
                    break;
                case "MenuFragment":
                    replaceFragment(new MenuFragment());
                    break;
                case "ProfileFragment":
                    replaceFragment(new ProfileFragment());
                    break;

                default:
                    replaceFragment(new ProfileFragment());  // Default to ProfileFragment if no extra is provided
                    break;
            }
        } else {
            replaceFragment(new ProfileFragment());
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
