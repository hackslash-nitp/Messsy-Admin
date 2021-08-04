package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hackslash.messsyadmin.R;

public class LoginActivity extends AppCompatActivity
{
    EditText emailET, passwordET;          // ET stands for Edittext
    Button loginButton, loginMMButton;
    TextView forgotPasswordTV, createOneTV, forAdminTV;     // Tv stands for textview
    String sEmailAddress;
    String sPassword, sConditionChecker;  // s stannds for string
    boolean hasLoginAdmin = true , hasLoginMM = false;


    FirebaseAuth firebaseAuth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        firebaseAuth = FirebaseAuth.getInstance();

        emailET = (EditText) findViewById(R.id.EmailAddress);
        passwordET = (EditText) findViewById(R.id.Password);
        loginButton = (Button) findViewById(R.id.login);
        loginMMButton = (Button) findViewById(R.id.login_member);
        forgotPasswordTV = (TextView) findViewById(R.id.forgotPassword);
        createOneTV = (TextView) findViewById(R.id.create_one);
        forAdminTV = (TextView) findViewById(R.id.admin);


        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LoginActivity.this, "Forgot password", Toast.LENGTH_SHORT).show();

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

                firebaseAuth.signInWithEmailAndPassword(sEmailAddress, sPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        //Login Successful


                        //send to admin fragment or mess fragment depending on mess member or admin



                        if (sConditionChecker.equalsIgnoreCase("Login as Mess Member")){
                            startActivity(new Intent(LoginActivity.this, AdminFragmentContainer.class));
                            finish();
                            hasLoginAdmin = true;
                            hasLoginMM = false;
                        }

                        else{
                            startActivity(new Intent(LoginActivity.this, MessFragmentContainer.class));
                            finish();
                            hasLoginMM = true;
                            hasLoginAdmin = false;
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
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
            if (hasLoginAdmin){
                startActivity(new Intent(LoginActivity.this, AdminFragmentContainer.class));
                finish();
            }
            else{
                startActivity(new Intent(LoginActivity.this, MessFragmentContainer.class));
                finish();
            }
        }

    }
}