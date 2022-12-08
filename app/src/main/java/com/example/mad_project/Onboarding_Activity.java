package com.example.mad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Onboarding_Activity extends AppCompatActivity {

    Button getstarted;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        count = 0;
        getstarted = findViewById(R.id.Get_Started);

        getSupportFragmentManager().beginTransaction().add(R.id.OB_FRAME, new OB_1()).commit();

        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 0)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.OB_FRAME, new OB_2()).commit();
                    count++;
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), Create_Login_Activity.class);
                    startActivity(intent);
                }
            }
        });
    }
}