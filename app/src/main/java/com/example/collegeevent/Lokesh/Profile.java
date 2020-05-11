package com.example.collegeevent.Lokesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.collegeevent.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    TextView profileName;
    TextView profileUSN;
    TextView profileEmail;
    TextView profileSemester;
    TextView profileSection;
    TextView profilePhoneNumber;
    TextView profileUserType;
    TextView profilePassword;
    TextView profileBranch;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference mRef;
    MemberStudent memberStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileBranch       = findViewById(R.id.t_branch);
        profileName         = findViewById(R.id.t_name);
        profileUSN          = findViewById(R.id.t_usn);
        profileEmail        = findViewById(R.id.t_email);
        profileSection      = findViewById(R.id.t_section);
        profileSemester     = findViewById(R.id.t_semester);
        profilePhoneNumber  = findViewById(R.id.t_phone_number);
        profileUserType     = findViewById(R.id.t_user_type);
        profilePassword     = findViewById(R.id.t_password);

        memberStudent = new MemberStudent();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();
        if(currentUser != null)
        {
            // todo : replace login student in sign up part. The user info must be stored outside only
            mRef.child("LoginStudent").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot x: dataSnapshot.getChildren())
                    {
                        memberStudent = x.getValue(MemberStudent.class);
                        profileName.setText(memberStudent.getStudent_name());
                        profileUSN.setText(memberStudent.getStudent_USN());
                        profileBranch.setText(memberStudent.getStudent_branch());
                        profileSemester.setText(memberStudent.getStudent_semester());
                        profileSection.setText(memberStudent.getStudent_section());
                        profileEmail.setText(memberStudent.getStudent_email());
                        profileUserType.setText(memberStudent.getUser_type());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser == null)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
