package com.example.collegeevent.Lokesh;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeevent.R;

import java.util.ArrayList;

public class Groups_Adapter extends RecyclerView.Adapter<Groups_Adapter.MyViewHolder> {
    ArrayList<Group_List> group_lists;
    Context context;
    public Groups_Adapter(Context c,ArrayList<Group_List> g)
    {
        group_lists=g;
        context=c;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.groups_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt1.setText(group_lists.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return group_lists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt1= itemView.findViewById(R.id.name_of_group);
        }
    }
}
