package com.hackslash.messsyadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.hackslash.messsyadmin.Adapters.AbsenteesListAdapter;
import com.hackslash.messsyadmin.Model.AbsenteesListAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;
import java.util.List;

public class AdminAbsenteesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<AbsenteesListAdapterClass> userList;
    AbsenteesListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_absentees);

        initData();
        initRecyclerView();
    }

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

}