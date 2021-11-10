package com.hackslash.messsyadmin.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.DocumentMask;
import com.hackslash.messsyadmin.Adapters.SectionPageAdapter;
import com.hackslash.messsyadmin.Fragment.AdminActivityFragment;
import com.hackslash.messsyadmin.Fragment.AdminHomeFragment;
import com.hackslash.messsyadmin.Fragment.AdminProfileFragment;
import com.hackslash.messsyadmin.Fragment.AdminWalletFragment;
import com.hackslash.messsyadmin.Model.UserClass;
import com.hackslash.messsyadmin.R;

import java.util.Objects;

public class AdminFragmentContainer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View navigationHeader;
    ImageView headerImageView;
    TextView headerName;
    DocumentReference docref ;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    UserClass user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_fragmentcontainer);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fargment_container, new AdminHomeFragment()).commit();}


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navigationView);

        View hView = navigationView.inflateHeaderView(R.layout.navigation_drawer_header_layout);

//        navigationHeader = navigationView.getHeaderView(R.layout.navigation_drawer_header_layout);
        headerImageView = (ImageView) hView.findViewById(R.id.image_navigation_drawer);
        headerName = (TextView) hView.findViewById(R.id.name_navigation_drawer);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        docref = FirebaseFirestore.getInstance().collection("UserInformation").document(currentUser.getUid());
        String url = "https://th.bing.com/th/id/OIP.vxVLwAKkFacSqbweyL_-twAAAA?pid=ImgDet&w=280&h=280&rs=1";
        if(headerImageView!=null) {
            Glide.with(Objects.requireNonNull(getApplicationContext())).load(url).into(headerImageView);
        }

        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    user = documentSnapshot.toObject(UserClass.class);

                    if(user != null) {
                        if(user.getsName() != null) {
                            headerName.setText(user.getsName());
                        }
                        String sImageUrl = user.getImageUrl();
                        if(sImageUrl != null && !(sImageUrl.equalsIgnoreCase("null"))){
                            Glide.with(Objects.requireNonNull(getApplicationContext())).load(sImageUrl).into(headerImageView);}
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Information doesn't exists ", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




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
                    selectedFragment = new AdminHomeFragment();
                    break;
                case R.id.activity_icon :
                    selectedFragment = new AdminActivityFragment();
                    break;
//                case R.id.wallet_icon :
//                    selectedFragment = new AdminWalletFragment();
//                    break;
                case R.id.profile_icon :
                    selectedFragment = new AdminProfileFragment();
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
                Intent navigationDrawerEditProfileIntent = new Intent(getApplicationContext(), AdminEditProfile.class);
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
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminFragmentContainer.this,LoginActivity.class));
                finishAffinity();
                Toast.makeText(this, "Signing Out", Toast.LENGTH_SHORT).show();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}