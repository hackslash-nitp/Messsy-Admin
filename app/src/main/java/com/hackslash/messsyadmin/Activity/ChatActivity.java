package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackslash.messsyadmin.Adapters.MessageAdapter;
import com.hackslash.messsyadmin.Model.MessageClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    MessageAdapter messageAdapter;
    ArrayList<MessageClass> messageClassArrayList;
    String sName ,senderRoom , receiverRoom , receiverUid , senderUid;
    Toolbar toolbar;
    Button sendButton;
    EditText messageET ;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        user = FirebaseAuth.getInstance().getCurrentUser();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sendButton = (Button) findViewById(R.id.button_send);
        messageET = (EditText) findViewById(R.id.userMessage);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMessage) ;
        database = FirebaseDatabase.getInstance();


        setSupportActionBar(toolbar);

        messageClassArrayList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this , messageClassArrayList);

        sName = getIntent().getStringExtra("name");
        receiverUid = getIntent().getStringExtra("uid");
        Toast.makeText(this, "Name = " + sName, Toast.LENGTH_SHORT).show();
        if(user != null){
        senderUid = FirebaseAuth.getInstance().getUid();}

        senderRoom = senderUid + receiverUid;
        receiverRoom = receiverUid + senderUid;



        database.getReference().child("chats")
                .child(senderRoom)
                .child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageClassArrayList.clear();

                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            MessageClass message = snapshot1.getValue(MessageClass.class);
                            messageClassArrayList.add(message);
                        }
                        messageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ChatActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();

                Date date = new Date();
                MessageClass message = new MessageClass(messageText , senderUid , date.getTime());
                messageET.setText("");

                String randomKey = database.getReference().push().getKey();

                HashMap<String , Object> lastMsgObj = new HashMap<>();
                lastMsgObj.put("lastMsg" , message.getMessage());
                lastMsgObj.put("lastMsgTime",date.getTime());
                database.getReference().child("chats").child(senderRoom).updateChildren(lastMsgObj);
                database.getReference().child("chats").child(receiverRoom).updateChildren(lastMsgObj);

                assert randomKey != null;
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .child(randomKey)
                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        database.getReference().child("chats")
                                .child(receiverRoom)
                                .child("messages")
                                .child(randomKey)
                                .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChatActivity.this, "Message Updated On firebase", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(sName);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messageAdapter);

    }
}