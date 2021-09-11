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

import com.hackslash.messsyadmin.Adapters.ChatAdapter;
import com.hackslash.messsyadmin.Model.ChatAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;

public class MessMessageAdminFragment extends Fragment {
    ChatAdapter chatAdapter;
    ArrayList<ChatAdapterClass> data = new ArrayList<>();
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mess_messageadmin,container,false);
        data.clear();

        recyclerView = view.findViewById(R.id.recyclerView2);

        data.add(new ChatAdapterClass("King Kohli","Bhai 50 maar diya me","Just Now" ,R.drawable.admin_profile_fragment_img));
        data.add(new ChatAdapterClass("God Sachin","Whats Up?","Just Now" ,R.drawable.admin_profile_fragment_img));
        data.add(new ChatAdapterClass("Fearless Sehwag","Kya kr rha bhai","Just Now" ,R.drawable.admin_profile_fragment_img));
        data.add(new ChatAdapterClass("Lord Shardul","Bhai bowling sikha de thodi","12:54 P.M." ,R.drawable.admin_profile_fragment_img));

        chatAdapter = new ChatAdapter(data);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatAdapter);
        return view;
    }
}
