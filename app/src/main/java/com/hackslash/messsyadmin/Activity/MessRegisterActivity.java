package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackslash.messsyadmin.Model.UserClass;
import com.hackslash.messsyadmin.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MessRegisterActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    Button loginButton, addImageButton, registerButton, visibilityButton;
    EditText nameET, emailAddET, mobileNumberET, hostelNameET, passwordET; // ET stands for edittext
    String  sName , sEmail , sMobile , sHostelName, sPassword ,sDesignation = "Mess Member" , sImageUrl = "null",sUId ="null"; // s stands for string
    Boolean hasVisible = false;
    ImageView profileImage;
    Dialog dialogSuccesfullyRegistered;
    private static int PICK_IMAGE = 1 ;
    UserClass userInfo;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    Uri selectedImage;
    FirebaseStorage storage;
    String ImagePath;
    Bitmap bitmap;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DocumentReference documentReference;
    StorageReference storageReference;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_register);
        Intent intent = getIntent();

        loginButton = (Button) findViewById(R.id.login);
        addImageButton = (Button) findViewById(R.id.addImage);
        registerButton = (Button) findViewById(R.id.register);
        visibilityButton = (Button) findViewById(R.id.visibility);
        nameET = (EditText) findViewById(R.id.name);
        emailAddET = (EditText) findViewById(R.id.EmailAddress);
        mobileNumberET = (EditText) findViewById(R.id.mobilenumber);
        hostelNameET = (EditText) findViewById(R.id.hostelname);
        passwordET = (EditText) findViewById(R.id.Password);
        profileImage = (ImageView) findViewById(R.id.image);
        dialogSuccesfullyRegistered = new Dialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        storage=FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(MessRegisterActivity.this);
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("Please Wait");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = nameET.getText().toString();
                sEmail = emailAddET.getText().toString();
                sMobile = mobileNumberET.getText().toString();
                sHostelName = hostelNameET.getText().toString();
                sPassword = passwordET.getText().toString();

                if (sName.isEmpty()) {
                    nameET.setError("Name is required");
                    return;
                }
                if (sEmail.isEmpty()) {
                    emailAddET.setError("Email Address is required");
                    return;

                }
                if (sMobile.isEmpty()) {
                    mobileNumberET.setError("Mobile Number is required");
                    return;

                }
                if (sHostelName.isEmpty()) {
                    hostelNameET.setError("Hostel Name is required");
                    return;

                }
                if (sPassword.isEmpty()) {
                    passwordET.setError("Not a valid Password");
                    return;

                }

//                UserClass userInfo = new UserClass(sName, sEmail, sMobile, sHostelName, sDesignation, sImageUrl);


                if (currentUser != null) {
                    Intent sendToMessFragmentContainerIntent = new Intent(getApplicationContext(), MessFragmentContainer.class);
                    startActivity(sendToMessFragmentContainerIntent);
                } else{
                    firebaseAuth.createUserWithEmailAndPassword(sEmail, sPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressDialog.show();
                            currentUser=FirebaseAuth.getInstance().getCurrentUser();
                            assert currentUser != null;
                            sUId = currentUser.getUid();
                            userInfo = new UserClass(sName, sEmail, sMobile, sHostelName, sDesignation, sImageUrl , sUId);
                            firebaseFirestore.collection("UserInformation").document(currentUser.getUid()).set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MessRegisterActivity.this, "Information uploaded on firebase", Toast.LENGTH_SHORT).show();
                                    if(selectedImage != null){
                                        storageReference = storage.getReference().child("images").child(currentUser.getUid());
                                        documentReference = FirebaseFirestore.getInstance().collection("UserInformation").document(currentUser.getUid());
                                        storageReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Toast.makeText(MessRegisterActivity.this, "Image Saved", Toast.LENGTH_SHORT).show();
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        ImagePath = uri.toString();
                                                        documentReference.update("imageUrl", ImagePath).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(MessRegisterActivity.this, "Url Saved", Toast.LENGTH_SHORT).show();
                                                                progressDialog.dismiss();
                                                                OpenDialog();

                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(MessRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                                            }
                                                        });
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(MessRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(MessRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        OpenDialog();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MessRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MessRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        visibilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasVisible) {
                    passwordET.setTransformationMethod(new PasswordTransformationMethod());
                    hasVisible = false;
                } else {
                    passwordET.setTransformationMethod(null);
                    hasVisible = true;

                }
            }
        });

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessRegisterActivity.this, "Adding Image", Toast.LENGTH_SHORT).show();
                Intent intent1= new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent1,"Add Image"),PICK_IMAGE);
            }
        });

    }


    private void OpenDialog(){
        dialogSuccesfullyRegistered.setContentView(R.layout.successfully_registered_dialog);
        dialogSuccesfullyRegistered.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccesfullyRegistered.setCancelable(false);
        dialogSuccesfullyRegistered.setCanceledOnTouchOutside(false);

        Button btnDone = dialogSuccesfullyRegistered.findViewById(R.id.btn_Done);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialogSuccesfullyRegistered.dismiss();
                Intent sendToMessFragmentContainerIntent = new Intent(getApplicationContext(), MessFragmentContainer.class);
                startActivity(sendToMessFragmentContainerIntent);
            }
        });

        dialogSuccesfullyRegistered.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            selectedImage = data.getData();
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                profileImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}