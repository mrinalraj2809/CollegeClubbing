package com.example.collegeevent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.example.collegeevent.Lokesh.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChannelGroup extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRefChats;
    private DatabaseReference mRefAdmin;
    private ArrayList<Message_Model> message_list;
    private Toolbar mToolbar;
    private ImageView sendButton;
    private EditText textMessage;
    private String senderAdminName;
    private ImageView welcomeMessage;
    private LinearLayout messageLinearLayout;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth== null)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_group);
        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();
        welcomeMessage = findViewById(R.id.welcome_hi_message);
        mToolbar = (Toolbar) findViewById(R.id.group_page_toolbar);
        messageLinearLayout = findViewById(R.id.message_layout);
        //setSupportActionBar(mToolbar);
        setSupportActionBar(mToolbar);

        String groupName = getIntent().getExtras().get("GROUPNAME").toString();
        getSupportActionBar().setTitle(groupName);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mRefAdmin = firebaseDatabase.getReference(groupName).child("admin");

        mRefChats = firebaseDatabase.getReference(groupName).child("Chats");
        mRefAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot x: dataSnapshot.getChildren())
                {
//                    Toast.makeText(ChannelGroup.this, x.child("user_id").getValue().toString(), Toast.LENGTH_SHORT).show();
                    if (x.child("user_id").getValue().toString().equals(mAuth.getUid()))
                    {
                        messageLinearLayout.setVisibility(View.VISIBLE);
                        senderAdminName = x.child("name").getValue().toString();
                    }
                    else
                        messageLinearLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = findViewById(R.id.chat_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();
        message_list = new ArrayList<Message_Model>();
        mRefChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Message_Model value;
                message_list.clear();
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    value = x.getValue(Message_Model.class);
                    message_list.add(value);
                    // Toast.makeText(Main2Activity.this, "item: "+list.get(i).title, Toast.LENGTH_LONG).show();
                }
                recyclerView.clearOnScrollListeners();
                recyclerView.clearOnChildAttachStateChangeListeners();

                //This sets the all data from the firebase onto the adapter
                MessageAdapter messageAdapter = new MessageAdapter(message_list);
                RecyclerView.LayoutManager recyce = new GridLayoutManager(recyclerView.getContext(), 1);
                recyclerView.setLayoutManager(recyce);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Sending the message
        sendButton = findViewById(R.id.send_button);
        textMessage = findViewById(R.id.editTextMessage);

        final Message_Model message_model = new Message_Model();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textMessage.getText().toString().trim();
                textMessage.setText("");
                message_model.setMessage_content(message);
                message_model.setSenders_name(senderAdminName);
                message_model.setSenders_unique_id(mAuth.getUid());
                message_model.setSent_date_time(""+System.currentTimeMillis());
                mRefChats.child(""+System.currentTimeMillis()).setValue(message_model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ChannelGroup.this, "Message Sent", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChannelGroup.this, "Message Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}


