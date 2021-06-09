package com.hackslash.messsyadmin.MessMember;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hackslash.messsyadmin.R;

public class MessMemberUI extends AppCompatActivity {

    Button loginButton, addImageButton, registerButton, visibilityButton;
    EditText nameET, emailAddET, mobileNumberET, hostelNameET, passwordET; // ET stands for edittext
    String  sName , sEmail , sMobile , sHostelName, sPassword , sData; // s stands for string
    Boolean hasVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_member_u_i);
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



     registerButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             sName = nameET.getText().toString();
             sEmail = emailAddET.getText().toString();
             sMobile = mobileNumberET.getText().toString();
             sHostelName = hostelNameET.getText().toString();
             sPassword = passwordET.getText().toString();
             sData = "Name: " + sName + "\nEmail: " + sEmail + "\nMobile No: " + sMobile +"\nHostel name: " + sHostelName + "\nPassword: " +sPassword;
             Toast.makeText(MessMemberUI.this, sData, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(MessMemberUI.this, "Adding Image", Toast.LENGTH_SHORT).show();
        }
    });

    }
}