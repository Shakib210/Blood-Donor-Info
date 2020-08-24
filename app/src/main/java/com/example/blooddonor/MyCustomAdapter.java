package com.example.blooddonor;

import android.app.Activity;
import android.content.Intent;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class MyCustomAdapter extends ArrayAdapter<DataUpload> {

    private Activity context;
    private List<DataUpload> list;

    public MyCustomAdapter(Activity context, List<DataUpload> list) {
        super(context, R.layout.sample_layout, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View view= inflater.inflate(R.layout.sample_layout,null,true);

        final DataUpload upload=list.get(position);
        TextView name= view.findViewById(R.id.name);
        TextView time=view.findViewById(R.id.time);
        LinearLayout linearLayout=view.findViewById(R.id.u);
        name.setText("Name: "+upload.getName());
        time.setText("Date: "+upload.getDate());

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=upload.getuId();
                Intent intent=new Intent(context, DetailsActivity.class);
                intent.putExtra("key",id);
                context.startActivity(intent);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=upload.getuId();
                Intent intent=new Intent(context, DetailsActivity.class);
                intent.putExtra("key",id);
                context.startActivity(intent);
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=upload.getuId();
                Intent intent=new Intent(context, DetailsActivity.class);
                intent.putExtra("key",id);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
