package com.hackslash.messsyadmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hackslash.messsyadmin.Adapters.UsersAdapter;
import com.hackslash.messsyadmin.Model.UserClass;
import com.hackslash.messsyadmin.Model.UsersAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;

public class MessDMsFragment extends Fragment {
    UsersAdapter usersAdapter;
    private FirebaseFirestore firebaseFirestore =  FirebaseFirestore.getInstance() ;
    private final CollectionReference userInfoReference = firebaseFirestore.collection("UserInformation");
    String  sName , sEmail , sMobile  ,sHostelName = "null", sDesignation = "Mess Member",sImageUrl = "null" , sUId ;
    ArrayList<UserClass> users = new ArrayList<>();
    private RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mess_dm,container,false);
        recyclerView = view.findViewById(R.id.recyclerView1);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        usersAdapter = new UsersAdapter(users , getContext());

        userInfoReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                users.clear();

                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                    sName = documentSnapshot.getString("sName");
                    sImageUrl = documentSnapshot.getString("imageUrl");
                    sEmail = documentSnapshot.getString("sEmail");
                    sDesignation = documentSnapshot.getString("sDesignation");
                    sMobile = documentSnapshot.getString("sMobile");
                    sHostelName = documentSnapshot.getString("sHostelName");
                    sUId = documentSnapshot.getString("uid");
//                    if(sDesignation.equalsIgnoreCase("Mess Member")){
                        users.add(new UserClass(sName, sEmail,sMobile,sHostelName,sDesignation,sImageUrl,sUId));
//                    }

                }
                usersAdapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(usersAdapter);



        return view;
    }
}
