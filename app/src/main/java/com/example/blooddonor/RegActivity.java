package com.example.blooddonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegActivity extends AppCompatActivity {

    private Button reg,dis;
    private EditText pass1, update, phone,name,address,pin;
    private RadioGroup gender;
    private RadioButton mf;
    private TextView log;
    private Spinner group;
    String GroupNo[];
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        FirebaseDatabase.getInstance();
        this.setTitle("Add donor");
        reg = findViewById(R.id.sign_up_button);
        address = findViewById(R.id.adr);
        update = findViewById(R.id.update);
        phone = findViewById(R.id.phn);
        name=findViewById(R.id.name);
        gender=findViewById(R.id.gender);
        group=findViewById(R.id.group);
        dis=findViewById(R.id.display);
        pin=findViewById(R.id.pin);
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),DisplayActivity.class);
                startActivity(intent);
            }
        });
        List<String> groups = new ArrayList<>();
        groups.add("A+");
        groups.add("A-");
        groups.add("B+");
        groups.add("B-");
        groups.add("AB+");
        groups.add("AB-");
        groups.add("O+");
        groups.add("O-");

        ArrayAdapter mAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,groups);
        mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        group.setAdapter(mAdapter);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no=pin.getText().toString().trim();
                final String phn = phone.getText().toString().trim();
                String addr = address.getText().toString().trim();
                String up = update.getText().toString().trim();
                final String fname=name.getText().toString();
                String grp=group.getSelectedItem().toString();
                int select= gender.getCheckedRadioButtonId();
                mf=findViewById(select);
                final String radio=mf.getText().toString();
                if (!no.equals("6969")){
                    Toast.makeText(RegActivity.this, "Pin is Not correct", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phn.isEmpty() && addr.isEmpty() && up.isEmpty() && fname.isEmpty()){
                    return;
                }else {
                    String Final=grp;
                    databaseReference= FirebaseDatabase.getInstance().getReference(Final);
                    String key=databaseReference.push().getKey();
                    DataUpload Upload=new DataUpload(fname,up,grp,addr,phn,radio);
                    databaseReference.child(key).setValue(Upload);
                    name.setText("");
                    update.setText("");
                    phone.setText("");
                    address.setText("");
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}