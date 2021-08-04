package com.hackslash.messsyadmin.Adapters;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.hackslash.messsyadmin.Model.MessMenuAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.List;


public class MessMenuAdapter extends RecyclerView.Adapter<MessMenuAdapter.viewHolder> {

    private List<MessMenuAdapterClass> data;

    public MessMenuAdapter(List<MessMenuAdapterClass> data){
        this.data = data;
    }

    @NonNull
    @Override
    public MessMenuAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_mess_menu_adapter,parent,false);
        return new viewHolder(itemView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.mealtimeTV.setText(data.get(position).getmealtime());
        holder.mMondayTV.setText(data.get(position).getmMonday());
        holder.mTuesdayTV.setText(data.get(position).getmTuesday());
        holder.mWednesdayTV.setText(data.get(position).getmWednesday());
        holder.mThursdayTV.setText(data.get(position).getmThursday());
        holder.mFridayTV.setText(data.get(position).getmFriday());
        holder.mSaturdayTV.setText(data.get(position).getmSaturday());
        holder.mSundayTV.setText(data.get(position).getmSunday());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{


        TextView mealtimeTV , mMondayTV , mTuesdayTV,  mWednesdayTV, mThursdayTV, mFridayTV, mSaturdayTV, mSundayTV;

        public viewHolder( @NonNull View view){
            super(view);

            mealtimeTV = view.findViewById(R.id.mealtime);
            mMondayTV = view.findViewById(R.id.mMonday);
            mTuesdayTV = view.findViewById(R.id.mTuesday);
            mWednesdayTV= view.findViewById(R.id.mWednesday);
            mThursdayTV = view.findViewById(R.id.mThursday);
            mFridayTV = view.findViewById(R.id.mFriday);
            mSaturdayTV = view.findViewById(R.id.mSaturday);
            mSundayTV = view.findViewById(R.id.mSunday);


        }

    }

}
