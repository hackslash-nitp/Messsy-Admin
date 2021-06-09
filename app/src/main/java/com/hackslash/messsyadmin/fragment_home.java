package com.hackslash.messsyadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class fragment_home extends Fragment implements View.OnClickListener {
    CardView absenteesListCV, studentTrackCV, createNewNoticeCV, complaintBoxCV;
    CardView conversationCV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        absenteesListCV = (CardView)view.findViewById(R.id.card_view1);
        studentTrackCV = (CardView) view.findViewById(R.id.card_view2);
        createNewNoticeCV = (CardView) view.findViewById(R.id.card_view3);
        complaintBoxCV = (CardView) view.findViewById(R.id.card_view5);
        conversationCV = (CardView) view.findViewById(R.id.card_view4);

        absenteesListCV.setOnClickListener(this);
        studentTrackCV.setOnClickListener(this);
        createNewNoticeCV.setOnClickListener(this);
        complaintBoxCV.setOnClickListener(this);
        conversationCV.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.card_view1 :
                Toast.makeText(getActivity(), "Opening Absentees List", Toast.LENGTH_SHORT).show();
                Intent absenteesIntent = new Intent(getContext(), activity_absenteesList.class);
                startActivity(absenteesIntent);
                break;
            case R.id.card_view2 :
                Toast.makeText(getActivity(), "Opening Student's track", Toast.LENGTH_SHORT).show();
                Intent studentTrackIntent = new Intent(getContext(), activity_studentTrack.class);
                startActivity(studentTrackIntent);
                break;
            case R.id.card_view3 :
                Toast.makeText(getActivity(), "Opening Create new notice", Toast.LENGTH_SHORT).show();
                Intent createNewNoticeIntent = new Intent(getContext(), activity_createNewNotice.class);
                 startActivity(createNewNoticeIntent);
                break;
            case R.id.card_view4 :
                Toast.makeText(getActivity(), "Opening conversations", Toast.LENGTH_SHORT).show();
                Intent conversationIntent = new Intent(getContext(), activity_conversations.class);
                startActivity(conversationIntent);
                break;
            case R.id.card_view5 :
                Toast.makeText(getActivity(), "Opening complaint Box", Toast.LENGTH_SHORT).show();
                Intent complaintBoxIntent = new Intent(getContext(), activity_complaintBox.class);
            startActivity(complaintBoxIntent);
                break;


        }
    }
}