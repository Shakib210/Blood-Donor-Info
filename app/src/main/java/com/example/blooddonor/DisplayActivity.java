package com.example.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private ListView listView;
    DatabaseReference databaseReference;
    private List<DataUpload> list;
    private MyCustomAdapter adapter;
    private Spinner spinner;
    String[] keys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        this.setTitle("Donor list");
        spinner=findViewById(R.id.spin);
        String group1[]=getResources().getStringArray(R.array.group1);
        List<String> groups = new ArrayList<>();
        groups.add("A+");
        groups.add("A-");
        groups.add("B+");
        groups.add("B-");
        groups.add("AB+");
        groups.add("AB-");
        groups.add("O+");
        groups.add("O-");

      ArrayAdapter  mAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,groups);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(mAdapter);
        String grp=spinner.getSelectedItem().toString();
        listView=findViewById(R.id.list);
        databaseReference= FirebaseDatabase.getInstance().getReference(grp);
        list=new ArrayList<>();
        adapter=new MyCustomAdapter(DisplayActivity.this,list);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
                SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("value", selectedItemText);
                editor.apply();
                databaseReference= FirebaseDatabase.getInstance().getReference(selectedItemText);
                onStart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onStart() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren() ){
                    DataUpload upload= dataSnapshot1.getValue(DataUpload.class);
                    upload.setuId(dataSnapshot1.getKey());
                    list.add(upload);
                }

                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
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
