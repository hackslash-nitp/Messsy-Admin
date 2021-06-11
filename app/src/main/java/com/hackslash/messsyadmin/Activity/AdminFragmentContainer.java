package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hackslash.messsyadmin.Fragment.AdminActivityFragment;
import com.hackslash.messsyadmin.Fragment.AdminHomeFragment;
import com.hackslash.messsyadmin.Fragment.AdminProfileFragment;
import com.hackslash.messsyadmin.Fragment.AdminWalletFragment;
import com.hackslash.messsyadmin.R;

public class AdminFragmentContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_fragmentcontainer);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fargment_container, new AdminHomeFragment()).commit();
    }
    private  BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){

                case R.id.home_icon :
                    selectedFragment = new AdminHomeFragment();
                    break;
                case R.id.activity_icon :
                    selectedFragment = new AdminActivityFragment();
                    break;
                case R.id.wallet_icon :
                    selectedFragment = new AdminWalletFragment();
                    break;
                case R.id.profile_icon :
                    selectedFragment = new AdminProfileFragment();
                    break;


            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fargment_container,selectedFragment).commit();
            return true;

        }
    };


}