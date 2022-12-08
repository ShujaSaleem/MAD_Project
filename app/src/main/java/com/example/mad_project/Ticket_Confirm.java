package com.example.mad_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Ticket_Confirm extends Fragment {

    ImageView image;
    TextView name, gender, cnic, seat, fromto, datetime, fare, type;
    Button confirm, back;
    String user_id;
    Home home;
    Seat_Selection seat_selection;
    Passenger passenger;
    Select_Buses select_buses;

    public Ticket_Confirm() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket__confirm, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();
        Toast.makeText(getContext(), "User ID: "+user_id, Toast.LENGTH_SHORT).show();

        id_assigner(view);
        set_values();
        Buttons_Func();

        return view;
    }

    private void id_assigner(View view)
    {
        image = view.findViewById(R.id.TC_Bus_Image);
        name = view.findViewById(R.id.TC_name);
        gender  = view.findViewById(R.id.TC_gender);
        cnic  = view.findViewById(R.id.TC_cnic);
        seat  = view.findViewById(R.id.TC_seatno);
        fromto = view.findViewById(R.id.TC_from_to);
        datetime = view.findViewById(R.id.TC_date_time);
        fare = view.findViewById(R.id.TC_Fare);
        type = view.findViewById(R.id.TC_bustype);
        confirm = view.findViewById(R.id.TC_confirm_btn);
        back = view.findViewById(R.id.TC_BACK);
    }

    private void set_values()
    {
        Picasso.get().load(select_buses.BUS_DATAARRAY[0]).into(image);
        name.setText(passenger.Name);
        gender.setText(passenger.Gender);
        cnic.setText(passenger.Cnic);
        seat.setText(seat_selection.SEATNUMBER);
        fromto.setText(home.DataArray[0]+"-"+home.DataArray[1]);
        datetime.setText(home.DataArray[2]+"/"+home.DataArray[3]);
        fare.setText("Rs."+home.DataArray[4]);
        type.setText(select_buses.BUS_DATAARRAY[1]);
    }

    private void Buttons_Func()
    {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.B_FRAME, new Payment( passenger.Name,
                        passenger.Gender, passenger.Cnic,seat_selection.SEATNUMBER,  home.DataArray[0]+"-"+home.DataArray[1],
                        home.DataArray[2]+"/"+home.DataArray[3], home.DataArray[4], select_buses.BUS_DATAARRAY[1], select_buses.BUS_DATAARRAY[0],user_id)).commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.B_FRAME, new Seat_Selection()).commit();
            }
        });
    }
}