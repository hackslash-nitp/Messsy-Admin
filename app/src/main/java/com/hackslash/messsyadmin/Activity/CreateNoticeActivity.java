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
    private Button backButton,createNotice;
    String subject,description;
    EditText subEdit,desEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notice);
        backButton = (Button) findViewById(R.id.backButton);
        subEdit=findViewById(R.id.subject_edit_text);
        desEdit=findViewById(R.id.notice_description_edit);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        createNotice=findViewById(R.id.create_notice);
        createNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotice();
            }
        });

    }

    private void createNotice() {
        subject=subEdit.getText().toString().trim();
        description=desEdit.getText().toString().trim();
    }
}
