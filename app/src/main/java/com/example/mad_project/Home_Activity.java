package com.example.mad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home_Activity extends AppCompatActivity {

    private Button home_btn, tickets_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction().add(R.id.H_FRAME, new Home()).commit();

        Button_func();
    }

    private void Button_func()
    {
        home_btn = findViewById(R.id.home_btn);
        tickets_btn = findViewById(R.id.tickets_btn);

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.H_FRAME, new Home()).commit();
            }
        });

        tickets_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.H_FRAME, new Tickets()).commit();
            }
        });
    }
}