package com.hackslash.messsyadmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hackslash.messsyadmin.Adapters.UsersAdapter;
import com.hackslash.messsyadmin.Model.UsersAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;

public class AdminMessageMessFragment extends Fragment {

    UsersAdapter chatAdapter;
    ArrayList<UsersAdapterClass> data = new ArrayList<>();
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_messagemess,container,false);
        data.clear();

        recyclerView = view.findViewById(R.id.recyclerView2);

//        data.add(new UsersAdapterClass("Kosi","hello","Just Now" ,R.drawable.kosi_icon));
//        data.add(new UsersAdapterClass("Ganga","Whats Up?","Just Now" ,R.drawable.ganga_icon));
//        data.add(new UsersAdapterClass("Brahmaputra","hii","Just Now" ,R.drawable.brahmputra_icon));
//        data.add(new UsersAdapterClass("Sone","What are u do'in?","12:54 P.M." ,R.drawable.sone_icon));
//
//        chatAdapter = new UsersAdapter(data);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatAdapter);
        return view;
    }
}
