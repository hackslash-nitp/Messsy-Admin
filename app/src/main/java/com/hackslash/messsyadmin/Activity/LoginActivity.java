package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackslash.messsyadmin.Model.UserClass;
import com.hackslash.messsyadmin.R;



public class LoginActivity extends AppCompatActivity
{
    EditText emailET, passwordET;          // ET stands for Edittext
    Button loginButton, loginMMButton;
    TextView forgotPasswordTV, createOneTV, forAdminTV;     // Tv stands for textview
    String sEmailAddress;
    String sPassword, sConditionChecker;  // s stannds for string
    boolean hasLoginAdmin = true , hasLoginMM = false;
    String Designation;
    FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private FirebaseUser currUser;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailET = (EditText) findViewById(R.id.EmailAddress);
        passwordET = (EditText) findViewById(R.id.Password);
        loginButton = (Button) findViewById(R.id.login);
        loginMMButton = (Button) findViewById(R.id.login_member);
        forgotPasswordTV = (TextView) findViewById(R.id.forgotPassword);
        createOneTV = (TextView) findViewById(R.id.create_one);
        forAdminTV = (TextView) findViewById(R.id.admin);
        dialog=new ProgressDialog(this);
        currUser = firebaseAuth.getCurrentUser();
        dialog.setMessage("Logging in...");
        dialog.setCancelable(false);


        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                sEmailAddress = emailET.getText().toString();

                if(sEmailAddress.isEmpty())
                {
                    emailET.setError("Required Field!");
                    return;
                }

                firebaseAuth.sendPasswordResetEmail(sEmailAddress).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(LoginActivity.this, "Email Sent to " + sEmailAddress + " .", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        sConditionChecker = loginMMButton.getText().toString();


        createOneTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sConditionChecker.equalsIgnoreCase("Login as Admin")) {
                    Intent intentMM = new Intent(LoginActivity.this, MessRegisterActivity.class);
                    startActivity(intentMM);
                }
                else{
                    Intent intentAdmin = new Intent(LoginActivity.this, AdminRegisterActivity.class);
                    startActivity(intentAdmin);
                }
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Data extraction and validation
                sEmailAddress = emailET.getText().toString();
                sPassword = passwordET.getText().toString();

                if(sEmailAddress.isEmpty())
                {
                    emailET.setError("Required Field!");
                    return;
                }

                if(sPassword.isEmpty())
                {
                    passwordET.setError("Password Reuired!");
                    return;
                }

                //Login
                dialog.show();
                firebaseAuth.signInWithEmailAndPassword(sEmailAddress, sPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        //Login Successful
                        dialog.setMessage("Getting your profile...");
                        currUser = firebaseAuth.getCurrentUser();
                        final String user_id = currUser.getUid();
                        Task<DocumentSnapshot> userDetails = db.collection("UserInformation").document(user_id).get();
                        userDetails.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot)
                            {

                                dialog.dismiss();
                                UserClass userInfo = documentSnapshot.toObject(UserClass.class);
                                Designation = userInfo.getsDesignation();


                                //send to admin fragment or mess fragment depending on mess member or admin



                                if (Designation.equalsIgnoreCase("Admin") && sConditionChecker.equalsIgnoreCase("Login as Mess Member")){
                                    startActivity(new Intent(LoginActivity.this, AdminFragmentContainer.class));
                                    finish();
                                    hasLoginAdmin = true;
                                    hasLoginMM = false;
                                }
                                else if (Designation.equalsIgnoreCase("Admin") && sConditionChecker.equalsIgnoreCase("Login as Admin"))
                                {
                                    FirebaseAuth.getInstance().signOut();
                                    Toast.makeText(LoginActivity.this, "Please Login as Admin.", Toast.LENGTH_SHORT).show();
                                }
                                else if (Designation.equalsIgnoreCase("Mess Member") && sConditionChecker.equalsIgnoreCase("Login as Admin")){
                                    startActivity(new Intent(LoginActivity.this, MessFragmentContainer.class));
                                    finish();
                                    hasLoginMM = true;
                                    hasLoginAdmin = false;
                                }
                                else
                                {
                                    FirebaseAuth.getInstance().signOut();
                                    Toast.makeText(LoginActivity.this, "Please Login as Mess Member.", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });





                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });



            loginMMButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sConditionChecker.equalsIgnoreCase("Login as Mess Member"))
                    {
                        forAdminTV.setText("Mess Member");
                        loginMMButton.setText("Login as Admin");
                        emailET.setText("");
                        passwordET.setText("");
                        sConditionChecker = "Login as Admin" ;
                        hasLoginAdmin = false;
                        hasLoginMM = true;
                    }
                   else{
                       forAdminTV.setText("For Admin");
                       loginMMButton.setText("Login as Mess Member");
                       emailET.setText("");
                       passwordET.setText("");
                        sConditionChecker = "Login as Mess Member" ;
                        hasLoginAdmin = true;
                        hasLoginMM = false;
                    }
                }

            });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            Task<DocumentSnapshot> userDetails = db.collection("UserInformation").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get();
            userDetails.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    UserClass userClass = documentSnapshot.toObject(UserClass.class);
                    Designation = userClass.getsDesignation();

                    if (Designation.equalsIgnoreCase("Admin")){
                        startActivity(new Intent(LoginActivity.this, AdminFragmentContainer.class));
                        finish();
                    }
                    else{
                        startActivity(new Intent(LoginActivity.this, MessFragmentContainer.class));
                        finish();
                    }
                }
            });


        }

    }
}