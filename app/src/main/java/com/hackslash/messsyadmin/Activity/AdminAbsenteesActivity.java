package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hackslash.messsyadmin.Adapters.AbsenteesListAdapter;
import com.hackslash.messsyadmin.Model.AbsenteesListAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminAbsenteesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<AbsenteesListAdapterClass> userList = new ArrayList<>();
    AbsenteesListAdapter adapter;

    String name, hostel, roll;


    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference notebookRef = db.collection("AbsenceNotification");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_absentees);

        recyclerView = findViewById(R.id.recyclerView);
        EditText search = findViewById(R.id.et_search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });



        notebookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {


                    name = documentSnapshot.getString("sName");
                    roll = documentSnapshot.getString("roll");
                    hostel = documentSnapshot.getString("hostel");

                    userList.add(new AbsenteesListAdapterClass(name, hostel, roll));

                }
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminAbsenteesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        adapter= new AbsenteesListAdapter(userList);
        layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }


    private void filter(String text) {
        ArrayList<AbsenteesListAdapterClass> filteredList = new ArrayList<>();

        for (AbsenteesListAdapterClass item : userList) {
            if ((item.getStudentName().toLowerCase().contains(text.toLowerCase())) || (item.getRollNumber().toLowerCase().contains(text.toLowerCase()))) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    /*


    private void initData() {
        userList= new ArrayList<>();

        userList.add(new AbsenteesListAdapterClass("1","Rahul",R.drawable.kosi_icon,"2002156"));
        userList.add(new AbsenteesListAdapterClass("2","ABC",R.drawable.sone_icon,"2001156"));
        userList.add(new AbsenteesListAdapterClass("3","Xyz",R.drawable.ganga_icon,"2003106"));
        userList.add(new AbsenteesListAdapterClass("4","UVW",R.drawable.brahmputra_icon,"2002156"));
        userList.add(new AbsenteesListAdapterClass("5","GMP",R.drawable.brahmputra_icon,"2002150"));
        userList.add(new AbsenteesListAdapterClass("6","ABC",R.drawable.kosi_icon,"2002161"));
        userList.add(new AbsenteesListAdapterClass("7","Rahul",R.drawable.ganga_icon,"2002162"));
        userList.add(new AbsenteesListAdapterClass("8","Deepali",R.drawable.ganga_icon,"2002163"));
        userList.add(new AbsenteesListAdapterClass("9","Seema",R.drawable.ganga_icon,"2002164"));
        userList.add(new AbsenteesListAdapterClass("10","Aryan",R.drawable.sone_icon,"2002165"));
        userList.add(new AbsenteesListAdapterClass("11","Aryan",R.drawable.sone_icon,"2002167"));
        userList.add(new AbsenteesListAdapterClass("12","Anu",R.drawable.ganga_icon,"2002168"));
        userList.add(new AbsenteesListAdapterClass("13","Preeti",R.drawable.ganga_icon,"2002169"));
        userList.add(new AbsenteesListAdapterClass("14","Kabir",R.drawable.brahmputra_icon,"2002170"));
        userList.add(new AbsenteesListAdapterClass("15","Rahul",R.drawable.kosi_icon,"2002156"));
    }

    private void initRecyclerView() {
        recyclerView=findViewById(R.id.recyclerView);
        layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new AbsenteesListAdapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    */

}