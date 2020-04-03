package com.example.collegeevent.Lokesh;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.*;

import com.example.collegeevent.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUP extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TextInputEditText               txtnmStudent,txtusnStudent,txtpnoStudent,txtsemStudent,txtdepStudent,txtemailStudent,txtpwdStudent,txtcnfpwdStudent;
    TextInputEditText               signinnameTeacher,signinUniqueIdTeacher,signinpnoTeacher,signInSpecialisationTeacher,signinbranchTeacher,signinDesignationTeacher,signinemailTeacher,signinpwdTeacher,signinpwdcnfrmTeacher;
    Button                          requestPermissionTeacher;
    Button                          requestPermissionAdmin;
    Button                          btnRegisterStudent;
    ViewStub                        stubStudent;
    ViewStub                        stubTeacher;
    ViewStub                        stubAdmin;
    ViewStub                        stubSuperAdmin;
    private FirebaseAuth            mAuth;
    DatabaseReference               dbref;
    //long maxid=111;
    Spinner spinnerUserType;
    MemberStudent                   memberStudent;
    MemberTeacher                   memberTeacher;
//    MemberAdmin memberAdmin;
//    MemberSuperAdmin superAdmin
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        spinnerUserType             = findViewById(R.id.spinnerUserType);
        stubStudent                 = (ViewStub) findViewById(R.id.layout_stub_student);
        stubTeacher                 = (ViewStub) findViewById(R.id.layout_stub_teacher);
        stubAdmin                   = (ViewStub) findViewById(R.id.layout_stub_admin);
        //stubSuperAdmin = (ViewStub) findViewById(R.id.layout_stub_super_admin);

        mAuth                       = FirebaseAuth.getInstance();
        dbref                       = FirebaseDatabase.getInstance().getReference();
        memberStudent               = new MemberStudent();
        memberTeacher               = new MemberTeacher();
//        To be deleted later
//        dbref= FirebaseDatabase.getInstance().getReference().child("Student");
//        dbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists())
//                {
//                    maxid= dataSnapshot.getChildrenCount();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.user_type,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(arrayAdapter);
        spinnerUserType.setOnItemSelectedListener(this);

    }
    public void studentRegister(View view)
    {

    }
    @Override
    public void onItemSelected(AdapterView<?> parent,View view, int position, long id) {
        String userType             = parent.getItemAtPosition(position).toString();
        if(position == 0) {
            stubStudent.setVisibility(View.GONE);// Safe way to use is VISIBLE and GONE.
            stubTeacher.setVisibility(View.GONE);
            stubAdmin.setVisibility(View.GONE);
            //stubSuperAdmin.setVisibility(View.GONE);
        }
        else if (position == 1) {
            stubStudent.setVisibility(View.GONE);// Safe way to use is VISIBLE and GONE.
            stubTeacher.setVisibility(View.GONE);
            stubAdmin.setVisibility(View.GONE);
//            stubSuperAdmin.setVisibility(View.GONE);
            stubStudent.setLayoutResource(R.layout.student_sign_up_form);
            stubStudent.setVisibility(View.VISIBLE);// Or else call using inflateId after inflate is called once.
            signInType(position);

        }
        else if (position == 2){
            stubStudent.setVisibility(View.GONE);// Safe way to use is VISIBLE and GONE.
            stubTeacher.setVisibility(View.GONE);
            stubAdmin.setVisibility(View.GONE);
            //stubSuperAdmin.setVisibility(View.GONE);
            stubTeacher.setLayoutResource(R.layout.teacher_sign_up_form);stubTeacher.setVisibility(View.VISIBLE);

            signInType(position);

        }
        else if (position == 3){
            stubStudent.setVisibility(View.GONE);// Safe way to use is VISIBLE and GONE.
            stubTeacher.setVisibility(View.GONE);
            stubAdmin.setVisibility(View.GONE);
            //stubSuperAdmin.setVisibility(View.GONE);
            stubAdmin.setLayoutResource(R.layout.admin_sign_up_form);stubAdmin.setVisibility(View.VISIBLE);
            requestPermissionAdmin =  stubAdmin.findViewById(R.id.requestPermissionButtonAdmin);
            signInType(position);
        }
        else if (position == 4){
            stubStudent.setVisibility(View.GONE);// Safe way to use is VISIBLE and GONE.
            stubTeacher.setVisibility(View.GONE);
            stubAdmin.setVisibility(View.GONE);
            //stubSuperAdmin.setVisibility(View.GONE);
            stubStudent.setLayoutResource(R.layout.student_sign_up_form);stubStudent.setVisibility(View.VISIBLE);
            signInType(position);
        }

    }
    void signInType(int position)
    {
        switch (position)
        {
            case 1:// Student
                txtnmStudent            = findViewById(R.id.signinnameStudent);
                txtusnStudent           = findViewById(R.id.signinusnStudent);
                txtpnoStudent           = findViewById(R.id.signinpnoStudent);
                txtsemStudent           = findViewById(R.id.signinsemStudent);
                txtdepStudent           = findViewById(R.id.signinbranchStudent);
                txtemailStudent         = findViewById(R.id.signinemailStudent);
                txtpwdStudent           = findViewById(R.id.signinpwdStudent);
                txtcnfpwdStudent        = findViewById(R.id.signinpwdcnfrmStudent);
                btnRegisterStudent      = findViewById(R.id.signinbtnStudent);
                btnRegisterStudent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String name       =txtnmStudent.getText().toString();
                        final String usn        = txtusnStudent.getText().toString();
                        final String pno        = txtpnoStudent.getText().toString();
                        final String sem        = txtsemStudent.getText().toString();
                        final String branch     = txtdepStudent.getText().toString();
                        final String email      = txtemailStudent.getText().toString();
                        final String pwd        = txtpwdStudent.getText().toString();
                        final String cnfpwd     = txtcnfpwdStudent.getText().toString();
                        if(!name.isEmpty() && !usn.isEmpty() && !pno.isEmpty() &&
                                !sem.isEmpty() && !branch.isEmpty() &&
                                !email.isEmpty() && !pwd.isEmpty() && !cnfpwd.isEmpty() && pwd.equals(cnfpwd)) {
                            if (cnfpwd.equals(pwd)) {
                                mAuth.createUserWithEmailAndPassword(email, pwd)
                                        .addOnCompleteListener(SignUP.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    memberStudent.setStudent_name(name);
                                                    memberStudent.setStudent_USN(usn);
                                                    memberStudent.setStudent_phone_number(pno);
                                                    memberStudent.setStudent_semester(sem);
                                                    memberStudent.setStudent_branch(branch);
                                                    memberStudent.setStudent_email(email);
                                                    memberStudent.setUser_type("LoginStudent");// login type is student
                                                    //we encrypt and store password
                                                    memberStudent.setStudent_password("1"+pwd+"1");
                                                    memberStudent.setStudent_user_Id(mAuth.getUid());
                                                    dbref.child("LoginStudent").child(mAuth.getUid()).setValue(memberStudent);
                                                    Toast.makeText(SignUP.this, "Data Inserted Successfully!!!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), MainActivityLokesh.class));
                                                } else {

                                                    Toast.makeText(SignUP.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                                }

                                            }
                                        });
                            } else {
                                Toast.makeText(SignUP.this, "Password Not Confirmed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Snackbar.make(v, "All fields are required.", Snackbar.LENGTH_LONG).show();
                        }

                    }
                });
                break;
            case 2: //Teacher Sign UP
                signinnameTeacher                   = findViewById(R.id.signinnameTeacher);
                signinUniqueIdTeacher               = findViewById(R.id.signinUniqueIdTeacher);
                signinpnoTeacher                    = findViewById(R.id.signinpnoTeacher);
                signInSpecialisationTeacher         = findViewById(R.id.signInSpecialisationTeacher);
                signinbranchTeacher                 = findViewById(R.id.signinbranchTeacher);
                signinDesignationTeacher            = findViewById(R.id.signinDesignationTeacher);
                signinemailTeacher                  = findViewById(R.id.signinemailTeacher);
                signinpwdcnfrmTeacher               = findViewById(R.id.signinpwdcnfrmTeacher);
                signinpwdTeacher                    = findViewById(R.id.signinpwdTeacher);
                requestPermissionTeacher            = findViewById(R.id.requestPermissionButtonTeacher);
                requestPermissionTeacher.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivity(new Intent(SignUP.this,Apply_Permission.class));
                        final String nameTeacher            = signinnameTeacher.getText().toString().trim();
                        final String uniqueIdTeacher        = signinUniqueIdTeacher.getText().toString().trim();
                        final String pnoTeacher             = signinpnoTeacher.getText().toString().trim();
                        final String specialisationTeacher  = signInSpecialisationTeacher.getText().toString().trim();
                        final String branchTeacher          = signinbranchTeacher.getText().toString().trim();
                        final String designationTeacher     = signinDesignationTeacher.getText().toString().trim();
                        final String emailTeacher           = signinemailTeacher.getText().toString().trim();
                        final String pwdTeacher             = signinpwdTeacher.getText().toString().trim();
                        final String cnfpwdTeacher          = signinpwdcnfrmTeacher.getText().toString().trim();
                        if(!nameTeacher.isEmpty() && !uniqueIdTeacher.isEmpty() && !pnoTeacher.isEmpty() &&
                                !specialisationTeacher.isEmpty() && !branchTeacher.isEmpty() &&
                                !emailTeacher.isEmpty() && !pwdTeacher.isEmpty() && !cnfpwdTeacher.isEmpty() && !designationTeacher.isEmpty()) {
                            if (cnfpwdTeacher.equals(pwdTeacher)) {
                                mAuth.createUserWithEmailAndPassword(emailTeacher, pwdTeacher)
                                        .addOnCompleteListener(SignUP.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    memberTeacher.setTeacher_name(nameTeacher);
                                                    memberTeacher.setTeacher_unique_id(uniqueIdTeacher);
                                                    memberTeacher.setTeacher_phone_number(pnoTeacher);
                                                    memberTeacher.setTeacher_specialisation(specialisationTeacher);
                                                    memberTeacher.setTeacher_branch(branchTeacher);
                                                    memberTeacher.setTeacher_designation(designationTeacher);
                                                    memberTeacher.setTeacher_email(emailTeacher);
                                                    memberTeacher.setUser_type("LoginTeacher");// login type is Teacher
                                                    //we encrypt and store password
                                                    memberTeacher.setTeacher_password("1"+pwdTeacher+"1");
                                                    memberTeacher.setTeacher_user_Id(mAuth.getUid());
                                                    memberTeacher.setTeacher_verified("0");
                                                    //Apply_Permission apply_permission = new Apply_Permission(memberTeacher);apply_permission.memberTeacher=memberTeacher;
                                                    dbref.child("LoginTeacher").child(mAuth.getUid()).setValue(memberTeacher);
                                                    Toast.makeText(SignUP.this, "Registered Successfully!!!\nComplete the Permission Letter", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(getApplicationContext(), Apply_Permission.class);
                                                    intent.putExtra("USER_TYPE","LoginTeacher");
                                                    intent.putExtra("TEACHER_NAME",nameTeacher);
                                                    intent.putExtra("USER_ID",mAuth.getUid());
                                                    startActivity(intent);
                                                } else {

                                                    Toast.makeText(SignUP.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                                }

                                            }
                                        });
                            } else {
                                Toast.makeText(SignUP.this, "Password Not Confirmed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Snackbar.make(v, "All fields are required.", Snackbar.LENGTH_LONG).show();
                        }

                    }
                });
                break;
//            case 3: //Admin Sign UP
//                signinnameTeacher = findViewById(R.id.signinnameTeacher);
//                signinUniqueIdTeacher = findViewById(R.id.signinUniqueIdTeacher);
//                signinpnoTeacher = findViewById(R.id.signinpnoTeacher);
//                signInSpecialisationTeacher = findViewById(R.id.signInSpecialisationTeacher);
//                signinbranchTeacher = findViewById(R.id.signinbranchTeacher);
//                signinDesignationTeacher = findViewById(R.id.signinDesignationTeacher);
//                signinemailTeacher = findViewById(R.id.signinemailTeacher);
//                signinpwdcnfrmTeacher = findViewById(R.id.signinpwdcnfrmTeacher);
//                signinpwdTeacher = findViewById(R.id.signinpwdTeacher);
//                requestPermissionTeacher = findViewById(R.id.requestPermissionButtonTeacher);
//                requestPermissionTeacher.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //startActivity(new Intent(SignUP.this,Apply_Permission.class));
//                        final String nameTeacher=signinnameTeacher.getText().toString().trim();
//                        final String uniqueIdTeacher = signinUniqueIdTeacher.getText().toString().trim();
//                        final String pnoTeacher = signinpnoTeacher.getText().toString().trim();
//                        final String specialisationTeacher = signInSpecialisationTeacher.getText().toString().trim();
//                        final String branchTeacher = signinbranchTeacher.getText().toString().trim();
//                        final String designationTeacher = signinDesignationTeacher.getText().toString().trim();
//                        final String emailTeacher = signinemailTeacher.getText().toString().trim();
//                        final String pwdTeacher = signinpwdTeacher.getText().toString().trim();
//                        final String cnfpwdTeacher = signinpwdcnfrmTeacher.getText().toString().trim();
//                        if(!nameTeacher.isEmpty() && !uniqueIdTeacher.isEmpty() && !pnoTeacher.isEmpty() &&
//                                !specialisationTeacher.isEmpty() && !branchTeacher.isEmpty() &&
//                                !emailTeacher.isEmpty() && !pwdTeacher.isEmpty() && !cnfpwdTeacher.isEmpty() && !designationTeacher.isEmpty()) {
//                            if (cnfpwdTeacher.equals(pwdTeacher)) {
//                                mAuth.createUserWithEmailAndPassword(emailTeacher, pwdTeacher)
//                                        .addOnCompleteListener(SignUP.this, new OnCompleteListener<AuthResult>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                                if (task.isSuccessful()) {
//                                                    memberTeacher.setTeacher_name(nameTeacher);
//                                                    memberTeacher.setTeacher_unique_id(uniqueIdTeacher);
//                                                    memberTeacher.setTeacher_phone_number(pnoTeacher);
//                                                    memberTeacher.setTeacher_specialisation(specialisationTeacher);
//                                                    memberTeacher.setTeacher_branch(branchTeacher);
//                                                    memberTeacher.setTeacher_designation(designationTeacher);
//                                                    memberTeacher.setTeacher_email(emailTeacher);
//                                                    memberTeacher.setUser_type("LoginTeacher");// login type is Teacher
//                                                    //we encrypt and store password
//                                                    memberTeacher.setTeacher_password("1"+pwdTeacher+"1");
//                                                    memberTeacher.setTeacher_user_Id(mAuth.getUid());
//                                                    memberTeacher.setTeacher_verified("0");
//                                                    dbref.child("LoginTeacher").child(mAuth.getUid()).setValue(memberTeacher);
//                                                    Toast.makeText(SignUP.this, "Registered Successfully!!!\nComplete the Permission Letter", Toast.LENGTH_LONG).show();
//                                                    Intent intent = new Intent(getApplicationContext(), Apply_Permission.class);
//                                                    intent.putExtra("USER_TYPE","LoginAdmin");
//                                                    intent.putExtra("ADMIN_NAME",nameAdmin);
//                                                    startActivity(intent);
//                                                } else {
//
//                                                    Toast.makeText(SignUP.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//
//                                                }
//
//                                            }
//                                        });
//                            } else {
//                                Toast.makeText(SignUP.this, "Password Not Confirmed", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        else {
//                            Snackbar.make(v, "All fields are required.", Snackbar.LENGTH_LONG).show();
//                        }
//
//                    }
//                });
//                break;
        }

    }
//    ViewStub v =((ViewStub)findViewById(R.id.message_layout)).setOnInflateListener(new ViewStub.OnInflateListener() {
//        @Override
//        public void onInflate(ViewStub stub, View inflated) {
//            Button register = inflated.findViewById(R.id.signinbtn);
//        }
//    });

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
