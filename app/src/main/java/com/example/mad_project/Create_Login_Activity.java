package com.example.mad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Create_Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_login);

        getSupportFragmentManager().beginTransaction().add(R.id.CL_FRAME, new CreateLogin()).commit();
    }
}