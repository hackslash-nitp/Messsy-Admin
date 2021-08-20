package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackslash.messsyadmin.Model.ReportIssue;
import com.hackslash.messsyadmin.R;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MessReportIssueActivity extends AppCompatActivity {

    Button backButton, sendButton, uploadImageButton;
    EditText issueET, explainationET;
    TextView informationTV;
    String sIssue , sExplaination ;
    ImageView sImage;

    Dialog dialogIssueReported;

    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private static int PICK_IMAGE = 1 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_reportissue);
        Intent intentReport = getIntent();


        backButton = (Button) findViewById(R.id.backButton_report);
        sendButton = (Button) findViewById(R.id.sendButton);
        uploadImageButton = (Button) findViewById(R.id.uploadimage);
        issueET = (EditText) findViewById(R.id.issue);
        explainationET = (EditText) findViewById(R.id.explaination);
        informationTV = (TextView) findViewById(R.id.information);


        firebaseAuth = FirebaseAuth.getInstance();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sIssue = issueET.getText().toString();
                sExplaination = explainationET.getText().toString();

                if(sIssue.isEmpty())
                {
                    issueET.setError("Subject Required!");
                    return;
                }

                if(sExplaination.isEmpty())
                {
                    explainationET.setError("Please describe your issue.");
                    return;
                }

                ReportIssue ReportInfo = new ReportIssue(sIssue, sExplaination, sImage);

                currentUser = FirebaseAuth.getInstance().getCurrentUser();

                firebaseFirestore.collection("Issues").document(currentUser.getUid()).set(ReportInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        OpenDialog();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MessReportIssueActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
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

                Toast.makeText(MessReportIssueActivity.this, "Clicked on Data Use Policy", Toast.LENGTH_SHORT).show();

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

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
                sImage.setImageBitmap(bitmap);
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

    public void OpenDialog(){
        dialogIssueReported.setContentView(R.layout.report_sent_dialog);
        dialogIssueReported.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogIssueReported.setCancelable(false);
        dialogIssueReported.setCanceledOnTouchOutside(false);

        Button btnDone = dialogIssueReported.findViewById(R.id.btn_Done);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialogIssueReported.dismiss();
                Intent sendToMessFragmentContainerIntent = new Intent(getApplicationContext(), MessFragmentContainer.class);
                startActivity(sendToMessFragmentContainerIntent);
            }
        });

        dialogIssueReported.show();

    }

}