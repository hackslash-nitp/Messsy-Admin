package com.hackslash.messsyadmin.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class MessGroupTalkFragment extends Fragment {
    private static final int RESULT_OK = -1;
    List<MessageClass> messageClassArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    MessageGroupAdapter messageGroupAdapter;
    ImageButton sendButton , addButton;
    FirebaseDatabase database;
    UserClass user;
    FirebaseUser currentUser;
    DocumentReference docref ;
    EditText userMessageET;
    String senderMessage , senderUid , name , profileImageUrl,ImagePath = "null";
    FirebaseStorage storage ;
    StorageReference storageReference;
    private static final int PICK_IMAGE = 2;
    Uri imageUri;
    ProgressDialog loadingDialogBox;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mess_messtalk,container,false);
        userMessageET = view.findViewById(R.id.userMessage);
        sendButton = view.findViewById(R.id.button_send);
        addButton = view.findViewById(R.id.addButton);

        recyclerView = view.findViewById(R.id.recyclerViewMessage);
        messageGroupAdapter = new MessageGroupAdapter(getActivity() , messageClassArrayList);
        database = FirebaseDatabase.getInstance();
        senderUid = FirebaseAuth.getInstance().getUid();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        docref = FirebaseFirestore.getInstance().collection("UserInformation").document(currentUser.getUid());

        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    user = documentSnapshot.toObject(UserClass.class);
                    assert user != null;
                    name = user.getsName();
                    profileImageUrl = user.getImageUrl();
                }
                else{
                    Toast.makeText(getActivity(), "Information doesn't exists ", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        database.getReference().child("globalGroupChat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageClassArrayList.clear();

                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            MessageClass message =  snapshot1.getValue(MessageClass.class);
                            messageClassArrayList.add(message);

                        }
                        messageGroupAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userMessageET.getText().toString().equals("")){
                    userMessageET.setError("Can't Send Empty Messages");
                    return;
                }
                senderMessage = userMessageET.getText().toString();
                String messageTime , messageDate;
                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                messageDate = currentDate.format(calendar.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
                messageTime = currentTime.format(calendar.getTime());

                MessageClass message = new MessageClass(senderMessage , senderUid , name , profileImageUrl,messageTime,messageDate , "null");
                userMessageET.setText("");
                String randomKey = database.getReference().push().getKey();

                assert randomKey != null;
                database.getReference().child("globalGroupChat")
                        .child(randomKey)
                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Message Uploaded On Realtime Database", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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

                startActivityForResult(Intent.createChooser(gallery,"Select Picture"),PICK_IMAGE);
                Toast.makeText(getActivity(), "Clicked on Add Button", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messageGroupAdapter);

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){



            if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
                assert data != null;
                imageUri = data.getData();
                loadingDialogBox = ProgressDialog.show(getContext(), "Sending Image", "Please Wait...", true, false);

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
                                            Toast.makeText(getContext(), "Message Uploaded On Realtime Database", Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }}}}



