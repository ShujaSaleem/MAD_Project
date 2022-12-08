package com.example.mad_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Select_Buses extends Fragment implements Ticket_Adapter.BusClickInterface{

    private Button back;
    private RecyclerView recyclerView;
    private ArrayList<Bus_Model> bus_modelArrayList = new ArrayList<>();
    Ticket_Adapter adapter;

    static public String [] BUS_DATAARRAY = new String[2];

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Select_Buses() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select__buses, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("BUSES");

        id_assigner(view);
        Recycler_View(view);
        Button_func();

        return view;
    }

    private void id_assigner(View view)
    {
        back = view.findViewById(R.id.back_btn);
        recyclerView = view.findViewById(R.id.Bus_Recycler);
    }


    private void Recycler_View(View view)
    {
        recyclerView = view.findViewById(R.id.Bus_Recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        get_values();
        adapter = new Ticket_Adapter(bus_modelArrayList, getContext(), (Ticket_Adapter.BusClickInterface) this);
        recyclerView.setAdapter(adapter);
    }

    private void get_values()
    {
        bus_modelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                bus_modelArrayList.add(snapshot.getValue(Bus_Model.class));
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

    private void  Button_func()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Home_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBusselectClick(int position) {
        BUS_DATAARRAY[0] = bus_modelArrayList.get(position).getBus_Image();
        BUS_DATAARRAY[1] = bus_modelArrayList.get(position).getBus_Type();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.B_FRAME, new Passenger()).commit();
    }
}