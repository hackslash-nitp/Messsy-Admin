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

import com.hackslash.messsyadmin.Model.AbsenteesListAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;
import java.util.List;

public class AbsenteesListAdapter extends RecyclerView.Adapter<AbsenteesListAdapter.ViewHolder> implements Adapter
{

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
    public void onBindViewHolder(@NonNull AbsenteesListAdapter.ViewHolder holder, int position) {
        String serial_number =userList.get(position).getSerialNumber();
        String student_name = userList.get(position).getStudentName();
        int hostel_icon = userList.get(position).getHostelIcon();
        String rollNumber = userList.get(position).getRollNumber();


        holder.setData(serial_number, student_name, hostel_icon, rollNumber);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public void filterList(ArrayList<AbsenteesListAdapterClass> filteredList) {
        userList = filteredList;
        notifyDataSetChanged();
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView SNo;
        private TextView Name;
        private TextView Roll;
        private ImageView Hostel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            SNo = itemView.findViewById(R.id.tv_serial_number);
            Name = itemView.findViewById(R.id.tv_student_name);
            Roll = itemView.findViewById(R.id.tv_student_roll);
            Hostel = itemView.findViewById(R.id.iv_hostel_icon);

        }

        public void setData(String serial_number, String student_name, int hostel_icon, String rollNumber) {
            SNo.setText(serial_number);
            Name.setText(student_name);
            Hostel.setImageResource(hostel_icon);
            Roll.setText(rollNumber);
        }
    }

}
