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
import com.hackslash.messsyadmin.MessMember.ConversationMembers;
import com.hackslash.messsyadmin.MessMember.MenuMembers;
import com.hackslash.messsyadmin.MessMember.ReportAnIssueMembers;
import com.hackslash.messsyadmin.MessMember.ViewNoticeMembers;

public class HomePageMembersUI extends Fragment implements View.OnClickListener {
    CardView conversation, viewNotice, ReportIssue, Menu;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_members_u_i, container, false);
        conversation = (CardView) view.findViewById(R.id.card_view1);
        viewNotice = (CardView) view.findViewById(R.id.card_view2);
        ReportIssue = (CardView) view.findViewById(R.id.card_view3);
        Menu = (CardView) view.findViewById(R.id.card_view4);

        conversation.setOnClickListener(this);
        viewNotice.setOnClickListener(this);
        ReportIssue.setOnClickListener(this);
        Menu.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.card_view1:
                Toast.makeText(getActivity(), "Opening Conversations", Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(getContext(), ConversationMembers.class);
                startActivity(i1);
                break;
            case R.id.card_view2:
                Toast.makeText(getActivity(), "Opening Notice", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(getContext(), ViewNoticeMembers.class);
                startActivity(i2);
                break;
            case R.id.card_view3:
                Toast.makeText(getActivity(), "Opening Issues", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(getContext(), ReportAnIssueMembers.class);
                startActivity(i3);
                break;
            case R.id.card_view4:
                Toast.makeText(getActivity(), "Opening Menu", Toast.LENGTH_SHORT).show();
                Intent i4 = new Intent(getContext(), MenuMembers.class);
                startActivity(i4);
                break;


        }
    }
}