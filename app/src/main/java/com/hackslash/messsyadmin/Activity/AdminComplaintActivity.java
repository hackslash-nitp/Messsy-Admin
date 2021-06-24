package com.hackslash.messsyadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.hackslash.messsyadmin.Adapters.ComplaintBoxAdapter;
import com.hackslash.messsyadmin.Model.ComplaintBoxAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;

public class AdminComplaintActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ComplaintBoxAdapter complaintBoxAdapter;
    ArrayList<ComplaintBoxAdapterClass> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaintbox);
        Intent complaintBoxIntent = getIntent();

        recyclerView = findViewById(R.id.recylerViewComplaintBox);


        data.add(new ComplaintBoxAdapterClass(R.drawable.complaint_box_boy_icon, "Arjun Singh", "12th April 2021",
                "Heading of Complain", R.string.complaintBoxDescription1, "165 Upvotes", "20 Comments"));

        data.add(new ComplaintBoxAdapterClass(R.drawable.complaint_box_boy_icon, "Vikash Jha", "10th March 2021",
                "Heading of Complain", R.string.complaintBoxDescription2, "143 Upvotes", "13 Comments"));

        data.add(new ComplaintBoxAdapterClass(R.drawable.complaint_box_girl_icon, "Neetu Kumari", "5th March 2021",
                "Heading of Complain", R.string.complaintBoxDescription2, "93 Upvotes", "7 Comments"));

        data.add(new ComplaintBoxAdapterClass(R.drawable.complaint_box_girl_icon, "Kajal Mishra", "5th March 2021",
                "Heading of Complain", R.string.complaintBoxDescription2, "51 Upvotes", "3 Comments"));

        data.add(new ComplaintBoxAdapterClass(R.drawable.complaint_box_boy_icon, "Arjun Singh", "12th April 2021",
                "Heading of Complain", R.string.complaintBoxDescription1, "165 Upvotes", "20 Comments"));

        data.add(new ComplaintBoxAdapterClass(R.drawable.complaint_box_boy_icon, "Vikash Jha", "10th March 2021",
                "Heading of Complain", R.string.complaintBoxDescription2, "143 Upvotes", "13 Comments"));

        data.add(new ComplaintBoxAdapterClass(R.drawable.complaint_box_girl_icon, "Neetu Kumari", "5th March 2021",
                "Heading of Complain", R.string.complaintBoxDescription2, "93 Upvotes", "7 Comments"));

        data.add(new ComplaintBoxAdapterClass(R.drawable.complaint_box_girl_icon, "Kajal Mishra", "5th March 2021",
                "Heading of Complain", R.string.complaintBoxDescription2, "51 Upvotes", "3 Comments"));

        complaintBoxAdapter = new ComplaintBoxAdapter(data);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(complaintBoxAdapter);


    }
}