package com.hackslash.messsyadmin.Adapters;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hackslash.messsyadmin.Model.AbsenteesListAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;
import java.util.List;

public class AbsenteesListAdapter extends RecyclerView.Adapter<AbsenteesListAdapter.ViewHolder>{

    private List<AbsenteesListAdapterClass> userList;

    public AbsenteesListAdapter (List<AbsenteesListAdapterClass>userList){ this.userList=userList;}

    @NonNull
    @Override
    public AbsenteesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_absentees_list_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.SNo.setText(String.valueOf(" "+(position+1)));
        holder.Name.setText(userList.get(position).getStudentName());
        holder.Roll.setText(userList.get(position).getRollNumber());
        if (userList.get(position).getHostelIcon().equalsIgnoreCase("Brahmputra"))
            holder.Hostel.setImageResource(R.drawable.brahmputra_icon);
        else if (userList.get(position).getHostelIcon().equalsIgnoreCase("Kosi"))
            holder.Hostel.setImageResource(R.drawable.kosi_icon);
        else if (userList.get(position).getHostelIcon().equalsIgnoreCase("Sone"))
            holder.Hostel.setImageResource(R.drawable.sone_icon);
        else if (userList.get(position).getHostelIcon().equalsIgnoreCase("Ganga"))
            holder.Hostel.setImageResource(R.drawable.ganga_icon);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public void filterList(ArrayList<AbsenteesListAdapterClass> filteredList) {
        userList = filteredList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView SNo, Name, Roll;
        ImageView Hostel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            SNo = itemView.findViewById(R.id.tv_serial_number);
            Name = itemView.findViewById(R.id.tv_student_name);
            Roll = itemView.findViewById(R.id.tv_student_roll);
            Hostel = itemView.findViewById(R.id.iv_hostel_icon);

        }

    }

}
