package com.example.collegeevent;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**********************************Adapter Class***********************************/
public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Event_Model>       userList;
    LinearLayout                    linearLayout;
    DatabaseReference               databaseReference = FirebaseDatabase.getInstance().getReference("collegeEvent");
    int                             flag              = 1;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        //public TextView title, desc;
        //public String title, String eventDate, String eventTime, String venue, String reminderSet, String desciption
        public TextView             eventTitle;
        public TextView             eventDate;
        public TextView             eventTime;
        public TextView             venue;
        public TextView             t_description;
        public TextView             description;
        public Button               reminderSet;

        public MyViewHolder(View view) {
            super(view);
            eventTitle              = view.findViewById(R.id.t_title);
            eventDate               = view.findViewById(R.id.t_date);
            eventTime               = view.findViewById(R.id.t_time);
            venue                   = view.findViewById(R.id.t_venue);
            reminderSet             = view.findViewById(R.id.b_reminder);
            t_description           = view.findViewById(R.id.t_description);
            description             = view.findViewById(R.id.description);

            linearLayout            = view.findViewById(R.id.id);
        }
    }


    public MyAdapter(List<Event_Model> event_modelList) {
        this.userList = event_modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Event_Model user = userList.get(position);
        //this fn puts the data onto the each view holder.
        holder.eventTitle.setText(user.getTitle());
        holder.eventDate.setText(user.getEventDate());
        holder.eventTime.setText(user.getEventTime());
        holder.venue.setText(user.getVenue());
        holder.description.setText(user.getDescription());
//        if (user.getReminderSet() == "1")
//            holder.reminderSet.setHighlightColor(R.color.light_grey);
//        else
//            holder.reminderSet.setHighlightColor(R.color.white);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Opening! Please wait.....", Toast.LENGTH_LONG).show();
                if(flag == 1)
                {
                    flag = 0;
                    holder.t_description.setVisibility(View.VISIBLE);
                    holder.description.setVisibility(View.VISIBLE);
                }
                else
                {
                    flag = 1;
                    holder.t_description.setVisibility(View.GONE);
                    holder.description.setVisibility(View.GONE);

                }
                //Intent i = new Intent(v.getContext(), MainActivity.class);
                //i.putExtra("position", "" + (position + 1));
                //v.getContext().startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}