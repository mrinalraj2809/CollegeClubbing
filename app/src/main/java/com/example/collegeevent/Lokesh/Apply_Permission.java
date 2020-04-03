package com.example.collegeevent.Lokesh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeevent.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Apply_Permission extends AppCompatActivity {
    EditText                txtgrpnm,txtgrpdesc,txtgrptype,txtpersonname;
    TextView                txtfilename;
    Button                  btnupload,btnapply;
    ProgressDialog          progressDialog;
    Uri                     pdfUri;
    //int                   maxid=0;
    Permission              permission;
    MemberTeacher           memberTeacher;
    //MemberAdmin           memberAdmin;
    FirebaseStorage         firebaseStorage;
    FirebaseDatabase        firebaseDatabase;
    DatabaseReference       dbref;
    String                  teacherName, adminName;
    String                  userType;
    String                  userID;
    FirebaseAuth            mAuth;
    public Apply_Permission()
    {
        SignUP signUP = new SignUP();
        memberTeacher = signUP.memberTeacher;
    }
//    Apply_Permission(MemberAdmin memberAdmin)
//    {
//        this.memberAdmin = memberAdmin;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply__permission);

        mAuth = FirebaseAuth.getInstance();

        txtgrpdesc              = findViewById(R.id.groupdesc);
        txtpersonname           = findViewById(R.id.personname);
        txtfilename             = findViewById(R.id.permissiontext);
        txtgrpnm                = findViewById(R.id.groupname);
        txtgrptype              = findViewById(R.id.typeOfgroup);
        btnapply                = findViewById(R.id.apply);
        btnupload               = findViewById(R.id.uploadpdf);
        permission              = new Permission();
        firebaseStorage         = FirebaseStorage.getInstance();
        firebaseDatabase        = FirebaseDatabase.getInstance();

        // get login type from the intent
        Intent intent = getIntent();
        userType                    = intent.getStringExtra("USER_TYPE");
        userID                      = intent.getStringExtra("USER_ID");
        if (userType.equals("LoginTeacher")) {
            //teacherName = intent.getStringExtra("TEACHER_NAME");
            //txtpersonname.setText(String.valueOf(FirebaseDatabase.getInstance().getReference().child("LoginTeacher").child(mAuth.getUid()).child("teacher_name")));
            FirebaseDatabase.getInstance().getReference().child("LoginTeacher").child(mAuth.getUid()).child("teacher_name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    teacherName     = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Apply_Permission.this, "Name failed to fetch", Toast.LENGTH_SHORT).show();
                }
            });
            txtpersonname.setText(teacherName);
        }
        else {
            //adminName = intent.getStringExtra("ADMIN_NAME");
            FirebaseDatabase.getInstance().getReference().child("LoginAdmin").child(mAuth.getUid()).child("admin_name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    adminName       = dataSnapshot.getValue(String.class);    // We have to specify the class, similar to messageModel and it has to be same as firebase value.
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            txtpersonname.setText(adminName);
        }

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf();
                }
                else
                {
                    ActivityCompat.requestPermissions(Apply_Permission.this,new  String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });

        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfUri!=null)
                {
                    uploadfile(pdfUri);
                    save_details();
                }
                else
                {
                    Toast.makeText(Apply_Permission.this, "Select a file!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void save_details() {
        if (userType.equals("LoginTeacher"))
            dbref               = FirebaseDatabase.getInstance().getReference().child("RequestChannelCreationTeacherLogin");
        else
            dbref               = FirebaseDatabase.getInstance().getReference().child("RequestChannelCreationAdminLogin");

    }

    private void uploadfile(Uri pdfUri) {
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setProgress(0);
        progressDialog.show();
        final String fileName= System.currentTimeMillis()+"";
        StorageReference storageReference= firebaseStorage.getReference();
        storageReference.child("UploadedPDF").child(fileName).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final String url                    = taskSnapshot.getStorage().getDownloadUrl().toString();
                final String groupname              = txtgrpnm.getText().toString();
                final String person                 = txtpersonname.getText().toString();
                final String groupdesc              = txtgrpdesc.getText().toString();
                final String grouptype              = txtgrptype.getText().toString();
                final DatabaseReference reference   = dbref;          // dbref is either requestTeacher or requestAdminLogin
                permission.setName(person);
                permission.setGroup(groupname);
                permission.setDesc(groupdesc);
                permission.setType(grouptype);
                permission.setPdf(url);
                permission.setChannelApproved("0");
                permission.setUserID(mAuth.getUid());               // UserId can be used to fetch the account of the teacher or admin
                String timeInMillis = String.valueOf(System.currentTimeMillis());
                reference.child(timeInMillis).setValue(memberTeacher);
                if (userType.equalsIgnoreCase("LoginTeacher"))
                {
                    //reference.child(timeInMillis).setValue(memberTeacher);
                }

                Toast.makeText(Apply_Permission.this, "File Successfully Uploaded", Toast.LENGTH_SHORT).show();
                txtgrpnm.setText("");
                txtfilename.setText("");
                txtgrpdesc.setText("");
                txtgrptype.setText("");

//                reference.child(String.valueOf(System.currentTimeMillis())).child("admin"+String.valueOf(maxid+1)).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful())
//                        {
//                                                    }
//                        else
//                        {
//
//                        }
//
//                    }
//                });
//
            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Apply_Permission.this, "File not uploaded", Toast.LENGTH_SHORT).show();
//
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                int currentProgress=   (int)(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                progressDialog.setProgress(currentProgress);
//            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectPdf();
        }
        else
        {
            Toast.makeText(this, "Please provide permission!!!", Toast.LENGTH_SHORT).show();
        }

        //        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void selectPdf() {
        Intent i                        = new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==86 && resultCode== RESULT_OK && data!=null)
        {
            pdfUri                      = data.getData();
            txtfilename.setText(""+data.getData().getLastPathSegment());
            Toast.makeText(this, "File Selected Successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please Select a file!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
