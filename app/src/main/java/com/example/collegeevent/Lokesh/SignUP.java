package com.example.collegeevent.Lokesh;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
    TextInputEditText               txtnmStudent,txtusnStudent,txtpnoStudent,txtemailStudent,txtpwdStudent,txtcnfpwdStudent;
    TextInputEditText               signinnameTeacher,signinUniqueIdTeacher,signinpnoTeacher,signInSpecialisationTeacher,signinbranchTeacher,signinDesignationTeacher,signinemailTeacher,signinpwdTeacher,signinpwdcnfrmTeacher;

    ProgressDialog                  loadingBar;
    AutoCompleteTextView            acTxtsemStudent,acTxtdepStudent,acTxtSectionStudent;
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
    String                          departmentArr[] = {"CSE","ISE","MECH","ECE","EEE","TLE","BIO","CHEM","ARCHI"};
    String                          sectionArr[] = {"A","B","C","D","E","F","G","H","I","J"};
    String                          semesterArr[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
//    MemberAdmin memberAdmin;
//    MemberSuperAdmin superAdmin
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loadingBar                  = new ProgressDialog(this);
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
                ArrayAdapter<String> adapterDepartment = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, departmentArr);
                ArrayAdapter<String> adapterSemester = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, semesterArr);
                ArrayAdapter<String> adapterSection = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, sectionArr);
                //Find TextView control

                txtnmStudent            = findViewById(R.id.signinnameStudent);
                txtusnStudent           = findViewById(R.id.signinusnStudent);
                txtpnoStudent           = findViewById(R.id.signinpnoStudent);
                acTxtsemStudent         = (AutoCompleteTextView)findViewById(R.id.signinsemStudent);
                acTxtdepStudent         = (AutoCompleteTextView)findViewById(R.id.signinbranchStudent);
                acTxtSectionStudent     = (AutoCompleteTextView)findViewById(R.id.signinSectionStudent);
                txtemailStudent         = findViewById(R.id.signinemailStudent);
                txtpwdStudent           = findViewById(R.id.signinpwdStudent);
                txtcnfpwdStudent        = findViewById(R.id.signinpwdcnfrmStudent);
                btnRegisterStudent      = findViewById(R.id.signinbtnStudent);
                //Set the number of characters the user must type before the drop down list is shown
                acTxtSectionStudent.setThreshold(1);
                acTxtdepStudent.setThreshold(1);
                acTxtsemStudent.setThreshold(1);
                //Set the adapter
                acTxtdepStudent.setAdapter(adapterDepartment);
                acTxtsemStudent.setAdapter(adapterSemester);
                acTxtSectionStudent.setAdapter(adapterSection);
                btnRegisterStudent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String name       = txtnmStudent.getText().toString();
                        final String usn        = txtusnStudent.getText().toString();
                        final String pno        = txtpnoStudent.getText().toString();
                        final String sem        = acTxtsemStudent.getText().toString();
                        final String branch     = acTxtdepStudent.getText().toString();
                        final String section    = acTxtSectionStudent.getText().toString();
                        final String email      = txtemailStudent.getText().toString();
                        final String pwd        = txtpwdStudent.getText().toString();
                        final String cnfpwd     = txtcnfpwdStudent.getText().toString();
                        int Deptflag=0;
                        int Semflag=0;
                        int Sectionflag=0;
                        for(String s:departmentArr)
                        {
                            if(s.equals(branch)){ Deptflag =1; break;}
                        }
                        if(Deptflag == 0){
                            Toast.makeText(SignUP.this, "Choose Department from the Option!!!", Toast.LENGTH_SHORT).show();
                        }
                        for(String s:sectionArr)
                        {
                            if(s.equals(section)){Sectionflag =1; break;}
                        }
                        if(Sectionflag == 0){
                            Toast.makeText(SignUP.this, "Choose Section from the Option!!!", Toast.LENGTH_SHORT).show();
                        }
                        for(String s:semesterArr)
                        {
                            if(s.equals(sem)){Semflag =1; break;}
                        }
                        if(Semflag == 0){
                            Toast.makeText(SignUP.this, "Choose Semester from the Option!!!", Toast.LENGTH_SHORT).show();
                        }

                        if(!name.isEmpty() && !usn.isEmpty() && !pno.isEmpty() && Sectionflag ==1 && Semflag == 1 && Deptflag == 1 &&
                                !sem.isEmpty() && !branch.isEmpty() &&
                                !email.isEmpty() && !pwd.isEmpty() && !cnfpwd.isEmpty() && pwd.equals(cnfpwd)) {
                            if (cnfpwd.equals(pwd)) {
                                loadingBar.setTitle("Creating new Account");
                                loadingBar.setMessage("Please wait, while we are creating new account for you...");
                                loadingBar.setCanceledOnTouchOutside(true);
                                loadingBar.show();
                                mAuth.createUserWithEmailAndPassword(email, pwd)
                                        .addOnCompleteListener(SignUP.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    memberStudent.setStudent_name(name);
                                                    memberStudent.setStudent_USN(usn);
                                                    memberStudent.setStudent_phone_number(pno);
                                                    memberStudent.setStudent_semester(sem);
                                                    memberStudent.setStudent_section(section);
                                                    memberStudent.setStudent_branch(branch);
                                                    memberStudent.setStudent_email(email);
                                                    memberStudent.setUser_type("LoginStudent");// login type is student
                                                    //we encrypt and store password
                                                    memberStudent.setStudent_password(""+pwd);
                                                    memberStudent.setStudent_user_Id(mAuth.getUid());
                                                    long time = System.currentTimeMillis();
                                                    long dept = time +1;
                                                    long deptSem = time +2;
                                                    long deptSemSec = time +3;
                                                    // fills all the colleges groups into the users' node while signup.
                                                    // todo : apply check condition on getting group key.
                                                    dbref.child("LoginStudent").child(mAuth.getUid()).child("profile_info").setValue(memberStudent);
                                                    dbref.child("LoginStudent").child(mAuth.getUid()).child("CollegeGroups").child(""+dept).child("key").setValue(String.valueOf(dept));
                                                    dbref.child("LoginStudent").child(mAuth.getUid()).child("CollegeGroups").child(""+dept).child("name").setValue(memberStudent.getStudent_branch());
                                                    dbref.child("LoginStudent").child(mAuth.getUid()).child("CollegeGroups").child(""+deptSem).child("key").setValue(""+deptSem);
                                                    dbref.child("LoginStudent").child(mAuth.getUid()).child("CollegeGroups").child(""+deptSem).child("name").setValue(memberStudent.getStudent_branch()+memberStudent.getStudent_semester());
                                                    dbref.child("LoginStudent").child(mAuth.getUid()).child("CollegeGroups").child(""+deptSemSec).child("key").setValue(""+deptSemSec);
                                                    dbref.child("LoginStudent").child(mAuth.getUid()).child("CollegeGroups").child(""+deptSemSec).child("name").setValue(memberStudent.getStudent_branch()+memberStudent.getStudent_semester()+memberStudent.getStudent_section());

                                                    //Inserting the data into Department, Year, Section
                                                    //Department Node will help in sending event department wise
                                                    dbref.child(branch).child("Info").child("GroupKey").setValue(""+dept);
                                                    dbref.child(branch).child("Users").child(mAuth.getUid()).child("name").setValue(memberStudent.getStudent_name());
                                                    dbref.child(branch).child("Users").child(mAuth.getUid()).child("usn").setValue(memberStudent.getStudent_USN());
                                                    //Semester Node will help in sending event semester wise
                                                    dbref.child(branch+memberStudent.getStudent_semester()).child("Info").child("GroupKey").setValue(""+deptSem);
                                                    dbref.child(branch+memberStudent.getStudent_semester()).child("Users").child(mAuth.getUid()).child("name").setValue(memberStudent.getStudent_name());
                                                    dbref.child(branch+memberStudent.getStudent_semester()).child("Users").child(mAuth.getUid()).child("usn").setValue(memberStudent.getStudent_USN());
                                                    // Section Node will help in sending event section wise
                                                    dbref.child(branch+memberStudent.getStudent_semester()+memberStudent.getStudent_section()).child("Info").child("GroupKey").setValue(""+deptSemSec);
                                                    dbref.child(branch+memberStudent.getStudent_semester()+memberStudent.getStudent_section()).child("Users").child(mAuth.getUid()).child("name").setValue(memberStudent.getStudent_name());
                                                    dbref.child(branch+memberStudent.getStudent_semester()+memberStudent.getStudent_section()).child("Users").child(mAuth.getUid()).child("usn").setValue(memberStudent.getStudent_USN());


                                                    loadingBar.dismiss();
                                                    Toast.makeText(SignUP.this, "Data Inserted Successfully!!!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                } else {
                                                    loadingBar.dismiss();
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
                                loadingBar.setTitle("Creating new Account");
                                loadingBar.setMessage("Please wait, while we are creating new account for you...");
                                loadingBar.setCanceledOnTouchOutside(true);
                                loadingBar.show();
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
                                                    dbref.child("LoginTeacher").child(mAuth.getUid()).child("profile_info").setValue(memberTeacher);
                                                    Toast.makeText(SignUP.this, "Registered Successfully!!!\nComplete the Permission Letter", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(getApplicationContext(), Apply_Permission.class);
                                                    intent.putExtra("USER_TYPE","LoginTeacher");
                                                    intent.putExtra("TEACHER_NAME",nameTeacher);
                                                    intent.putExtra("USER_ID",mAuth.getUid());
                                                    loadingBar.dismiss();
                                                    startActivity(intent);
                                                } else {
                                                    loadingBar.dismiss();
                                                    Toast.makeText(SignUP.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                                }

                                            }
                                        });
                            } else {
                                loadingBar.dismiss();
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
//                                                    dbref.child("LoginAdmin").child(mAuth.getUid()).setValue(memberTeacher);
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
