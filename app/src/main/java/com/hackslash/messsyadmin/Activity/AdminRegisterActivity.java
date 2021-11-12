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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import java.util.Random;

public class AdminRegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button loginButton, addImageButton, registerButton, visibilityButton;
    EditText nameET, emailAddET, mobileNumberET, passwordET;
    ImageView profileImage;
    String  sName , sEmail , sMobile , sPassword ,sHostelName = "null", sDesignation = "Admin",sImageUrl = "null" , sUId;
    Boolean hasVisible = false;
    private static int PICK_IMAGE = 1 ;
    Dialog dialogSuccesfullyRegistered;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    UserClass userInfo;

    Uri selectedImage;
    FirebaseStorage storage;
    String ImagePath;
    Bitmap bitmap;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DocumentReference documentReference;
    StorageReference storageReference;

    ProgressDialog progressDialog;

    Spinner spinner;
    String[] hostellist={"Select Your Hostel", "Brahmaputra", "Ganga", "Kosi", "Sone"};
    String data;
    DatabaseReference databaseReference;
    UserClass userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        Intent intent = getIntent(
        );

        loginButton = (Button) findViewById(R.id.login);
        addImageButton = (Button) findViewById(R.id.addImage);
        registerButton = (Button) findViewById(R.id.register);
        visibilityButton = (Button) findViewById(R.id.visibility);
        nameET = (EditText) findViewById(R.id.name);
        emailAddET = (EditText) findViewById(R.id.EmailAddress);
        mobileNumberET = (EditText) findViewById(R.id.mobilenumber);
        passwordET = (EditText) findViewById(R.id.Password);
        profileImage = (ImageView) findViewById(R.id.image);
        dialogSuccesfullyRegistered = new Dialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        spinner = findViewById(R.id.enter_hostelName);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("sHostelName");
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,hostellist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        storage=FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(AdminRegisterActivity.this);
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("Please Wait");



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = nameET.getText().toString();
                sEmail = emailAddET.getText().toString();
                sMobile = mobileNumberET.getText().toString();
                sPassword = passwordET.getText().toString();
                sHostelName = spinner.getSelectedItem().toString();

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


                if(sHostelName == "Select Your Hostel") {
                    Toast.makeText(AdminRegisterActivity.this, "Please Select Your Hostel", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    String id= databaseReference.push().getKey();
                    databaseReference.child(id).setValue(userInfo);
                    Toast.makeText(AdminRegisterActivity.this,"",Toast.LENGTH_SHORT).show();
                    }


                userInfo = new UserClass(sName, sEmail, sMobile, sHostelName, sDesignation, sImageUrl);


                if (currentUser != null) {
                    Intent sendToAdminFragmentContainerIntent = new Intent(getApplicationContext(), AdminFragmentContainer.class);
                    startActivity(sendToAdminFragmentContainerIntent);
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
                                    Toast.makeText(AdminRegisterActivity.this, "Information uploaded on firebase", Toast.LENGTH_SHORT).show();
                                    if(selectedImage != null){
                                    storageReference = storage.getReference().child("images").child(currentUser.getUid());
                                    documentReference = FirebaseFirestore.getInstance().collection("UserInformation").document(currentUser.getUid());
                                    storageReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(AdminRegisterActivity.this, "Image Saved", Toast.LENGTH_SHORT).show();
                                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    ImagePath = uri.toString();
                                                    documentReference.update("imageUrl", ImagePath).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(AdminRegisterActivity.this, "Url Saved", Toast.LENGTH_SHORT).show();
                                                            progressDialog.dismiss();
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
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AdminRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

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
                profileImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
