package com.example.collegeevent.Lokesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeevent.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;

public class MainActivityLokesh extends AppCompatActivity {
    Button btn;
    EditText idedittext,pasSedittext;
    TextView signtext;
    private FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lokesh);
        idedittext=findViewById(R.id.emailid);
        pasSedittext=findViewById(R.id.password);
        signtext= findViewById(R.id.signintext);
        btn= findViewById(R.id.btnLogin);
        mAuth= FirebaseAuth.getInstance();
        signtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityLokesh.this,SignUP.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uName = idedittext.getText().toString();
                final String pass = pasSedittext.getText().toString();
                if(uName.equals(""))
                {
                    Toast.makeText(MainActivityLokesh.this, "Email ID space is empty!!!", Toast.LENGTH_SHORT).show();
                    idedittext.requestFocus();
                }
                else if(pass.equals(""))
                {
                    Toast.makeText(MainActivityLokesh.this, "Password space is empty!!!", Toast.LENGTH_SHORT).show();
                    pasSedittext.requestFocus();
                }
                else if(uName.equals("reachcclub@gmail.com") && pass.equals("re@chTCCO2020:D"))
                {
                    startActivity(new Intent(getApplicationContext(),superAdmin.class));
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(uName,pass)
                            .addOnCompleteListener(MainActivityLokesh.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        startActivity(new Intent(getApplicationContext(),Groups.class));
                                        //updateUI(user);
                                    } else {

                                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }




            }
        });
    }
}
