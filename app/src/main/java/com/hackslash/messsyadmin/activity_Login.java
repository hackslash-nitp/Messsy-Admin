package com.hackslash.messsyadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hackslash.messsyadmin.Admin.AdminUI;
import com.hackslash.messsyadmin.MessMember.MessMemberUI;

public class activity_Login extends AppCompatActivity {
    EditText emailET, passwordET;          // ET stands for Edittext
    Button loginButton, loginMMButton;
    TextView forgotPasswordTV, createOneTV, forAdminTV;     // Tv stands for textview
    String sEmailAddress;
    String sPassword, sConditionChecker;  // s stannds for string
    boolean hasLoginAdmin , hasLoginMM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




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

                Toast.makeText(activity_Login.this, "Forgot password", Toast.LENGTH_SHORT).show();
            }
        });

        sConditionChecker = loginMMButton.getText().toString();
        createOneTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_Login.this, "Creating a new account", Toast.LENGTH_SHORT).show();
                Toast.makeText(activity_Login.this, "Creating a new account", Toast.LENGTH_SHORT).show();

                // fake intent created for checking the ui
                Intent intentReport = new Intent(activity_Login.this, activity_ReportAnIssueUI.class);
                startActivity(intentReport);
//
                if(sConditionChecker.equalsIgnoreCase("Login as Admin")) {
                    Intent intentMM = new Intent(activity_Login.this, MessMemberUI.class);
                    startActivity(intentMM);
                }
                else{
                    Intent intentAdmin = new Intent(activity_Login.this, AdminUI.class);
                    startActivity(intentAdmin);
                }
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEmailAddress = emailET.getText().toString();
                sPassword = passwordET.getText().toString();
                if (sConditionChecker.equalsIgnoreCase("Login as Mess Member")){
                        hasLoginAdmin = true;
                 }
                 else{
                     hasLoginMM = true;
                 }
                Toast.makeText(activity_Login.this, "Email:" + sEmailAddress +"\nPassword:"+ sPassword, Toast.LENGTH_SHORT).show();

            }

        });



            loginMMButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sConditionChecker.equalsIgnoreCase("Login as Mess Member")) {
                        forAdminTV.setText("Mess Member");
                        loginMMButton.setText("Login as Admin");
                        emailET.setText("");
                        passwordET.setText("");
                        sConditionChecker = "Login as Admin" ;
                    }
                   else{
                            forAdminTV.setText("For Admin");
                            loginMMButton.setText("Login as Mess Member");
                            emailET.setText("");
                            passwordET.setText("");
                        sConditionChecker = "Login as Mess Member" ;
                    }
                    }

            });







    }
}