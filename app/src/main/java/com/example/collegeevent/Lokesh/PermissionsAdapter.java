package com.example.collegeevent.Lokesh;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeevent.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PermissionsAdapter extends RecyclerView.Adapter<PermissionsAdapter.MyViewHolder> {

Context context;
ArrayList<Permission> permissions;

public PermissionsAdapter(Context c,ArrayList<Permission> p)
{
    context=c;
    permissions=p;
}


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(permissions.get(position).getName());
        holder.group.setText(permissions.get(position).getGroup());
        holder.desc.setText(permissions.get(position).getDesc());
        holder.types.setText(permissions.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return permissions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
    TextView name,group,desc,types;
    Button pdfgen;
    ImageButton correct,wrong;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.adminname);
            group= itemView.findViewById(R.id.groupName);
            desc= itemView.findViewById(R.id.descriptionGroup);
            types= itemView.findViewById(R.id.type);
            pdfgen= itemView.findViewById(R.id.pdf);
            correct= itemView.findViewById(R.id.approve);
            wrong= itemView.findViewById(R.id.reject);

        }
    }
}
