package com.hackslash.messsyadmin.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.hackslash.messsyadmin.Fragment.MessActivityFragment;
import com.hackslash.messsyadmin.Fragment.MessHomeFragment;
import com.hackslash.messsyadmin.Fragment.MessProfileFragment;
import com.hackslash.messsyadmin.Fragment.MessWalletFragment;
import com.hackslash.messsyadmin.R;

public class MessFragmentContainer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mess_fragmentcontainer);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fargment_container, new MessHomeFragment()).commit();}


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{ super.onBackPressed();}
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

            getSupportFragmentManager().beginTransaction().replace(R.id.fargment_container,selectedFragment).commit();
            return true;

        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.edit_profile:

                Intent navigationDrawerEditProfileIntent = new Intent(getApplicationContext(), MessEditProfile.class);
                startActivity(navigationDrawerEditProfileIntent);
                break;

            case R.id.notification:
                Intent navigationDrawerNotificationIntent = new Intent(getApplicationContext(), NavigationDrawerNotificationActivity.class);
                startActivity(navigationDrawerNotificationIntent);
                break;

            case R.id.report_issue:
                Intent navigationDrawerReportIssueIntent = new Intent(getApplicationContext(), NavigationDrawerReportIssueActivity.class);
                startActivity(navigationDrawerReportIssueIntent);
                break;

            case R.id.app_updates:
                Intent navigationDrawerAppUpdatesIntent = new Intent(getApplicationContext(), NavigationDrawerAppUpdateActivity.class);
                startActivity(navigationDrawerAppUpdatesIntent);
                break;

            case R.id.contact_us:
                Intent navigationDrawerContactUsIntent = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(navigationDrawerContactUsIntent);
                break;
            case R.id.sign_out:
                Toast.makeText(this, "Signing Out", Toast.LENGTH_SHORT).show();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}