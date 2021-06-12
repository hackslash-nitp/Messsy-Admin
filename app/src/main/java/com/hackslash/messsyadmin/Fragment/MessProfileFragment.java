package com.hackslash.messsyadmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.hackslash.messsyadmin.R;

public class MessProfileFragment extends Fragment {
    private ImageView profileImageIV;    // IV stands for imageview

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_profile,container,false);

        profileImageIV = (ImageView)view.findViewById(R.id.profile_image);

        String url = "https://th.bing.com/th/id/OIP.vxVLwAKkFacSqbweyL_-twAAAA?pid=ImgDet&w=280&h=280&rs=1";


        Glide.with(getContext()).load(url).into(profileImageIV);
        return view;
    }
}
