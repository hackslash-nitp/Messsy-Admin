package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackslash.messsyadmin.Fragment.AdminHomeFragment;
import com.hackslash.messsyadmin.R;

import java.util.HashMap;
import java.util.Map;

public class CreateNoticeActivity extends AppCompatActivity {
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
                if(subject.isEmpty()){
                    subEdit.setError("This field is required");
                    return;
                }
                if (description.isEmpty()){
                    desEdit.setError("This field is required");
                    return;
                }

                Map<String, Object> Notice = new HashMap<>();
                Notice.put("Notice Subject", subject);
                Notice.put("Notice Description", description);

                db.collection("MessssyNotice")
                        .add(Notice)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(CreateNoticeActivity.this, "Notice Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateNoticeActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }

    private void createNotice() {
        subject=subEdit.getText().toString().trim();
        description=desEdit.getText().toString().trim();

    }
}
