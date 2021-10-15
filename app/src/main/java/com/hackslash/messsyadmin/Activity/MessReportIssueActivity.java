package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackslash.messsyadmin.Model.ComplaintBoxAdapterClass;
import com.hackslash.messsyadmin.Model.ReportIssue;
import com.hackslash.messsyadmin.Model.UserClass;
import com.hackslash.messsyadmin.R;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;


public class MessReportIssueActivity extends AppCompatActivity {

    Button backButton, sendButton, uploadImageButton;
    EditText issueET, explainationET;
    TextView informationTV;
    String sIssue, sExplaination, generatedFilePath;

    String sComplainerName, sComplainerProfileImage;
    
    Dialog dialogIssueReported;

    private Uri imageUri;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference documentReference, documentReference2;
    FirebaseAuth firebaseAuth ;
    FirebaseUser currentUser;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;

    private static final int PICK_IMAGE = 1;

    Bitmap bitmap;





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

        dialogIssueReported = new Dialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        documentReference = FirebaseFirestore.getInstance().collection("Issues").document(currentUser.getUid());
        documentReference2 = FirebaseFirestore.getInstance().collection("UserInformation").document(currentUser.getUid());
        storageReference = storage.getReference().child("images").child(currentUser.getUid());



        documentReference2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists())
                {
                    UserClass user = documentSnapshot.toObject(UserClass.class);
                    sComplainerName = user.getsName();
                    sComplainerProfileImage = user.getImageUrl();
                    
                    if(sComplainerProfileImage.equalsIgnoreCase("null"))
                    {
                        sComplainerProfileImage = "https://th.bing.com/th/id/OIP.vxVLwAKkFacSqbweyL_-twAAAA?pid=ImgDet&w=280&h=280&rs=1";
                    }
                }
                
                else 
                {
                    sComplainerName = "Unknown";
                    sComplainerProfileImage = "https://th.bing.com/th/id/OIP.vxVLwAKkFacSqbweyL_-twAAAA?pid=ImgDet&w=280&h=280&rs=1";
                    Toast.makeText(MessReportIssueActivity.this, "Something went Wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                sComplainerName = "Unknown";
                sComplainerProfileImage = "https://th.bing.com/th/id/OIP.vxVLwAKkFacSqbweyL_-twAAAA?pid=ImgDet&w=280&h=280&rs=1";
                Toast.makeText(MessReportIssueActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
            }
        });
        
        
        

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

                Calendar calendar= Calendar.getInstance();
                String sDate = DateFormat.getDateInstance().format(calendar.getTime());

                ComplaintBoxAdapterClass complain = new ComplaintBoxAdapterClass(sComplainerName, sComplainerProfileImage, sIssue, sExplaination, String.valueOf(imageUri), sDate);

                firebaseFirestore.collection("Issues").document(currentUser.getUid()).set(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        OpenDialog();
                        Toast.makeText(MessReportIssueActivity.this, "Complaint Sent.", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MessReportIssueActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

                if(imageUri != null) {
                    storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(MessReportIssueActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    generatedFilePath = uri.toString();

                                    documentReference.update("imageUrl",generatedFilePath).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(MessReportIssueActivity.this, "Image Url Saved On Firebase", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MessReportIssueActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MessReportIssueActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MessReportIssueActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //OpenDialog();
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

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK)
        {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            }
            catch (IOException e) {
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