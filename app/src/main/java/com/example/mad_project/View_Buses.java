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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class View_Buses extends Fragment implements  Bus_View_Adapter.BusClickInterface{

    public static String Bus_N, Bus_T, Bus_I, Bus_ID;

    ArrayList<Bus_Model> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    Bus_View_Adapter adapter;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public View_Buses() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view__buses, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("BUSES");

        Recycler_View(view);

        return view;
    }

    private void Recycler_View(View view)
    {
        recyclerView = view.findViewById(R.id.VB_RV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        get_values();
        adapter = new Bus_View_Adapter(arrayList, getContext(), this);
        recyclerView.setAdapter(adapter);
    }

    private void get_values()
    {
        arrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arrayList.add(snapshot.getValue(Bus_Model.class));
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
    public void onBusClick(int position) {

        Bus_N = arrayList.get(position).getBus_Number();
        Bus_T = arrayList.get(position).getBus_Type();
        Bus_I = arrayList.get(position).getBus_Image();
        Bus_ID = arrayList.get(position).getBus_id();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.A_FRAME, new Edit_Buses()).commit();
    }
}