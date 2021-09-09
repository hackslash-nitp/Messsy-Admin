package com.hackslash.messsyadmin.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackslash.messsyadmin.Model.ChatAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.viewHolder> {

    private List<ChatAdapterClass> data;

    public ChatAdapter(List<ChatAdapterClass> chat){
        data = chat;
    }


    @NonNull
    @Override
    public ChatAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.chat_layout ,parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.timeTV.setText(data.get(position).getsTime());
        holder.lastMessageTV.setText(data.get(position).getsLastMessage());
        holder.nameTV.setText(data.get(position).getsName());
        holder.profileImageIV.setImageResource(data.get(position).getmImageResourceId());
    }

    @Override
    public int getItemCount() {
        return data.size();
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
