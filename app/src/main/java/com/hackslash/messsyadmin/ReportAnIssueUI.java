package com.hackslash.messsyadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ReportAnIssueUI extends AppCompatActivity {

    Button back , send , uploadImage;
    EditText issue , explaination;
    TextView information;
    String sIssue , sExplaination ;
    private static int PICK_IMAGE = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_an_issue_u_i);
        Intent intentReport = getIntent();

        back = (Button) findViewById(R.id.backButton);
        send = (Button) findViewById(R.id.sendButton);
        uploadImage = (Button) findViewById(R.id.uploadimage);
        issue = (EditText) findViewById(R.id.issue);
        explaination = (EditText) findViewById(R.id.explaination);
        information = (TextView) findViewById(R.id.information);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sIssue = issue.getText().toString();
                sExplaination = explaination.getText().toString();
                Toast.makeText(ReportAnIssueUI.this, "Issue:" + sIssue + "\nExplaination:"+ sExplaination, Toast.LENGTH_SHORT).show();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);
            }
        });

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReportAnIssueUI.this, "Clicked on Data Use Policy", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReportAnIssueUI.this, "Clicked on back button", Toast.LENGTH_SHORT).show();
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

        uploadImage.setText("Image Uploaded");
        uploadImage.setBackgroundResource(R.drawable.uploadimageafteruploaded);
    }
}