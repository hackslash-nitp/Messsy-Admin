package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackslash.messsyadmin.Adapters.MessageAdapter;
import com.hackslash.messsyadmin.Model.MessageClass;
import com.hackslash.messsyadmin.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    MessageAdapter messageAdapter;
    ArrayList<MessageClass> messageClassArrayList;
    String sName ,senderRoom , receiverRoom , receiverUid , senderUid ,ImagePath = "null";
    Toolbar toolbar;
    Button sendButton , addButton;
    EditText messageET ;
    Uri imageUri;
    Bitmap bitmap;
    private static final int PICK_IMAGE = 1;
    FirebaseDatabase database;
    FirebaseStorage storage ;
    StorageReference storageReference;
    RecyclerView recyclerView;
    FirebaseUser user;
    ProgressDialog loadingDialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        user = FirebaseAuth.getInstance().getCurrentUser();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sendButton = (Button) findViewById(R.id.button_send);
        messageET = (EditText) findViewById(R.id.userMessage);
        addButton = (Button) findViewById(R.id.addButton1) ;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMessage) ;
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
//        loadingDialogBox = new ProgressDialog(this);
//        loadingDialogBox.setTitle("Sending Image");
//        loadingDialogBox.setMessage("Please Wait...");
//        loadingDialogBox.setCanceledOnTouchOutside(false);

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri = null;
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery,"Select Picture"),PICK_IMAGE);
//                Toast.makeText(ChatActivity.this, "Image uri = " +imageUri, Toast.LENGTH_SHORT).show();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String messageText = messageET.getText().toString();
                if(messageText.equals("")){
                    messageET.setError("Can't Send Empty messages");
                    return;
                }
                String messageTime , messageDate;
                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                messageDate = currentDate.format(calendar.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
                messageTime = currentTime.format(calendar.getTime());



                MessageClass message = new MessageClass(messageText , senderUid , messageTime ,messageDate, "null");
                messageET.setText("");


                HashMap<String , Object> lastMsgObj = new HashMap<>();
                if(message.getMessage().equals("") && !message.getImageUrl().equals("")){
                    lastMsgObj.put("lastMsg" , "Image");
                }else{
                    lastMsgObj.put("lastMsg" , message.getMessage());
                }
                lastMsgObj.put("lastMsgTime",messageTime);
                lastMsgObj.put("lastMsgdate",messageDate);
                database.getReference().child("chats").child(senderRoom).updateChildren(lastMsgObj);
                database.getReference().child("chats").child(receiverRoom).updateChildren(lastMsgObj);
                String randomKey = database.getReference().push().getKey();

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

//                                Toast.makeText(ChatActivity.this, "Message Updated On firebase" + "Image path = " + ImagePath, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){


            assert data != null;
            imageUri = data.getData();
            loadingDialogBox = ProgressDialog.show(ChatActivity.this , "Sending Image" ,"Please Wait..." ,true ,false);

//            new ProgressTask().execute();
            String messageText = messageET.getText().toString();

            String messageTime , messageDate;
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            messageDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
            messageTime = currentTime.format(calendar.getTime());
            messageET.setText("");
            MessageClass message = new MessageClass(messageText , senderUid , messageTime , messageDate , ImagePath);


            HashMap<String , Object> lastMsgObj = new HashMap<>();
            if(message.getMessage().equals("") && !message.getImageUrl().equals("")){
                lastMsgObj.put("lastMsg" , "Image");
            }else{
                lastMsgObj.put("lastMsg" , message.getMessage());
            }
            lastMsgObj.put("lastMsgTime",messageTime);
            lastMsgObj.put("lastMsgdate",messageDate);
            database.getReference().child("chats").child(senderRoom).updateChildren(lastMsgObj);
            database.getReference().child("chats").child(receiverRoom).updateChildren(lastMsgObj);
            String randomKey = database.getReference().push().getKey();

                if (imageUri != null) {
                    storageReference = storage.getReference().child("images").child(user.getUid());
                    storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    ImagePath = uri.toString();
                                    MessageClass message = new MessageClass(messageText, senderUid, messageTime, messageDate, ImagePath);
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
                                                    ImagePath = "null";
                                                    imageUri = null;
                                                    loadingDialogBox.dismiss();
                                                    Toast.makeText(ChatActivity.this, "Image uploaded on firebase", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


        }
        }
    }
