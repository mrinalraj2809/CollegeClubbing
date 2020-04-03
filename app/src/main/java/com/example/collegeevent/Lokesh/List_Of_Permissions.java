package com.example.collegeevent.Lokesh;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.collegeevent.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_Of_Permissions extends AppCompatActivity {
DatabaseReference databaseReference;
RecyclerView recyclerView;
PermissionsAdapter adapter;
ArrayList<Permission> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__of__permissions);
        recyclerView= findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList= new ArrayList<Permission>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Admin");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Permission p= dataSnapshot.getValue(Permission.class);
                    arrayList.add(p);

                }
                adapter= new PermissionsAdapter(getApplicationContext(),arrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
