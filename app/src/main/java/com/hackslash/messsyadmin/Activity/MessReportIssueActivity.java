package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackslash.messsyadmin.Model.ReportIssue;
import com.hackslash.messsyadmin.R;



public class MessReportIssueActivity extends AppCompatActivity {

    Button backButton, sendButton, uploadImageButton;
    EditText issueET, explainationET;
    TextView informationTV;
    String sIssue, sExplaination;

    ImageView sImage;

    Dialog dialogIssueReported;

    private Uri downloadURL;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth ;
    FirebaseUser currentUser;
    StorageReference mstorageReference;





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
        currentUser = firebaseAuth.getCurrentUser();
        mstorageReference= FirebaseStorage.getInstance().getReference().child("images");




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


                ReportIssue ReportInfo = new ReportIssue(sIssue, sExplaination, String.valueOf(downloadURL));


                firebaseFirestore.collection("Issues").document(currentUser.getUid()).set(ReportInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        //OpenDialog();
                        Toast.makeText(MessReportIssueActivity.this, "Complaint Sent.", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MessReportIssueActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

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

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choosePicture();

            }

        });

    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            Uri imageUri = data.getData();
            try {
                Bitmap currentImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                sImage.setImageBitmap(currentImage);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            StorageReference photoRef = mstorageReference.child(imageUri.getLastPathSegment());

            photoRef.putFile(imageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    downloadURL = urlTask.getResult();
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MessReportIssueActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }

        uploadImageButton.setText("Image Uploaded");
        uploadImageButton.setBackgroundResource(R.drawable.uploadimageafteruploaded);
    }



/*

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

 */

}