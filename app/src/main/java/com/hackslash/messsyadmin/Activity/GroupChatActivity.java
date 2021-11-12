package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackslash.messsyadmin.Adapters.MessageGroupAdapter;
import com.hackslash.messsyadmin.Model.MessageClass;
import com.hackslash.messsyadmin.Model.UserClass;
import com.hackslash.messsyadmin.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity {

    List<MessageClass> messageClassArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    MessageGroupAdapter messageGroupAdapter;
    ImageButton sendButton, addButton;
    FirebaseDatabase database;
    UserClass user;
    FirebaseUser currentUser;
    DocumentReference docref;
    FirebaseStorage storage;
    StorageReference storageReference;
    EditText userMessageET;
    private static final int PICK_IMAGE = 2;
    Uri imageUri;
    String senderMessage, senderUid, name, profileImageUrl, ImagePath = "null";
    ProgressDialog loadingDialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        userMessageET = findViewById(R.id.userMessage);
        sendButton = findViewById(R.id.button_send);
        addButton = findViewById(R.id.addButton);
        messageGroupAdapter = new MessageGroupAdapter(getApplicationContext(), messageClassArrayList);

        recyclerView = findViewById(R.id.recyclerViewMessage);
        database = FirebaseDatabase.getInstance();
        senderUid = FirebaseAuth.getInstance().getUid();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();

        docref = FirebaseFirestore.getInstance().collection("UserInformation").document(currentUser.getUid());

        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    user = documentSnapshot.toObject(UserClass.class);
                    assert user != null;
                    name = user.getsName();
                    profileImageUrl = user.getImageUrl();
                } else {
                    Toast.makeText(getApplicationContext(), "Information doesn't exists ", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        database.getReference().child("adminGroupChat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageClassArrayList.clear();

                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            MessageClass message = snapshot1.getValue(MessageClass.class);
                            messageClassArrayList.add(message);

                        }
                        messageGroupAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(GroupChatActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userMessageET.getText().toString().equals("")) {
                    userMessageET.setError("Can't Send Empty Messages");
                    return;
                }
                senderMessage = userMessageET.getText().toString();
                String messageTime, messageDate;
                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                messageDate = currentDate.format(calendar.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
                messageTime = currentTime.format(calendar.getTime());

                MessageClass message = new MessageClass(senderMessage, senderUid, name, profileImageUrl, messageTime, messageDate);
                userMessageET.setText("");
                String randomKey = database.getReference().push().getKey();

                assert randomKey != null;
                database.getReference().child("globalGroupChat")
                        .child(randomKey)
                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(GroupChatActivity.this, "Message Uploaded On Realtime Database", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GroupChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri = null;
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messageGroupAdapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {


            assert data != null;
            imageUri = data.getData();
            loadingDialogBox = ProgressDialog.show(GroupChatActivity.this, "Sending Image", "Please Wait...", true, false);

//            new ProgressTask(loadingDialogBox).execute();
            senderMessage = userMessageET.getText().toString();
            String messageTime, messageDate;
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            messageDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
            messageTime = currentTime.format(calendar.getTime());

            MessageClass message = new MessageClass(senderMessage, senderUid, name, profileImageUrl, messageTime, messageDate);
            userMessageET.setText("");

            String randomKey = database.getReference().push().getKey();
            assert randomKey != null;


            if (imageUri != null) {
                storageReference = storage.getReference().child("images").child(currentUser.getUid());
                storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                ImagePath = uri.toString();
                                MessageClass message = new MessageClass(senderMessage, senderUid, name, profileImageUrl, messageTime, messageDate, ImagePath);
                                database.getReference().child("globalGroupChat")
                                        .child(randomKey)
                                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        ImagePath = "null";
                                        imageUri = null;
                                        loadingDialogBox.dismiss();
                                        Toast.makeText(GroupChatActivity.this, "Message Uploaded On Realtime Database", Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(GroupChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GroupChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
