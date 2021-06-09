package com.hackslash.messsyadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class activity_ReportAnIssueUI extends AppCompatActivity {

    Button backButton, sendButton, uploadImageButton;
    EditText issueET, explainationET;
    TextView informationTV;
    String sIssue , sExplaination ;
    private static int PICK_IMAGE = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_an_issue_u_i);
        Intent intentReport = getIntent();

        backButton = (Button) findViewById(R.id.backButton);
        sendButton = (Button) findViewById(R.id.sendButton);
        uploadImageButton = (Button) findViewById(R.id.uploadimage);
        issueET = (EditText) findViewById(R.id.issue);
        explainationET = (EditText) findViewById(R.id.explaination);
        informationTV = (TextView) findViewById(R.id.information);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sIssue = issueET.getText().toString();
                sExplaination = explainationET.getText().toString();
                Toast.makeText(activity_ReportAnIssueUI.this, "Issue:" + sIssue + "\nExplaination:"+ sExplaination, Toast.LENGTH_SHORT).show();
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);
            }
        });

        informationTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_ReportAnIssueUI.this, "Clicked on Data Use Policy", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_ReportAnIssueUI.this, "Clicked on back button", Toast.LENGTH_SHORT).show();
            }
        });





    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode== PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        uploadImageButton.setText("Image Uploaded");
        uploadImageButton.setBackgroundResource(R.drawable.uploadimageafteruploaded);
    }
}