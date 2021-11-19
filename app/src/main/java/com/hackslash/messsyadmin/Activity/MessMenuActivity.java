package com.hackslash.messsyadmin.Activity;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.hackslash.messsyadmin.Adapters.MessMenuAdapter;
import com.hackslash.messsyadmin.Model.MessMenuAdapterClass;
import com.hackslash.messsyadmin.R;
import java.util.ArrayList;
import static androidx.recyclerview.widget.LinearLayoutManager.*;

public class MessMenuActivity extends Activity {
    RecyclerView recyclerView;
    Button backButton;

    com.hackslash.messsyadmin.Adapters.MessMenuAdapter MessMenuAdapter;
    ArrayList<MessMenuAdapterClass> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);
        Intent messMenu = getIntent();

        recyclerView = findViewById(R.id.recyclerView);

        backButton = findViewById(R.id.backButton);

        data.add(new MessMenuAdapterClass("Day        " ,"Monday", "Tuesday",
                "Wednesday",  "Thursday","Friday","Saturday","Sunday"));

        data.add(new MessMenuAdapterClass("Breakfast    " ,"Paratha", "Dosa",
                "Bread-Jam",  "Maggi & Tea","Uttapam","Chole-Bhature","Poha"));

        data.add(new MessMenuAdapterClass("Lunch      " ,"Paneer Chilli", "Mix Veg",
                "Biryani",  "Malai Kofta","Rajma-Chawal","Aaloo Dumm","Paneer Do Pyaaza"));

        data.add(new MessMenuAdapterClass("Snacks     " ,"Biscuit & Maggi", "Tea/Coffee",
                "Noodles",  "Raj-Kachori","Pakoda","Samosa","Kachori"));

        data.add(new MessMenuAdapterClass("Dinner     " ,"Matar Paneer", "Kaju Curry",
                "Dal Makhni",  "Veg Pulao","Dosa","Mushroom Chilli","Malai Kofta"));






        MessMenuAdapter = new MessMenuAdapter(data);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(MessMenuAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}