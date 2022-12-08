package com.example.mad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Buses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses);

        getSupportFragmentManager().beginTransaction().add(R.id.B_FRAME, new Select_Buses()).commit();
    }
}