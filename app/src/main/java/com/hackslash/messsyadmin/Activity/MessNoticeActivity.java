package com.hackslash.messsyadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hackslash.messsyadmin.Adapters.NoticeBoxAdapter;
import com.hackslash.messsyadmin.Model.NoticeBoxAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;

public class MessNoticeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView recyclerView;
    NoticeBoxAdapter noticeBoxAdapter;
    Spinner sortBySpinner, groupBySpinner;
    ArrayList<String> sortByList = new ArrayList<>();
    ArrayList<String> grouptByList = new ArrayList<>();
    FloatingActionButton floatingButton;
    ArrayList<NoticeBoxAdapterClass> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_notice);
        Intent i2 = getIntent();


        recyclerView = findViewById(R.id.recyclerView);
        floatingButton = findViewById(R.id.create_notice_fab);


        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessNoticeActivity.this,CreateNoticeActivity.class));
            }
        });
        data.add(new NoticeBoxAdapterClass(R.drawable.ganga_hostel_icon, "Ganga Hostel Mess", R.string.noticeDescriptionGangaMess, "12-06-2021", "2:30PM"));
        data.add(new NoticeBoxAdapterClass(R.drawable.brahmaputra_hostel_icon, "Brahmaputra Hostel Mess", R.string.noticeDescriptionBrahmaputraMess, "12-06-2021", "2:30PM"));
        data.add(new NoticeBoxAdapterClass(R.drawable.ganga_hostel_icon, "Ganga Hostel Mess", R.string.noticeDescriptionGangaMess, "12-06-2021", "2:30PM"));
        data.add(new NoticeBoxAdapterClass(R.drawable.brahmaputra_hostel_icon, "Brahmaputra Hostel Mess", R.string.noticeDescriptionBrahmaputraMess, "12-06-2021", "2:30PM"));
        data.add(new NoticeBoxAdapterClass(R.drawable.ganga_hostel_icon, "Ganga Hostel Mess", R.string.noticeDescriptionGangaMess, "12-06-2021", "2:30PM"));
        data.add(new NoticeBoxAdapterClass(R.drawable.brahmaputra_hostel_icon, "Brahmaputra Hostel Mess", R.string.noticeDescriptionBrahmaputraMess, "12-06-2021", "2:30PM"));
        data.add(new NoticeBoxAdapterClass(R.drawable.ganga_hostel_icon, "Ganga Hostel Mess", R.string.noticeDescriptionGangaMess, "12-06-2021", "2:30PM"));
        data.add(new NoticeBoxAdapterClass(R.drawable.brahmaputra_hostel_icon, "Brahmaputra Hostel Mess", R.string.noticeDescriptionBrahmaputraMess, "12-06-2021", "2:30PM"));

        noticeBoxAdapter = new NoticeBoxAdapter(data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(noticeBoxAdapter);


        sortByList.add("All");
        sortByList.add("Today");
        sortByList.add("Last 10 days");

        grouptByList.add("Ganga Hostel");
        grouptByList.add("Brahmaputra Hostel");

        sortBySpinner = findViewById(R.id.sortBySpinner);
        groupBySpinner = findViewById(R.id.groupByspinner);

        setUpSortSpinner(sortBySpinner);
        setUpGroupSpinner(groupBySpinner);

    }

    private void setUpSortSpinner(Spinner sortBySpinner) {
        ArrayAdapter<String> sortByAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortByList);
        sortByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(sortByAdapter);
        sortBySpinner.setOnItemSelectedListener(new sortSpinner());

    }

    private void setUpGroupSpinner(Spinner groupBySpinner) {
        ArrayAdapter<String> groupByAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, grouptByList);
        groupByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupBySpinner.setAdapter(groupByAdapter);
        groupBySpinner.setOnItemSelectedListener(new groupSpinner());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class sortSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class groupSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


}