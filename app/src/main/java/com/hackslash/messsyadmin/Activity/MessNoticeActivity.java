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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.datatransport.cct.internal.LogEvent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hackslash.messsyadmin.Adapters.NoticeBoxAdapter;
import com.hackslash.messsyadmin.Model.CreateNewNoticeClass;
import com.hackslash.messsyadmin.Model.NoticeBoxAdapterClass;
import com.hackslash.messsyadmin.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MessNoticeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView recyclerView;
    NoticeBoxAdapter noticeBoxAdapter;
    Spinner sortBySpinner, groupBySpinner;
    ArrayList<String> sortByList = new ArrayList<>();
    ArrayList<String> grouptByList = new ArrayList<>();
    FloatingActionButton floatingButton;
    ArrayList<CreateNewNoticeClass> data = new ArrayList<>();

    String date, description, subject, time, user, hostel, designation , Date ,hostelFilter ,dateFilter;
    Date givenDate ,beforeTenDaysDate  , currentDate;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference notebookRef = db.collection("MessssyNotice");

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



        notebookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    data.clear();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    date = documentSnapshot.getString("date");
                    description = documentSnapshot.getString("description");
                    subject = documentSnapshot.getString("subject");
                    time = documentSnapshot.getString("timestamp");
                    user = documentSnapshot.getString("userInfo");
                    hostel = documentSnapshot.getString("hostel");
                    designation = documentSnapshot.getString("designation");

                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yy");
                    Date =sdf.format(c);

                    Log.e("date in firebase:  ", ""+ date );

                    try {
                        currentDate = sdf.parse(Date);
                    Log.e("current date :" ,"" +currentDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        beforeTenDaysDate = sdf.parse((tenDaysBeforeDate()));
                        Log.e("beforeTenDays date :" ,"" +beforeTenDaysDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try{
                        DateFormat originalFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
                        DateFormat targetFormat = new SimpleDateFormat("dd/MM/yy");
                        Date d = originalFormat.parse(date);
                        String formattedDate = targetFormat.format(d);
                        givenDate = sdf.parse(formattedDate);
                        Log.e("Given Date: " , ""+givenDate);
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
//                    Log.e("Date Filter: " ,dateFilter);
//                    Log.e("Hostel Filter: " ,hostelFilter);

                    if(dateFilter.equalsIgnoreCase("All") && hostelFilter.equalsIgnoreCase(hostel + " hostel")){
                        Log.e("Inside:",dateFilter);
                        data.add(new CreateNewNoticeClass(subject, description, date, time, designation, hostel));
                    }

                   else if(dateFilter.equalsIgnoreCase("Today") && givenDate.equals(currentDate) && hostelFilter.equalsIgnoreCase(hostel+ " hostel")){
                        Log.e("Inside:",dateFilter);
                        data.add(new CreateNewNoticeClass(subject, description, date, time, designation, hostel));
                    }

                    else if(dateFilter.equalsIgnoreCase("Last 10 days") && givenDate.after(beforeTenDaysDate) && hostelFilter.equalsIgnoreCase(hostel + " hostel")){
                        Log.e("Inside:",dateFilter);
                        data.add(new CreateNewNoticeClass(subject, description, date, time, designation, hostel));
                    }

                }
                noticeBoxAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MessNoticeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

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
        grouptByList.add("Kosi Hostel");
        grouptByList.add("Sone Hostel");

        sortBySpinner = findViewById(R.id.sortBySpinner);
        groupBySpinner = findViewById(R.id.groupByspinner);

        setUpSortSpinner(sortBySpinner);
        setUpGroupSpinner(groupBySpinner);

    }

    private String tenDaysBeforeDate(){
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DATE ,-10);
    return simpleDateFormat.format(c.getTime());
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
            dateFilter = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), dateFilter, Toast.LENGTH_SHORT).show();

            notebookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    data.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        date = documentSnapshot.getString("date");
                        description = documentSnapshot.getString("description");
                        subject = documentSnapshot.getString("subject");
                        time = documentSnapshot.getString("timestamp");
                        user = documentSnapshot.getString("userInfo");
                        hostel = documentSnapshot.getString("hostel");
                        designation = documentSnapshot.getString("designation");

                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yy");
                        Date =sdf.format(c);

                        Log.e("date in firebase:  ", ""+ date );

                        try {
                            currentDate = sdf.parse(Date);
                            Log.e("current date :" ,"" +currentDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            beforeTenDaysDate = sdf.parse((tenDaysBeforeDate()));
                            Log.e("beforeTenDays date :" ,"" +beforeTenDaysDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try{
                            DateFormat originalFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
                            DateFormat targetFormat = new SimpleDateFormat("dd/MM/yy");
                            Date d = originalFormat.parse(date);
                            String formattedDate = targetFormat.format(d);
                            givenDate = sdf.parse(formattedDate);
                            Log.e("Given Date: " , ""+givenDate);
                        }catch (ParseException e){
                            e.printStackTrace();
                        }
//                    Log.e("Date Filter: " ,dateFilter);
//                    Log.e("Hostel Filter: " ,hostelFilter);

                        if(dateFilter.equalsIgnoreCase("All") && hostelFilter.equalsIgnoreCase(hostel + " hostel")){
                            Log.e("Inside:",dateFilter);
                            data.add(new CreateNewNoticeClass(subject, description, date, time, designation, hostel));
                        }

                        else if(dateFilter.equalsIgnoreCase("Today") && givenDate.equals(currentDate) && hostelFilter.equalsIgnoreCase(hostel+ " hostel")){
                            Log.e("Inside:",dateFilter);
                            data.add(new CreateNewNoticeClass(subject, description, date, time, designation, hostel));
                        }

                        else if(dateFilter.equalsIgnoreCase("Last 10 days") && givenDate.after(beforeTenDaysDate) && hostelFilter.equalsIgnoreCase(hostel + " hostel")){
                            Log.e("Inside:",dateFilter);
                            data.add(new CreateNewNoticeClass(subject, description, date, time, designation, hostel));
                        }

                    }
                    noticeBoxAdapter.notifyDataSetChanged();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MessNoticeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class groupSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           hostelFilter = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), hostelFilter, Toast.LENGTH_SHORT).show();

            notebookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    data.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        date = documentSnapshot.getString("date");
                        description = documentSnapshot.getString("description");
                        subject = documentSnapshot.getString("subject");
                        time = documentSnapshot.getString("timestamp");
                        user = documentSnapshot.getString("userInfo");
                        hostel = documentSnapshot.getString("hostel");
                        designation = documentSnapshot.getString("designation");

                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yy");
                        Date =sdf.format(c);

                        Log.e("date in firebase:  ", ""+ date );

                        try {
                            currentDate = sdf.parse(Date);
                            Log.e("current date :" ,"" +currentDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            beforeTenDaysDate = sdf.parse((tenDaysBeforeDate()));
                            Log.e("beforeTenDays date :" ,"" +beforeTenDaysDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try{
                            DateFormat originalFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
                            DateFormat targetFormat = new SimpleDateFormat("dd/MM/yy");
                            Date d = originalFormat.parse(date);
                            String formattedDate = targetFormat.format(d);
                            givenDate = sdf.parse(formattedDate);
                            Log.e("Given Date: " , ""+givenDate);
                        }catch (ParseException e){
                            e.printStackTrace();
                        }
//                    Log.e("Date Filter: " ,dateFilter);
//                    Log.e("Hostel Filter: " ,hostelFilter);

                        if(dateFilter.equalsIgnoreCase("All") && hostelFilter.equalsIgnoreCase(hostel + " hostel")){
                            Log.e("Inside:",dateFilter);
                            data.add(new CreateNewNoticeClass(subject, description, date, time, designation, hostel));
                        }

                        else if(dateFilter.equalsIgnoreCase("Today") && givenDate.equals(currentDate) && hostelFilter.equalsIgnoreCase(hostel+ " hostel")){
                            Log.e("Inside:",dateFilter);
                            data.add(new CreateNewNoticeClass(subject, description, date, time, designation, hostel));
                        }

                        else if(dateFilter.equalsIgnoreCase("Last 10 days") && givenDate.after(beforeTenDaysDate) && hostelFilter.equalsIgnoreCase(hostel + " hostel")){
                            Log.e("Inside:",dateFilter);
                            data.add(new CreateNewNoticeClass(subject, description, date, time, designation, hostel));
                        }

                    }
                    noticeBoxAdapter.notifyDataSetChanged();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MessNoticeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


}