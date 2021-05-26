package com.hackslash.messsyadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageAdminActivityUI extends Fragment implements View.OnClickListener {
    CardView absenteesList, studentTrack, createNewNotice, complaintBox;
    CardView conversation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_page_admin_u_i, container, false);
        absenteesList = (CardView)view.findViewById(R.id.card_view1);
        studentTrack = (CardView) view.findViewById(R.id.card_view2);
        createNewNotice = (CardView) view.findViewById(R.id.card_view3);
        complaintBox = (CardView) view.findViewById(R.id.card_view5);
        conversation = (CardView) view.findViewById(R.id.card_view4);

        absenteesList.setOnClickListener(this);
        studentTrack.setOnClickListener(this);
        createNewNotice.setOnClickListener(this);
        complaintBox.setOnClickListener(this);
        conversation.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.card_view1 :
                Toast.makeText(getActivity(), "Opening Absentees List", Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(getContext(),absenteesList.class);
                startActivity(i1);
                break;
            case R.id.card_view2 :
                Toast.makeText(getActivity(), "Opening Student's track", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(getContext(),studentTrack.class);
                startActivity(i2);
                break;
            case R.id.card_view3 :
                Toast.makeText(getActivity(), "Opening Create new notice", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(getContext(),createNewNotice.class);
                 startActivity(i3);
                break;
            case R.id.card_view4 :
                Toast.makeText(getActivity(), "Opening conversations", Toast.LENGTH_SHORT).show();
                Intent i4 = new Intent(getContext(),conversations.class);
                startActivity(i4);
                break;
            case R.id.card_view5 :
                Toast.makeText(getActivity(), "Opening complaint Box", Toast.LENGTH_SHORT).show();
                Intent i5 = new Intent(getContext(),complaintBox.class);
            startActivity(i5);
                break;


        }
    }
}