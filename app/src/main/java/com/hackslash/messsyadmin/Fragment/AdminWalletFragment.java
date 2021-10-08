package com.hackslash.messsyadmin.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.hackslash.messsyadmin.Activity.GroupChatActivity;
import com.hackslash.messsyadmin.R;

public class AdminWalletFragment extends Fragment implements View.OnClickListener{
    CardView kosiHostelCV , gangaHostelCV , brahmaputraHostelCV, soneHostelCV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           View view =  inflater.inflate(R.layout.fragment_admin_wallet,container,false);


        kosiHostelCV = view.findViewById(R.id.kosiHostel);
        brahmaputraHostelCV =view.findViewById(R.id.brahmaputraHostel);
        gangaHostelCV = view.findViewById(R.id.gangaHostel);
        soneHostelCV = view.findViewById(R.id.soneHostel);

        kosiHostelCV.setOnClickListener(this);
        brahmaputraHostelCV.setOnClickListener(this);
        gangaHostelCV.setOnClickListener(this);
        soneHostelCV.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.kosiHostel:
                Toast.makeText(getActivity(), "Opening chatbox for kosi hostel", Toast.LENGTH_SHORT).show();
                Intent kosiHostelChatBox = new Intent(getContext(), GroupChatActivity.class);
                kosiHostelChatBox.putExtra("hostelPicResourceId",R.drawable.kosi_hoste_pic_new_2);
                kosiHostelChatBox.putExtra("nameOfHostel","Kosi Hostel");
                startActivity(kosiHostelChatBox);
                break;

            case R.id.brahmaputraHostel:
                Toast.makeText(getActivity(), "Opening chatbox for brahmaputra hostel", Toast.LENGTH_SHORT).show();
                Intent brahmaputraHostelChatBox = new Intent(getContext(), GroupChatActivity.class);
                brahmaputraHostelChatBox.putExtra("hostelPicResourceId",R.drawable.brahmaputra_hostel_pic);
                brahmaputraHostelChatBox.putExtra("nameOfHostel","Brahmaputra Hostel");
                startActivity(brahmaputraHostelChatBox);
                break;

            case R.id.gangaHostel:
                Toast.makeText(getActivity(), "Opening chatbox for ganga hostel", Toast.LENGTH_SHORT).show();
                Intent gangaHostelChatBox = new Intent(getContext(), GroupChatActivity.class );
                gangaHostelChatBox.putExtra("hostelPicResourceId",R.drawable.ganga_hostel_pic);
                gangaHostelChatBox.putExtra("nameOfHostel","Ganga Hostel");
                startActivity(gangaHostelChatBox);
                break;

            case R.id.soneHostel:
                Toast.makeText(getActivity(), "Opening chatbox for sone hostel", Toast.LENGTH_SHORT).show();
                Intent soneHostelChatBox = new Intent(getContext(), GroupChatActivity.class );
                soneHostelChatBox.putExtra("hostelPicResourceId",R.drawable.sone_hostel_pic_15x);
                soneHostelChatBox.putExtra("nameOfHostel","Brahmaputra Hostel");
                startActivity(soneHostelChatBox);
                break;

        }

    }
}
