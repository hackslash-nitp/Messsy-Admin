package com.hackslash.messsyadmin.Adapters;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackslash.messsyadmin.Model.FeeDetailsAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.ArrayList;

public class FeeDetailsAdapter extends RecyclerView.Adapter<FeeDetailsAdapter.ViewHolder> {

    private ArrayList<FeeDetailsAdapterClass> userList;

    public FeeDetailsAdapter(ArrayList<FeeDetailsAdapterClass> userList)
    {
        this.userList=userList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView serialno;
        private TextView Name;
        private TextView Rollno;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serialno=itemView.findViewById(R.id.student_serial);
            Name=itemView.findViewById(R.id.student_name);
            Rollno=itemView.findViewById(R.id.student_roll);
        }
    }

    @NonNull
    @Override
    public FeeDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_fee_details_adapter_layout, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull FeeDetailsAdapter.ViewHolder holder, int position) {

        String serial=userList.get(position).getSerial_number();
        String Sname=userList.get(position).getStudentName();
        String Sroll=userList.get(position).getRollNumber();

        holder.serialno.setText(serial);
        holder.Name.setText(Sname);
        holder.Rollno.setText(Sroll);

    }

    @Override
    public int getItemCount() {
         return userList.size();
    }
}
