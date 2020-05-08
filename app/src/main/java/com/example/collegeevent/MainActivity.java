package com.example.collegeevent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerViewCollege, mRecyclerViewGroup;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRefCollege, mRefGroup;

    ArrayList<Event_Model> event_List_college;
    ArrayList<Event_Model> event_List_group;
    Button mCreateEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerViewCollege = findViewById(R.id.recycler_college_event);
        mRecyclerViewGroup = findViewById(R.id.recycler_group_event);
        mCreateEvent = (Button)findViewById(R.id.btnCreateEvent);
        //mRecyclerViewCollege.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerViewGroup.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRefCollege = firebaseDatabase.getReference("collegeEvent");
        mRefGroup = firebaseDatabase.getReference("groupEvent");
        //college events
        collegeContentRecycler();
        //group events
        groupContentRecycler();
        mCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Create_Event.class));
            }
        });
    }
    public void collegeContentRecycler() {
        //eeshan's code
        mRecyclerViewCollege.clearOnScrollListeners();
        mRecyclerViewCollege.clearOnChildAttachStateChangeListeners();
        // list.clear();
        event_List_college=new ArrayList<Event_Model>();

        mRefCollege.addValueEventListener(new ValueEventListener() {
            Event_Model event_model=new Event_Model();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Event_Model value;
                event_List_college.clear();
                for(DataSnapshot x:dataSnapshot.getChildren())
                {
                    value=x.getValue(Event_Model.class);
                    event_List_college.add(value);
                    // Toast.makeText(Main2Activity.this, "item: "+list.get(i).title, Toast.LENGTH_LONG).show();

                }
                mRecyclerViewCollege.clearOnScrollListeners();
                mRecyclerViewCollege.clearOnChildAttachStateChangeListeners();

                //This sets the all data from the firebase onto the adapter
                MyAdapter myAdapter= new MyAdapter(event_List_college);
                RecyclerView.LayoutManager recyce=new GridLayoutManager(mRecyclerViewCollege.getContext(),1);
                mRecyclerViewCollege.setLayoutManager(recyce);
                mRecyclerViewCollege.setItemAnimator(new DefaultItemAnimator());
                mRecyclerViewCollege.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void groupContentRecycler()
    {
        //eeshan's code
        mRecyclerViewGroup.clearOnScrollListeners();
        mRecyclerViewGroup.clearOnChildAttachStateChangeListeners();
        // list.clear();
        event_List_group=new ArrayList<Event_Model>();

        mRefGroup.addValueEventListener(new ValueEventListener() {
            Event_Model event_model=new Event_Model();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Event_Model group_value;
                event_List_group.clear();
                for(DataSnapshot x:dataSnapshot.getChildren())
                {
                    group_value=x.getValue(Event_Model.class);
                    event_List_group.add(group_value);
                    // Toast.makeText(Main2Activity.this, "item: "+list.get(i).title, Toast.LENGTH_LONG).show();

                }
                // to clears all listener in recycler view
                mRecyclerViewGroup.clearOnScrollListeners();
                mRecyclerViewGroup.clearOnChildAttachStateChangeListeners();

                //my adapter is local to group content creater fn
                MyAdapter myAdapter= new MyAdapter(event_List_group);
                // ask the use of grid layout manageer here
                RecyclerView.LayoutManager recyce=new GridLayoutManager(mRecyclerViewCollege.getContext(),1);
                mRecyclerViewGroup.setLayoutManager(recyce);
                mRecyclerViewGroup.setItemAnimator(new DefaultItemAnimator());
                mRecyclerViewGroup.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
