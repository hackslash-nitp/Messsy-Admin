package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hackslash.messsyadmin.Adapters.ComplaintBoxAdapter;
import com.hackslash.messsyadmin.Model.ComplaintBoxAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;

public class AdminComplaintActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button backButton;
    ComplaintBoxAdapter complaintBoxAdapter;
    ArrayList<ComplaintBoxAdapterClass> data = new ArrayList<>();

    String issue, explanation, date, imageurl;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference notebookRef = db.collection("Issues");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaintbox);
        Intent complaintBoxIntent = getIntent();

        recyclerView = findViewById(R.id.recylerViewComplaintBox);
        backButton = findViewById(R.id.backButton);

        notebookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    issue = documentSnapshot.getString("issue");
                    explanation = documentSnapshot.getString("explanation");
                    date = documentSnapshot.getString("date");
                    imageurl = documentSnapshot.getString("imageUrl");

                    data.add(new ComplaintBoxAdapterClass(issue, explanation, imageurl, date));
                }
                complaintBoxAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminComplaintActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        complaintBoxAdapter = new ComplaintBoxAdapter(data);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(complaintBoxAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminComplaintActivity.this, "Clicked on Back Button", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}