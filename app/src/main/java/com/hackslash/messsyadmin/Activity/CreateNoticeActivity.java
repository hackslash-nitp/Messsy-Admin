package com.hackslash.messsyadmin.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackslash.messsyadmin.Model.CreateNewNotice;
import com.hackslash.messsyadmin.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateNoticeActivity extends AppCompatActivity  {
    private Button backButton,createNotice;

    String subject,description;
    EditText subEdit,desEdit;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notice);
        db =FirebaseFirestore.getInstance();
        backButton = (Button) findViewById(R.id.backButton);
        subEdit=findViewById(R.id.subject_edit_text);
        desEdit=findViewById(R.id.notice_description_edit);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        createNotice=findViewById(R.id.create_notice);
        createNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotice();

                if (description.isEmpty() && subject.isEmpty()){
                    subEdit.setError("This field is required");
                    desEdit.setError("This field is required");
                    return;
                }
                else if(subject.isEmpty()){
                    subEdit.setError("This field is required");
                    return;
                }
                else  if (description.isEmpty()){
                    desEdit.setError("This field is required");
                    return;
                }

                else {
                    NewNotice(subject,description);


                }
            }
        });

    }

    private void createNotice() {
        subject=subEdit.getText().toString().trim();
        description=desEdit.getText().toString().trim();

    }




    @SuppressLint("SimpleDateFormat")
    public void NewNotice(String subject, String description) {
        Calendar calendar= Calendar.getInstance();
       long timestamp= Timestamp.now().getSeconds();

        String date= DateFormat.getDateInstance().format(calendar.getTime());


        // String userInfo = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        CollectionReference dbNotice = db.collection("MessssyNotice");
        CreateNewNotice Notice = new CreateNewNotice(subject,description,"jessica",date,timestamp);
        System.out.println(subject);
        System.out.println(description);
        System.out.println(date);
        System.out.println(timestamp);
        System.out.println(subject);
        Notice.setSubject(subject);
        Notice.setDescription(description);
        Notice.setTimestamp(timestamp);
        Notice.setDate(date);

        dbNotice.add(Notice).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(CreateNoticeActivity.this, "Notice Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateNoticeActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        });


    }
}