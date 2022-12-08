package com.example.mad_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Admin_Activity extends AppCompatActivity {

    Button view_bus, add_bus, view_ticket, logout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getSupportFragmentManager().beginTransaction().add(R.id.A_FRAME, new View_Buses()).commit();
        mAuth = FirebaseAuth.getInstance();
        id_assigner();
        Buttons_Func();
    }

    private void id_assigner()
    {
        view_bus = findViewById(R.id.view_buses);
        add_bus = findViewById(R.id.add_buses);
        view_ticket = findViewById(R.id.view_tickets);
        logout = findViewById(R.id.logout);;
    }

    private void Buttons_Func()
    {
        view_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.A_FRAME, new View_Buses()).commit();
            }
        });

        add_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.A_FRAME, new Add_Buses()).commit();
            }
        });

        view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.A_FRAME, new Tickets()).commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), Create_Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}