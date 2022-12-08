package com.example.mad_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Tickets extends Fragment implements H_Ticket_Adapter.ClickInterface{

    RecyclerView recyclerView;
    ArrayList<H_Ticket_Model> arrayList = new ArrayList<>();
    ArrayList<H_Ticket_Model> arrayList2 = new ArrayList<>();

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    H_Ticket_Adapter adapter;
    int index = 0;
    String user_id;

    public Tickets() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Tickets");
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();

        id_assigner(view);
        get_values();
        setting_recycler_view();

        return view;
    }

    private void id_assigner(View view)
    {
        recyclerView = view.findViewById(R.id.T_FRAME);
    }

    public void setting_recycler_view()
    {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new H_Ticket_Adapter(arrayList2, this);
        recyclerView.setAdapter(adapter);
    }

    private void get_values()
    {
        arrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arrayList.add(snapshot.getValue(H_Ticket_Model.class));
                if(user_id.matches(arrayList.get(arrayList.size()-1).getUser_id()))
                {
                    arrayList2.add(arrayList.get(arrayList.size()-1));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onTicketClick(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Cancel Ticket");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                databaseReference.child(arrayList.get(position).getName()).removeValue();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Ticket Canceld Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), Home_Activity.class);
                        startActivity(intent);
                    }
                },5000);

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}