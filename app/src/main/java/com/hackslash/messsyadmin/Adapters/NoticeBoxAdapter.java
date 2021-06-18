package com.hackslash.messsyadmin.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackslash.messsyadmin.Model.NoticeBoxAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.List;

public class NoticeBoxAdapter extends RecyclerView.Adapter<NoticeBoxAdapter.viewHolder> {
    private List<NoticeBoxAdapterClass> data;

    public NoticeBoxAdapter(List<NoticeBoxAdapterClass> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public NoticeBoxAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_notice_box_adapter_layout, parent, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.description.setText(data.get(position).getsDescription());
        holder.hostelMessName.setText(data.get(position).getsHostelMessName());
        holder.imageView.setImageResource(data.get(position).getmImageResourceId());
        holder.date.setText(data.get(position).getsDate());
        holder.time.setText(data.get(position).getsTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView hostelMessName, description, date, time;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageIcon);
            hostelMessName = itemView.findViewById(R.id.hostelMessName);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.timeDisplay);
        }

    }
}


