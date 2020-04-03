package com.example.collegeevent.Lokesh;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.collegeevent.R;

public class superAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.super_admin_groups,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch (id)
        {
            case R.id.pending_groups_list:
                Toast.makeText(this, "Check Pending groups", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),List_Of_Permissions.class));
                break;
        }
        return true;
    }
}
