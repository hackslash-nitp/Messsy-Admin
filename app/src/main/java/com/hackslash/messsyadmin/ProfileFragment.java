package com.hackslash.messsyadmin;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class ProfileFragment extends Fragment {
    private ImageView profileImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

           profileImage = (ImageView)view.findViewById(R.id.profile_image);

             String url = "https://th.bing.com/th/id/OIP.vxVLwAKkFacSqbweyL_-twAAAA?pid=ImgDet&w=280&h=280&rs=1";


        Glide.with(getContext()).load(url).into(profileImage);
        return view;
    }
}
