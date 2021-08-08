package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackslash.messsyadmin.Model.UserClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MessRegisterActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    Button loginButton, addImageButton, registerButton, visibilityButton;
    EditText nameET, emailAddET, mobileNumberET, hostelNameET, passwordET; // ET stands for edittext
    String  sName , sEmail , sMobile , sHostelName, sPassword ,sDesignation = "Mess Member"; // s stands for string
    Boolean hasVisible = false;
    Dialog dialogSuccesfullyRegistered;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    ArrayList<UserClass> userdata = new ArrayList<>();


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
        dialogSuccesfullyRegistered = new Dialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

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

                UserClass userInfo = new UserClass(sName, sEmail, sMobile, sHostelName, sDesignation);

                if (currentUser != null) {
                    Intent sendToMessFragmentContainerIntent = new Intent(getApplicationContext(), MessFragmentContainer.class);
                    startActivity(sendToMessFragmentContainerIntent);
                } else{
                    firebaseAuth.createUserWithEmailAndPassword(sEmail, sPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            userdata.add(userInfo);


                            firebaseFirestore.collection("UserInformation").document("User").set(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
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


}