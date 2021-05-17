package com.hackslash.messsyadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText email , password;
    Button login, loginMM;
    TextView forgotpass , createOne , forAdmin;
    String emailAdd ;
    String pass , conditionChecker;
    boolean hasLoginAdmin , hasLoginMM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_open_frame);




        email = (EditText) findViewById(R.id.EmailAddress);
        password = (EditText) findViewById(R.id.Password);
        login = (Button) findViewById(R.id.login);
        loginMM = (Button) findViewById(R.id.login_member);
        forgotpass = (TextView) findViewById(R.id.forgotPassword);
        createOne = (TextView) findViewById(R.id.create_one);
        forAdmin= (TextView) findViewById(R.id.admin);


        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Forgot password", Toast.LENGTH_SHORT).show();
            }
        });


        createOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Creating a new account", Toast.LENGTH_SHORT).show();
            }
        });


        conditionChecker = loginMM.getText().toString();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAdd = email.getText().toString();
                pass = password.getText().toString();
                if (conditionChecker.equalsIgnoreCase("Login as Mess Member")){
                        hasLoginAdmin = true;
                 }
                 else{
                     hasLoginMM = true;
                 }
                Toast.makeText(MainActivity.this, "Email:" + emailAdd +"\nPassword:"+ pass, Toast.LENGTH_SHORT).show();

            }

        });



            loginMM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (conditionChecker.equalsIgnoreCase("Login as Mess Member")) {
                        forAdmin.setText("Mess Member");
                        loginMM.setText("Login as Admin");
                        email.setText("");
                        password.setText("");
                        conditionChecker = "Login as Admin" ;
                    }
                   else{
                            forAdmin.setText("For Admin");
                            loginMM.setText("Login as Mess Member");
                            email.setText("");
                            password.setText("");
                        conditionChecker = "Login as Mess Member" ;
                    }
                    }

            });


//            loginMM.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if(conditionChecker.equalsIgnoreCase("Login as Admin")){
//
//                        forAdmin.setText("For Admin");
//                        loginMM.setText("Login as Mess Member");
//                        email.setText("");
//                        password.setText("");
//                        hasLoginAdmin = true;
//                    }
//
//                }
//            });





    }
}