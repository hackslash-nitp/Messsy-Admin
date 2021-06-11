package com.hackslash.messsyadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;

import com.hackslash.messsyadmin.R;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

    }

    public void OpenFbAddress(View view) {
        Uri webpageFb = Uri.parse("https://www.facebook.com/messsynitp");
        Intent webIntentFb = new Intent(Intent.ACTION_VIEW, webpageFb);

        try {
            startActivity(webIntentFb);
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Application not found.", Toast.LENGTH_SHORT).show();
        }

    }

    public void OpenIgAddress(View view) {
        Uri webpageIg = Uri.parse("https://www.instagram.com/messsynitp/");
        Intent webIntentIg = new Intent(Intent.ACTION_VIEW, webpageIg);

        try {
            startActivity(webIntentIg);
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Application not found.", Toast.LENGTH_SHORT).show();
        }

    }

    public void OpenMail(View view) {
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
        mailIntent.setData(Uri.parse("mailto:" + "contact_messsynitp@gmail.com"));

        try {
            startActivity(mailIntent);
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Application not found.", Toast.LENGTH_SHORT).show();
        }

    }

    public void OpenTelephoneAddress(View view) {
        Uri number = Uri.parse("tel:0612-210-1342");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

        try {
            startActivity(callIntent);
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Application not found.", Toast.LENGTH_SHORT).show();
        }
    }

}