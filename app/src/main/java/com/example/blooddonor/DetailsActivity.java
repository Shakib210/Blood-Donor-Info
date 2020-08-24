package com.example.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    private TextView name,group,phone,date,address,gender,date2;
    private Button edit,call,submit;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Donor info");
        setContentView(R.layout.activity_details);
        name=findViewById(R.id.name);
        group=findViewById(R.id.bld);
        phone=findViewById(R.id.phone);
        date=findViewById(R.id.date);
        address=findViewById(R.id.address);
        gender=findViewById(R.id.gender);
        call=findViewById(R.id.call);
        edit=findViewById(R.id.edit);
        date2=findViewById(R.id.date2);
        submit=findViewById(R.id.submit);
        linearLayout=findViewById(R.id.lay);
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String key1 = sharedPreferences.getString("value","");
        final String key2=getIntent().getStringExtra("key");

        databaseReference= FirebaseDatabase.getInstance().getReference(key1).child(key2);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataUpload upload=snapshot.getValue(DataUpload.class);
                name.setText(upload.getName());
                group.setText(upload.getGroup());
                phone.setText(upload.getPhone());
                date.setText(upload.getDate());
                address.setText(upload.getAddress());
                gender.setText(upload.getGender());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dial = "tel:" + phone.getText().toString();
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.VISIBLE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d=date2.getText().toString();
                if(d.isEmpty()) return;
                HashMap hashMap=new HashMap();
                hashMap.put("date",d);
                databaseReference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(DetailsActivity.this, "Added", Toast.LENGTH_SHORT).show();
                        linearLayout.setVisibility(View.GONE);
                    }
                });

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sample, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.log:
                newWay();
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newWay() {
        Intent intent=new Intent(getApplicationContext(),RegActivity.class);
        startActivity(intent);
    }
}