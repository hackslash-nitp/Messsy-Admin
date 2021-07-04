package com.hackslash.messsyadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hackslash.messsyadmin.Adapters.AdminWalletChatBoxAdapter;
import com.hackslash.messsyadmin.Model.AdminWalletChatBoxAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;
import java.util.List;

public class AdminWalletChatBoxActivity extends AppCompatActivity {

    List<AdminWalletChatBoxAdapterClass> messageData = new ArrayList<>();
    RecyclerView recyclerView;
    AdminWalletChatBoxAdapter adminWalletChatBoxSenderAdapter;
    ImageButton sendButton , addButton;
    EditText userMessageET;
    String sUserMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_wallet_chatbox);

        messageData.add(new AdminWalletChatBoxAdapterClass(R.drawable.admin_profile_fragment_img, "Rohan1234", "Hello"));
        messageData.add(new AdminWalletChatBoxAdapterClass(R.drawable.admin_profile_fragment_img, "Shyam12", "Hello"));
        messageData.add(new AdminWalletChatBoxAdapterClass(R.drawable.admin_profile_fragment_img, "Raju70", "Hello"));
        messageData.add(new AdminWalletChatBoxAdapterClass(R.drawable.admin_profile_fragment_img, "Virat18", "Hello"));
        messageData.add(new AdminWalletChatBoxAdapterClass(R.drawable.admin_profile_fragment_img, "Dhoni7", "How are You"));
        messageData.add(new AdminWalletChatBoxAdapterClass(R.drawable.admin_profile_fragment_img, "Rohit45", "Fine"));
        messageData.add(new AdminWalletChatBoxAdapterClass(R.drawable.admin_profile_fragment_img, "Sachin10", "Fine"));
        messageData.add(new AdminWalletChatBoxAdapterClass(R.drawable.admin_profile_fragment_img, "Gayle333", "Fine"));
        messageData.add(new AdminWalletChatBoxAdapterClass(R.drawable.admin_profile_fragment_img, "Abd17", "What are u doing?"));
        messageData.add(new AdminWalletChatBoxAdapterClass(R.drawable.admin_profile_fragment_img, "Jadeja11", "Nothing much"));
        messageData.add(new AdminWalletChatBoxAdapterClass(R.drawable.admin_profile_fragment_img, "Sehwag50", "just sitting with friends"));

        userMessageET = findViewById(R.id.userMessage);
        sendButton = findViewById(R.id.button_send);
        addButton = findViewById(R.id.addButton);

        recyclerView = findViewById(R.id.recyclerViewMessage);
        adminWalletChatBoxSenderAdapter = new AdminWalletChatBoxAdapter(messageData);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sUserMessage = userMessageET.getText().toString();
                if (sUserMessage.equalsIgnoreCase("")) {
                    Toast.makeText(AdminWalletChatBoxActivity.this, "Can't send empty message", Toast.LENGTH_SHORT).show();
                } else {
                    messageData.add(new AdminWalletChatBoxAdapterClass(R.drawable.admin_profile_fragment_img,"me",sUserMessage));
                   userMessageET.setText("");

                }


            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminWalletChatBoxActivity.this, "Clicked on Add Button", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adminWalletChatBoxSenderAdapter);


    }
}