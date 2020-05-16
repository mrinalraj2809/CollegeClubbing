package com.example.collegeevent.Lokesh;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeevent.ChannelGroup;
import com.example.collegeevent.R;

import java.util.ArrayList;

public class Groups_Adapter extends RecyclerView.Adapter<Groups_Adapter.MyViewHolder> {
    ArrayList<Group_List>       group_lists;
    public ImageView            imageGroupIcon;
    public TextView             groupName;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.groupName.setText(group_lists.get(position).getName());
        // VVIP note: set on click listener for the recycler adapter to be in bindview holder
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChannelGroup.class);
                intent.putExtra("GROUPNAME",group_lists.get(position).getName());
                v.getContext().startActivity(intent);

            }
        });
//        holder.groupIcon.setImageURI(Uri.parse(group_lists.get(position).getImage()));
    }

    @Override
    public int getItemCount() {
        return group_lists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView groupName;
//        TextView timeStamp;
        ImageView groupIcon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.name_of_group);
            groupIcon = itemView.findViewById(R.id.imagegroupIcon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

//            timeStamp = itemView.findViewById(R.id.textTimeStamp);
        }

    }
}
