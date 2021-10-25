package com.hackslash.messsyadmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackslash.messsyadmin.Activity.AdminRegisterActivity;
import com.hackslash.messsyadmin.Activity.ChatActivity;
import com.hackslash.messsyadmin.Model.UserClass;
import com.hackslash.messsyadmin.Model.UsersAdapterClass;
import com.hackslash.messsyadmin.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewHolder> {
    String name , uId;
    private List<UserClass> users;
    private Context context;

    public UsersAdapter(List<UserClass> chat , Context context){
        users = chat;
        this.context = context;
    }


    @NonNull
    @Override
    public UsersAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.chat_layout ,parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        String senderId = FirebaseAuth.getInstance().getUid();
        String senderRoom = senderId + users.get(position).getUId();

        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                        String lastMessage = snapshot.child("lastMsg").getValue(String.class);
                        String lastMessageTime = snapshot.child("lastMsgTime").getValue().toString();
                        String lastMessageDate = snapshot.child("lastMsgdate").getValue().toString();
                        String time = lastMessageDate + " " + lastMessageTime ;
                        holder.timeTV.setText(time);
                        holder.lastMessageTV.setText(lastMessage);
                        }else{
                            String temp = "Tap to chat";
                            holder.lastMessageTV.setText(temp);
                            holder.timeTV.setText(" ");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        holder.nameTV.setText(users.get(position).getsName());
        Glide.with(context).load(users.get(position).getImageUrl()).placeholder(R.drawable.mess_member_profile_image).into(holder.profileImageIV);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chatActivityIntent = new Intent(context , ChatActivity.class);
                chatActivityIntent.putExtra("name", users.get(position).getsName());
                chatActivityIntent.putExtra("uid",users.get(position).getUId());
                context.startActivity(chatActivityIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView profileImageIV;
        TextView nameTV , lastMessageTV , timeTV;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageIV = itemView.findViewById(R.id.profileImage);
            nameTV = itemView.findViewById(R.id.name1);
            lastMessageTV = itemView.findViewById(R.id.last_message);
            timeTV = itemView.findViewById(R.id.time1);
        }
    }
}
