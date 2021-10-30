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
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackslash.messsyadmin.Model.UserClass;
import com.hackslash.messsyadmin.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminRegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button loginButton, addImageButton, registerButton, visibilityButton;
    EditText nameET, emailAddET, mobileNumberET, passwordET;
    String  sName , sEmail , sMobile , sPassword ,sHostelName = "null", sDesignation = "Admin",sImageUrl = "null";
    Boolean hasVisible = false;
    private static int PICK_IMAGE = 1 ;
    Dialog dialogSuccesfullyRegistered;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

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

                if(data == "Select Your Hostel") {
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
                Toast.makeText(AdminRegisterActivity.this, "Adding Image", Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);

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


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
