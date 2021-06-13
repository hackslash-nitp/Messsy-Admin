package com.hackslash.messsyadmin.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hackslash.messsyadmin.R;

public class ActivityNavigationDrawerReportIssue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_report_issue);
        Intent navigationDrawerReportIssueIntent = getIntent();

    }
}