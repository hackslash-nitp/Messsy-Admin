package com.hackslash.messsyadmin.Fragment;

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

import com.hackslash.messsyadmin.Activity.AdminAbsenteesActivity;
import com.hackslash.messsyadmin.Activity.AdminComplaintActivity;
import com.hackslash.messsyadmin.Activity.AdminConversationActivity;
import com.hackslash.messsyadmin.Activity.AdminNoticeActivity;
import com.hackslash.messsyadmin.Activity.AdminStudentTrackActivity;
import com.hackslash.messsyadmin.R;

public class AdminHomeFragment extends Fragment implements View.OnClickListener {
    CardView absenteesListCV, studentTrackCV, createNewNoticeCV, complaintBoxCV;
    CardView conversationCV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
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
                Intent absenteesIntent = new Intent(getContext(), AdminAbsenteesActivity.class);
                startActivity(absenteesIntent);
                break;
            case R.id.card_view2 :
                Toast.makeText(getActivity(), "Opening Student's track", Toast.LENGTH_SHORT).show();
                Intent studentTrackIntent = new Intent(getContext(), AdminStudentTrackActivity.class);
                startActivity(studentTrackIntent);
                break;
            case R.id.card_view3 :
                Toast.makeText(getActivity(), "Opening Create new notice", Toast.LENGTH_SHORT).show();
                Intent createNewNoticeIntent = new Intent(getContext(), AdminNoticeActivity.class);
                 startActivity(createNewNoticeIntent);
                break;
            case R.id.card_view4 :
                Toast.makeText(getActivity(), "Opening conversations", Toast.LENGTH_SHORT).show();
                Intent conversationIntent = new Intent(getContext(), AdminConversationActivity.class);
                startActivity(conversationIntent);
                break;
            case R.id.card_view5 :
                Toast.makeText(getActivity(), "Opening complaint Box", Toast.LENGTH_SHORT).show();
                Intent complaintBoxIntent = new Intent(getContext(), AdminComplaintActivity.class);
            startActivity(complaintBoxIntent);
                break;


        }
    }
}