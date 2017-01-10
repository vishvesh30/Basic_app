package com.example.vishvraj.basic_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void next(View v){
        Intent intent=new Intent("com.example.vishvraj.basic_app.Main2Activity");
        startActivity(intent);
    }
}
