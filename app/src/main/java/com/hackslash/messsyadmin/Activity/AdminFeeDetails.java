package com.hackslash.messsyadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.auth.User;
import com.hackslash.messsyadmin.Adapters.FeeDetailsAdapter;
import com.hackslash.messsyadmin.Model.FeeDetailsAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;

public class AdminFeeDetails extends AppCompatActivity {

    ArrayList<FeeDetailsAdapterClass> userList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fee_details);

        recyclerView=findViewById(R.id.recyclerView);

        userList = new ArrayList<>();

        setStudentInfo();
        setAdapter();

    }

    private void setAdapter() {
        FeeDetailsAdapter adapter =  new FeeDetailsAdapter(userList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setStudentInfo() {

        userList.add(new FeeDetailsAdapterClass("1","Alka Kumari","2002156"));

        userList.add(new FeeDetailsAdapterClass("2","George","2003106"));

        userList.add(new FeeDetailsAdapterClass("3","Udbhaw","1902156"));

        userList.add(new FeeDetailsAdapterClass("4","Gitanjali","2002156"));

        userList.add(new FeeDetailsAdapterClass("5","Aman","1802150"));

        userList.add(new FeeDetailsAdapterClass("6","Vijay","2002156"));

        userList.add(new FeeDetailsAdapterClass("7","Alok Kumar","2002156"));

        userList.add(new FeeDetailsAdapterClass("8","Uday","2002156"));

        userList.add(new FeeDetailsAdapterClass("9","Xavier","2002156"));

        userList.add(new FeeDetailsAdapterClass("10","Garima","2002156"));

        userList.add(new FeeDetailsAdapterClass("11","Prithvi","2002156"));

        userList.add(new FeeDetailsAdapterClass("12","Malay","2002156"));

        userList.add(new FeeDetailsAdapterClass("13","Vinay","2002156"));

        userList.add(new FeeDetailsAdapterClass("14","Urvashi","2002156"));

        userList.add(new FeeDetailsAdapterClass("15","Raman","2002156"));

    }
}