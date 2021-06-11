package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hackslash.messsyadmin.Fragment.MessActivityFragment;
import com.hackslash.messsyadmin.Fragment.MessHomeFragment;
import com.hackslash.messsyadmin.Fragment.MessProfileFragment;
import com.hackslash.messsyadmin.Fragment.MessWalletFragment;
import com.hackslash.messsyadmin.R;

public class MessFragmentContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mess_fragmentcontainer);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_members, new MessHomeFragment()).commit();
    }
    private  BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){

                case R.id.home_icon :
                    selectedFragment = new MessHomeFragment();
                    break;
                case R.id.activity_icon :
                    selectedFragment = new MessActivityFragment();
                    break;
                case R.id.wallet_icon :
                    selectedFragment = new MessWalletFragment();
                    break;
                case R.id.profile_icon :
                    selectedFragment = new MessProfileFragment();
                    break;


            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_members,selectedFragment).commit();
            return true;

        }
    };
}


