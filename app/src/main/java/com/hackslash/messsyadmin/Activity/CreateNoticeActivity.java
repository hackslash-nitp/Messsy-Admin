package com.hackslash.messsyadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hackslash.messsyadmin.Fragment.AdminHomeFragment;
import com.hackslash.messsyadmin.R;

public class CreateNoticeActivity extends AppCompatActivity {
//private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notice);
        /**//*/backButton=(Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNoticeActivity.this, AdminHomeFragment.class);
                startActivity(intent);
//            }
//        });/**/
            }
        }
