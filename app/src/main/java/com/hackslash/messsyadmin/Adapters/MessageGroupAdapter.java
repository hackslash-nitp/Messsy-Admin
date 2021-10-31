package com.hackslash.messsyadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.hackslash.messsyadmin.Model.MessageClass;
import com.hackslash.messsyadmin.R;

import java.util.List;
import java.util.Objects;

public class MessageGroupAdapter extends RecyclerView.Adapter<MessageGroupAdapter.viewHolder> {
    final int ITEM_SENT = 1;
    final int ITEM_RECEIVE = 2;

    private List<MessageClass> messageData;
    Context context;


    public MessageGroupAdapter(Context context , List<MessageClass> messageClasses){
        messageData = messageClasses;
        this.context = context;

    }


    @NonNull
    @Override
    public MessageGroupAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == ITEM_SENT){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.admin_wallet_chatbox_adapter_user_layout,parent,false);
        return new viewHolder(itemView);
        }

        else{
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.admin_wallet_chatbox_adapter_sender_layout,parent,false);
            return new viewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String message = messageData.get(position).getMessage();
        String ImageUrl = messageData.get(position).getImageUrl();

    holder.usernameTV.setText(messageData.get(position).getName());
    Glide.with(context).load(messageData.get(position).getProfileImage()).placeholder(R.drawable.image).into(holder.profilePicIV);


        if(message.equals("")) {
            holder.messageTV.setHeight(0);
            holder.messageTV.setWidth(0);
            holder.messageTV.setVisibility(View.INVISIBLE);
        }else{
            holder.messageTV.setText(message);
        }

            if (!ImageUrl.equals("null")) {
                holder.ImageIV.setVisibility(View.VISIBLE);
                Glide.with(context).load(ImageUrl).into(holder.ImageIV);
            }

    }


    @Override
    public int getItemCount() {
        return messageData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView profilePicIV , ImageIV;
        TextView messageTV , usernameTV;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profilePicIV = itemView.findViewById(R.id.hostel_image);
            messageTV = itemView.findViewById(R.id.senderMessage);
            usernameTV = itemView.findViewById(R.id.username);
            ImageIV = itemView.findViewById(R.id.image4);


        }
    }

    @Override
    public int getItemViewType(int position) {

        MessageClass message = messageData.get(position);

        if (Objects.requireNonNull(FirebaseAuth.getInstance().getUid()).equalsIgnoreCase(message.getSenderId())) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;
        }

    }
}
