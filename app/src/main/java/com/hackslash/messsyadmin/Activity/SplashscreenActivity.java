package com.hackslash.messsyadmin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hackslash.messsyadmin.R;

public class SplashscreenActivity extends AppCompatActivity {

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread t= new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{

                    onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                    boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

                    if(isFirstTime){

                        SharedPreferences.Editor editor = onBoardingScreen.edit();
                        editor.putBoolean("firstTime", false);
                        editor.commit();

                        Intent i=new Intent(getApplicationContext(), OnBoarding.class);
                        startActivity(i);
                        finish();

                    }

                    else{
                        Intent i=new Intent(getApplicationContext(), AdminFragmentContainer.class);
                        startActivity(i);
                        finish();
                    }

                }
            }

        };t.start();
    }
}