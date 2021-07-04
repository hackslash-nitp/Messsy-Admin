package com.hackslash.messsyadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.hackslash.messsyadmin.R;

public class AdminStudentTrackActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_track);
        Intent studentTrackIntent = getIntent();


    }
}