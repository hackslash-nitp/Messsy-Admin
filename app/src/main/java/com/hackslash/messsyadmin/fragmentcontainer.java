package com.hackslash.messsyadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class fragmentcontainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentcontainer);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fargment_container, new fragment_home()).commit();
    }
    private  BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){

                case R.id.home_icon :
                    selectedFragment = new fragment_home();
                    break;
                case R.id.activity_icon :
                    selectedFragment = new fragment_activity();
                    break;
                case R.id.wallet_icon :
                    selectedFragment = new fragment_wallet();
                    break;
                case R.id.profile_icon :
                    selectedFragment = new fragment_profile();
                    break;


            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fargment_container,selectedFragment).commit();
            return true;

        }
    };


}