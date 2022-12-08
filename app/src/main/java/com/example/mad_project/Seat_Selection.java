package com.example.mad_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class Seat_Selection extends Fragment {

    Button confirm, back;
    int [] images = new int[] {R.drawable.ic_baseline_event_seat_24, R.drawable.ic_baseline_event_seat_24,R.drawable.ic_baseline_event_seat_24,
            R.drawable.ic_baseline_event_seat_24,R.drawable.ic_baseline_event_seat_24,R.drawable.ic_baseline_event_seat_24,R.drawable.ic_baseline_event_seat_24,
            R.drawable.ic_baseline_event_seat_24,R.drawable.ic_baseline_event_seat_24,R.drawable.ic_baseline_event_seat_24,};

    String [] Gid_Left = new String[]{"1","2","3","4","5","6","7","8","9","10"};
    String [] Gid_Right = new String[]{"11","12","13","14","15","16","17","18","19","20"};
    static public String SEATNUMBER;

    GridView left, right;

    public Seat_Selection() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seat__selection, container, false);

        SEATNUMBER = "0";
        id_assigner(view);
        set_values();
        Buttons_Func();

        return view;
    }

    private void id_assigner(View view)
    {
        confirm = view.findViewById(R.id.Confirm);
        left = view.findViewById(R.id.Left_Grid);
        right = view.findViewById(R.id.Right_Grid);
        back = view.findViewById(R.id.Back);
    }

    private void set_values()
    {
        Seat_Selection_Adapter adapter = new Seat_Selection_Adapter(getContext(), "image" ,images, Gid_Left);
        left.setAdapter(adapter);

        Seat_Selection_Adapter adapter1 = new Seat_Selection_Adapter(getContext(), "image" ,images, Gid_Right);
        right.setAdapter(adapter1);

        left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SEATNUMBER = Gid_Left[i];
            }
        });

        right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SEATNUMBER = Gid_Right[i];
            }
        });
    }

    private void Buttons_Func()
    {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SEATNUMBER.matches("0"))
                {
                    Toast.makeText(getContext(), "Please select a seat", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.B_FRAME, new Ticket_Confirm()).commit();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.B_FRAME, new Passenger()).commit();
            }
        });
    }
}