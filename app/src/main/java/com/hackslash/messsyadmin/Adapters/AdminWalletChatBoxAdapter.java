package com.hackslash.messsyadmin.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackslash.messsyadmin.Model.AdminWalletChatBoxAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.List;

public class AdminWalletChatBoxAdapter extends RecyclerView.Adapter<AdminWalletChatBoxAdapter.viewHolder> {

    private List<AdminWalletChatBoxAdapterClass> messageData;

    public AdminWalletChatBoxAdapter(List<AdminWalletChatBoxAdapterClass> adminWalletAdapterClass){
        messageData = adminWalletAdapterClass;
    }


    @NonNull
    @Override
    public AdminWalletChatBoxAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == 1){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.admin_wallet_chatbox_adapter_sender_layout,parent,false);
        return new viewHolder(itemView);
        }

        else{
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.admin_wallet_chatbox_adapter_user_layout,parent,false);
            return new viewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.messageTV.setText(messageData.get(position).getsMessage());
        holder.profilePicIV.setImageResource(messageData.get(position).getImageResourceId());
        holder.usernameTV.setText(messageData.get(position).getsSendersUserName());
    }


    @Override
    public int getItemCount() {
        return messageData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView profilePicIV;
        TextView messageTV , usernameTV;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profilePicIV = itemView.findViewById(R.id.hostel_image);
            messageTV = itemView.findViewById(R.id.senderMessage);
            usernameTV = itemView.findViewById(R.id.username);


        }
    }

    @Override
    public int getItemViewType(int position) {

        if(messageData.get(position).getsSendersUserName().equalsIgnoreCase("me")){
            return 2;
        }

        else{
            return 1;
        }

    }
}
