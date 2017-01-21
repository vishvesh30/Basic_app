package com.example.vishvraj.basic_app;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class showdata extends AppCompatActivity {

    private List<data_info> datalist=new ArrayList<>();
    private RecyclerView recyclerView;
    private dataadapter dataadapter;
    DatabaseHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdata);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview1);
        dataadapter=new dataadapter(datalist);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dataadapter);
        preparedata();

    }
    private void preparedata(){
        mydb=new DatabaseHelper(this);
        Cursor res=mydb.getAllData();
        while (res.moveToNext()) {
            data_info data = new data_info(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),res.getString(7),res.getString(8));
            datalist.add(data);
        }
    }
}
