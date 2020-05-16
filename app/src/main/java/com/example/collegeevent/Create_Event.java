package com.example.collegeevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeevent.Lokesh.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Create_Event extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mCollegeReference;
    DatabaseReference mGroupReference;
    EditText mTitle;
    EditText mDescription;
    TextView mDate;
    EditText mStartTime;
    EditText mEndTime;
    EditText mVenue;
    Button mCreateEvent;
    Button mDatePicker;

    String title ;
    String description ;
    String startTime ;
    String endTime ;
    String venue ;
    String date = "8022020";  // todo: replace it with the content of the date picker
    int counter = 3;

    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        if(mAuth.getUid() == null)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mCollegeReference = firebaseDatabase.getReference("LoginStudent").child(mAuth.getUid()).child("CollegeEvents");
        mGroupReference = firebaseDatabase.getReference("LoginStudent").child(mAuth.getUid()).child("GroupEvents");

        mTitle = (EditText)findViewById(R.id.titleEditText);
        mDescription = (EditText)findViewById(R.id.descriptionEditText);
        mDate = (TextView) findViewById(R.id.dateTextView);
        mDatePicker = (Button) findViewById(R.id.datePickerBtn);
        mStartTime = (EditText)findViewById(R.id.startTimeEditText);
        mEndTime = (EditText)findViewById(R.id.endTimeEditText);
        mVenue = (EditText)findViewById(R.id.eventVenueEditText);
        mCreateEvent = (Button) findViewById(R.id.btnCreateEvent);

        mCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = mTitle.getText().toString().trim();
                description = mDescription.getText().toString().trim();
                startTime = mStartTime.getText().toString().trim();
                endTime = mEndTime.getText().toString().trim();
                venue = mVenue.getText().toString().trim();
                if(title.equals("") || description.equals("") ||startTime.equals("") || endTime.equals("") || venue.equals("") )
                    Toast.makeText(Create_Event.this,"ALL FIELDS ARE REQUIRED",Toast.LENGTH_LONG);
                else {
                    counter++;
                    mCollegeReference.child(date+counter).child("title").setValue(title); // here replace date with (date and time) as a key combination for sorting automatically
                    mCollegeReference.child(date+counter).child("description").setValue(description);
                    mCollegeReference.child(date+counter).child("eventTime").setValue(startTime);
                    mCollegeReference.child(date+counter).child("reminderSet").setValue("1");  // for college event we have to set the event in the mobile's calendar
                    mCollegeReference.child(date+counter).child("venue").setValue(venue);
                    mCollegeReference.child(date+counter).child("eventDate").setValue(date);

                    Toast.makeText(Create_Event.this,"EVENT IS NOW LIVE!",Toast.LENGTH_LONG);
                    finish();
                }
            }
        });

    }
}
