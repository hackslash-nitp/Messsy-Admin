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
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackslash.messsyadmin.Model.UserClass;
import com.hackslash.messsyadmin.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminRegisterActivity extends AppCompatActivity {
    Button loginButton, addImageButton, registerButton, visibilityButton;
    EditText nameET, emailAddET, mobileNumberET, passwordET;
    String  sName , sEmail , sMobile , sPassword ,sHostelName = "null", sDesignation = "Admin",sImageUrl = "null";
    Boolean hasVisible = false;
    private static int PICK_IMAGE = 1 ;
    Dialog dialogSuccesfullyRegistered;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    Uri selectedImage;
    DocumentReference documentReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    String ImagePath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        Intent intent = getIntent();

        loginButton = (Button) findViewById(R.id.login);
        addImageButton = (Button) findViewById(R.id.addImage);
        registerButton = (Button) findViewById(R.id.register);
        visibilityButton = (Button) findViewById(R.id.visibility);
        nameET = (EditText) findViewById(R.id.name);
        emailAddET = (EditText) findViewById(R.id.EmailAddress);
        mobileNumberET = (EditText) findViewById(R.id.mobilenumber);
        passwordET = (EditText) findViewById(R.id.Password);
        dialogSuccesfullyRegistered = new Dialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference().child("images").child(currentUser.getUid());
        documentReference=FirebaseFirestore.getInstance().collection("User Information").document(currentUser.getUid());


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = nameET.getText().toString();
                sEmail = emailAddET.getText().toString();
                sMobile = mobileNumberET.getText().toString();
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

                if (sPassword.isEmpty()) {
                    passwordET.setError("Not a valid Password");
                    return;

                }

                UserClass userInfo = new UserClass(sName, sEmail, sMobile, sHostelName, sDesignation, sImageUrl);

                if (currentUser != null) {
                    Intent sendToAdminFragmentContainerIntent = new Intent(getApplicationContext(), AdminFragmentContainer.class);
                    startActivity(sendToAdminFragmentContainerIntent);
                } else{
                    firebaseAuth.createUserWithEmailAndPassword(sEmail, sPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                        currentUser=FirebaseAuth.getInstance().getCurrentUser();
                            firebaseFirestore.collection("UserInformation").document(currentUser.getUid()).set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    OpenDialog();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdminRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    storageReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AdminRegisterActivity.this, "Image Saved", Toast.LENGTH_SHORT).show();
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    ImagePath=uri.toString();
                                    documentReference.update("imageUrl", ImagePath).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(AdminRegisterActivity.this, "Url Saved", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AdminRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdminRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

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
                if(hasVisible){
                    passwordET.setTransformationMethod(new PasswordTransformationMethod());
                    hasVisible = false ;
                }
                else{
                    passwordET.setTransformationMethod(null);
                    hasVisible = true;

                }
            }
        });

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent sendToAdminFragmentContainerIntent = new Intent(getApplicationContext(), AdminFragmentContainer.class);
                startActivity(sendToAdminFragmentContainerIntent);
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
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
