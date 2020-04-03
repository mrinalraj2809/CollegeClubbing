package com.example.collegeevent.Lokesh;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeevent.R;

public class Groups_ViewHolder extends RecyclerView.ViewHolder {
    public TextView txtgroup;
    public Groups_ViewHolder(@NonNull View itemView) {
        super(itemView);
        txtgroup= itemView.findViewById(R.id.name_of_group);
    }
}
