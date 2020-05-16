package com.example.collegeevent.Lokesh;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.collegeevent.Create_Event;
import com.example.collegeevent.Event_Model;
import com.example.collegeevent.MainActivity;
import com.example.collegeevent.MyAdapter;
import com.example.collegeevent.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Events extends Fragment {

    RecyclerView mRecyclerViewCollege, mRecyclerViewGroup;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRefCollege, mRefGroup;

    ArrayList<Event_Model> event_List_college;
    ArrayList<Event_Model> event_List_group;
    Button mCreateEvent;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    public Fragment_Events() {
        // Required empty public constructor
    }
    @Override
    public void onStart() {
        super.onStart();
        if(currentUser == null)
        {
            startActivity(new Intent(getContext(),LoginActivity.class));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment__events, container, false);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mRecyclerViewCollege = view.findViewById(R.id.recycler_college_event);
        mRecyclerViewGroup = view.findViewById(R.id.recycler_group_event);
        mCreateEvent = (Button)view.findViewById(R.id.btnCreateEvent);
        //mRecyclerViewCollege.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerViewGroup.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        // todo: change the login student with the value obtained from intent during signup.
        mRefCollege = firebaseDatabase.getReference("LoginStudent").child(mAuth.getUid()).child("CollegeEvents");
        mRefGroup = firebaseDatabase.getReference("LoginStudent").child(mAuth.getUid()).child("GroupEvents");
        //college events
        collegeContentRecycler();
        //group events
        groupContentRecycler();
        mCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Create_Event.class));
            }
        });
        return view;
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
