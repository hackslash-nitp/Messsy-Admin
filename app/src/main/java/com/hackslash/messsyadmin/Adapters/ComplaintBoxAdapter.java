package com.hackslash.messsyadmin.Adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackslash.messsyadmin.Model.ComplaintBoxAdapterClass;
import com.hackslash.messsyadmin.R;

import java.util.List;

public class ComplaintBoxAdapter extends RecyclerView.Adapter<ComplaintBoxAdapter.viewHolder> {

    private List<ComplaintBoxAdapterClass> data;

    public ComplaintBoxAdapter(List<ComplaintBoxAdapterClass> data){
        this.data = data;
 }

    @NonNull
    @Override
    public ComplaintBoxAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_complaint_box_adapter_layout,parent,false);
        return new viewHolder(itemView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.imageiconIV.setImageResource(data.get(position).getmImageResourceId());
        holder.descriptionTV.setText(data.get(position).getsDescription());
        holder.nameTV.setText(data.get(position).getsName());
        holder.headingTV.setText(data.get(position).getsHeading());
        holder.dateTV.setText(data.get(position).getsDate());
        holder.upVotesTV.setText(data.get(position).getsUpvotes());
        holder.commentTV.setText(data.get(position).getsComments());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView imageiconIV;
        TextView nameTV , headingTV , dateTV, upVotesTV, commentTV, descriptionTV;

        public viewHolder( @NonNull View view){
            super(view);

            imageiconIV = view.findViewById(R.id.person_icon);
            nameTV = view.findViewById(R.id.name_of_person);
            headingTV = view.findViewById(R.id.headingOfComplain);
            dateTV = view.findViewById(R.id.date_complaint);
            upVotesTV = view.findViewById(R.id.upvotesComplaint);
            commentTV = view.findViewById(R.id.commentComplaint);
            descriptionTV = view.findViewById(R.id.descriptionComplain);

        }

    }

}