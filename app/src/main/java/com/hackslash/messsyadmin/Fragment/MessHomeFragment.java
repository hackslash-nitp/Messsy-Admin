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

import com.hackslash.messsyadmin.Activity.MessConversationActivity;
import com.hackslash.messsyadmin.Activity.MessMenuActivity;
import com.hackslash.messsyadmin.Activity.MessNoticeActivity;
import com.hackslash.messsyadmin.Activity.MessReportIssueActivity;
import com.hackslash.messsyadmin.R;

public class MessHomeFragment extends Fragment implements View.OnClickListener {
    CardView conversation, viewNotice, ReportIssue, Menu;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mess_home, container, false);
//        conversation = (CardView) view.findViewById(R.id.card_view1); -->no need of this
        viewNotice = (CardView) view.findViewById(R.id.card_view2);
        ReportIssue = (CardView) view.findViewById(R.id.card_view3);
        Menu = (CardView) view.findViewById(R.id.card_view4);

//        conversation.setOnClickListener(this);
        viewNotice.setOnClickListener(this);
        ReportIssue.setOnClickListener(this);
        Menu.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//      --> no need of this
//            case R.id.card_view1:
//                Toast.makeText(getActivity(), "Opening Conversations", Toast.LENGTH_SHORT).show();
//                Intent i1 = new Intent(getContext(), MessConversationActivity.class);
//                startActivity(i1);
//                break;
            case R.id.card_view2:
                Toast.makeText(getActivity(), "Opening Notice", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(getContext(), MessNoticeActivity.class);
                startActivity(i2);
                break;
            case R.id.card_view3:
                Toast.makeText(getActivity(), "Opening Issues", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(getContext(), MessReportIssueActivity.class);
                startActivity(i3);
                break;
            case R.id.card_view4:
                Toast.makeText(getActivity(), "Opening Menu", Toast.LENGTH_SHORT).show();
                Intent messMenu = new Intent(getContext(), MessMenuActivity.class);
                startActivity(messMenu);
                break;


        }
    }
}