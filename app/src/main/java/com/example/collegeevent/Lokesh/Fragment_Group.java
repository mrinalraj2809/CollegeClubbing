package com.example.collegeevent.Lokesh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import android.view.ViewGroup;

import com.example.collegeevent.MyAdapter;
import com.example.collegeevent.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
    public class Fragment_Group extends Fragment implements View.OnClickListener{
    //FloatingActionButton floatingActionButton;
    DatabaseReference dbref;
    DatabaseReference collegeChatRef;
    DatabaseReference groupChatRef;
    public RecyclerView mRecyclerViewCollegeChat;
    RecyclerView mRecyclerViewGroupChat;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    ArrayList<Group_List> college_groups_lists;
    ArrayList<Group_List> subscribed_groups_lists;

    //ArrayAdapter<String> adapter;
    //ArrayList<Group_List> list_of_groups;
    //Groups_Adapter groups_adapter;
    public Fragment_Group() {
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
        View view= inflater.inflate(R.layout.fragment_fragment__group, container, false); // since fragments is a view
        setHasOptionsMenu(true); // for 3 dots
        dbref = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        collegeChatRef = FirebaseDatabase.getInstance().getReference().child("LoginStudent").child(mAuth.getUid()).child("CollegeGroups");
        groupChatRef = FirebaseDatabase.getInstance().getReference().child("LoginStudent").child(mAuth.getUid()).child("SubscribedGroups");
        mRecyclerViewCollegeChat = view.findViewById(R.id.recycler_college_groups);  // recycler view from xml
        mRecyclerViewGroupChat = view.findViewById(R.id.recycler_subscribed_group);
        retrieve_college_groups();
        retrieve_subscribed_groups();
        return view;
    }
    private void retrieve_college_groups() {
        mRecyclerViewCollegeChat.clearOnScrollListeners();
        mRecyclerViewCollegeChat.clearOnChildAttachStateChangeListeners();
        college_groups_lists = new ArrayList<Group_List>();
        collegeChatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Group_List group_list;
                college_groups_lists.clear();
                for (DataSnapshot x: dataSnapshot.getChildren())
                {
                    group_list = x.getValue(Group_List.class);
                    college_groups_lists.add(group_list);
                }
                mRecyclerViewCollegeChat.clearOnScrollListeners();
                mRecyclerViewCollegeChat.clearOnChildAttachStateChangeListeners();

                //This sets the all data from the firebase onto the adapter
                Groups_Adapter myGroupAdapter= new Groups_Adapter(getContext(),college_groups_lists);
                //myGroupAdapter.imageGroupIcon.setImageResource(R.drawable.official);
                RecyclerView.LayoutManager recyce = new GridLayoutManager(mRecyclerViewCollegeChat.getContext(),1);
                mRecyclerViewCollegeChat.setLayoutManager(recyce);
                mRecyclerViewCollegeChat.setItemAnimator(new DefaultItemAnimator());
                mRecyclerViewCollegeChat.setAdapter(myGroupAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void retrieve_subscribed_groups() {
        //mRecyclerViewGroupChat.clearOnScrollListeners();
        //mRecyclerViewGroupChat.clearOnChildAttachStateChangeListeners();
        subscribed_groups_lists = new ArrayList<Group_List>();
        groupChatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Group_List group_list;
                subscribed_groups_lists.clear();
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    group_list = x.getValue(Group_List.class);
                    subscribed_groups_lists.add(group_list);
                }
                mRecyclerViewGroupChat.clearOnScrollListeners();
                mRecyclerViewGroupChat.clearOnChildAttachStateChangeListeners();

                //This sets the all data from the firebase onto the adapter

                Groups_Adapter myGroupAdapter = new Groups_Adapter(getContext(), subscribed_groups_lists);
                //myGroupAdapter.imageGroupIcon.setImageResource(R.drawable.public_group);
                RecyclerView.LayoutManager recyce = new GridLayoutManager(mRecyclerViewGroupChat.getContext(), 1);
                mRecyclerViewGroupChat.setLayoutManager(recyce);
                mRecyclerViewGroupChat.setItemAnimator(new DefaultItemAnimator());
                mRecyclerViewGroupChat.setAdapter(myGroupAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { // 3 dots on top
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case R.id.menu_create_group://if(mAuth.getUid() == LoginAdmin)
                                        createGroup();
                                        return true;
            case R.id.menu_logout: mAuth.signOut();
                                    startActivity(new Intent(getContext(),LoginActivity.class));
            case R.id.menu_profile_info: startActivity(new Intent(getContext(),Profile.class));
            default:return false;
        }
    }

    // called from a fn on pressing 3 dots
    private void createGroup() {
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setTitle("Enter Group Name");
        final EditText groupnamefield = new EditText(getContext());
        groupnamefield.setHint("e.g. DeCoders Official");
        builder.setView(groupnamefield);
        dbref= FirebaseDatabase.getInstance().getReference();
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupname= groupnamefield.getText().toString();
                CreateNewGroup(groupname);
            }
        });
        builder.show();
    }

    private void CreateNewGroup(final String groupname) {

        dbref.child("Groups").child(groupname).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getContext(), groupname+ "is created!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), "Search Groups!!!", Toast.LENGTH_SHORT).show();
    }


}
