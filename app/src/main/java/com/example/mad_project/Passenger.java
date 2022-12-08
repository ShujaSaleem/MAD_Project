package com.example.mad_project;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Passenger extends Fragment {

    Button confirm, back;
    EditText name, cnic;
    CheckBox male, female;

    int count;
    static public String Gender, Name, Cnic;

    public Passenger() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passenger, container, false);

        id_assigner(view);
        Buttons_Func();

        return view;
    }

    private void id_assigner(View view)
    {
        confirm = view.findViewById(R.id.Confirm);
        name = view.findViewById(R.id.p_name);
        cnic = view.findViewById(R.id.p_cnic);
        male = view.findViewById(R.id.g_male);
        female = view.findViewById(R.id.g_female);
        back = view.findViewById(R.id.P_back);
    }

    private void get_values()
    {
        Name = name.getText().toString().trim();
        Cnic = cnic.getText().toString().trim();
    }

    private void fields_checks()
    {
        count=0;

        if(Name.isEmpty())
        {
            name.setError("Please enter a name");
            name.requestFocus();
            return;
        }

        if(Cnic.isEmpty())
        {
            cnic.setError("Please enter a name");
            cnic.requestFocus();
            return;
        }

        if(Cnic.length() == 13)
        {
            boolean check = isInt(Cnic);

            if(check == true)
            {
                if(male.isChecked() && female.isChecked())
                {
                    dialog_box();
                }
                else if(!male.isChecked() && !female.isChecked())
                {
                    dialog_box();
                }
                else if(male.isChecked())
                {
                    Gender = "Male";
                    count++;
                }
                else
                {
                    Gender = "Female";
                    count++;
                }
            }
            else
            {
                cnic.setError("Please enter a valid CNIC");
                cnic.requestFocus();
                return;
            }
        }
        else
        {
            cnic.setError("Please enter a valid CNIC");
            cnic.requestFocus();
            return;
        }
    }

    static boolean isInt(String s)
    {
        int a=0;

        for(int i=0; i<11; i++)
        {
            if(s.codePointAt(i) >= 48 && s.codePointAt(i) <= 57)
            {
                a++;
            }
            else
            {
                break;
            }
        }

        if(a == 11)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void dialog_box()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please select one Gender");
        builder.setTitle("Gender");
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void Buttons_Func()
    {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_values();
                fields_checks();

                if(count == 1)
                {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.B_FRAME, new Seat_Selection()).commit();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.B_FRAME, new Select_Buses()).commit();
        }
    });
    }
}