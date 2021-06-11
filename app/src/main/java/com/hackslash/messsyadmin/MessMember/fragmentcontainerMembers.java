package com.hackslash.messsyadmin.MessMember;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hackslash.messsyadmin.HomePageMembersUI;
import com.hackslash.messsyadmin.R;

public class fragmentcontainerMembers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container_members);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_members, new HomePageMembersUI()).commit();
    }
    private  BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){

                case R.id.home_icon :
                    selectedFragment = new HomePageMembersUI();
                    break;
                case R.id.activity_icon :
                    selectedFragment = new ActivityFragmentMembers();
                    break;
                case R.id.wallet_icon :
                    selectedFragment = new walletFragmentMembers();
                    break;
                case R.id.profile_icon :
                    selectedFragment = new ProfileFragmentMembers();
                    break;


            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_members,selectedFragment).commit();
            return true;

        }
    };
}


