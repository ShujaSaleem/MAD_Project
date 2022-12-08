package com.example.mad_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.regex.Pattern;

public class Payment extends Fragment {

    EditText phone_no, pin, card_no, date, code, zip_postal;
    Button payment;
    ToggleButton toggle;
    int count;
    boolean check;
    String Phone_No, Pin, Card_No, Date, Code, Zip_postal;
    static public String name, gender, cnic, seat, fromto, datetime, fare, type, image, user_id;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Payment()
    {

    }

    public Payment(String name, String gender, String cnic, String seat, String fromto, String datetime, String fare, String type, String image, String user_id) {
        this.name = name;
        this.gender = gender;
        this.cnic = cnic;
        this.seat = seat;
        this.fromto = fromto;
        this.datetime = datetime;
        this.fare = fare;
        this.type = type;
        this.image = image;
        this.user_id = user_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        id_assigner(view);
        Buttons_Func();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Tickets");

        return view;
    }

    private void id_assigner(View view)
    {
        phone_no = view.findViewById(R.id.P_Number);
        pin = view.findViewById(R.id.P_Pin);
        card_no = view.findViewById(R.id.P_Card_Number);
        date = view.findViewById(R.id.P_Exp_date);
        code = view.findViewById(R.id.P_code);
        zip_postal = view.findViewById(R.id.P_zip_postal);
        payment = view.findViewById(R.id.P_Submit_Btn);
        toggle = view.findViewById(R.id.P_Toggle_Btn);
    }

    private void get_values()
    {
        Phone_No = phone_no.getText().toString().trim();
        Pin = pin.getText().toString().trim();
        Card_No = card_no.getText().toString().trim();
        Date = date.getText().toString().trim();
        Code = code.getText().toString().trim();
        Zip_postal = zip_postal.getText().toString().trim();
    }

    private void Fields_Checks()
    {
        count = 0;

        if(Phone_No.isEmpty() && !Card_No.isEmpty())
        {

            if(Card_No.length() < 16 || Card_No.length() > 16)
            {
                card_no.setError("Please enter a valid card number");
                card_no.requestFocus();
                return;
            }
            else
            {
                check = isInt(Card_No);
                if(check == false)
                {
                    card_no.setError("Please enter a valid card number");
                    card_no.requestFocus();
                    return;
                }
                else
                {
                    count++;
                    count++;
                }
            }

            if(Date.isEmpty())
            {
                date.setError("Enter the expiray date");
                date.requestFocus();
                return;
            }
            else if(Date.length() > 5 || Date.length() < 5)
            {
                date.setError("Enter a valid date");
                date.requestFocus();
                return;
            }

            if(Code.isEmpty())
            {
                code.setError("Enter a secret code");
                code.requestFocus();
                return;
            }
            else if(Code.length() > 3 || Code.length() < 3)
            {
                code.setError("Enter a valid secret code");
                code.requestFocus();
                return;
            }
            else
            {
                check = isInt(Code);
                if(check == false)
                {
                    code.setError("Please enter a valid code");
                    code.requestFocus();
                    return;
                }
                else
                {
                    count++;
                }
            }

            if(Zip_postal.isEmpty())
            {
                zip_postal.setError("Enter the Zip/Postal code");
                zip_postal.requestFocus();
                return;
            }
            else if(Zip_postal.length() > 10)
            {
                zip_postal.setError("Enter a valid Zip/Postal code");
                zip_postal.requestFocus();
                return;
            }
            else
            {
                check = isInt(Zip_postal);
                if(check == false)
                {
                    zip_postal.setError("Please enter a valid Zip/Postal code");
                    zip_postal.requestFocus();
                    return;
                }
                else
                {
                    count++;
                }
            }
        }
        else if(!Phone_No.isEmpty() && Card_No.isEmpty())
        {
            if(Phone_No.length() > 11 || Phone_No.length() < 11)
            {
                phone_no.setError("Please enter a valid number");
                phone_no.requestFocus();
                return;
            }
            else
            {
                check = isInt(Phone_No);
                if(check == false)
                {
                    phone_no.setError("Please enter a valid phone number");
                    phone_no.requestFocus();
                    return;
                }else
                {
                    count++;
                    count++;
                }
            }

            if(Pin.isEmpty())
            {
                pin.setError("Enter the pin");
                pin.requestFocus();
                return;
            }
            else if(Pin.length() > 4 || Pin.length() < 4)
            {
                pin.setError("Code length too long");
                pin.requestFocus();
                return;
            }
            else
            {
                check = isInt(Pin);
                if(check == false)
                {
                    pin.setError("Please enter a valid pin");
                    pin.requestFocus();
                    return;
                }
                else
                {
                    count++;
                    count++;
                }
            }
        }
        else
        {
            dialog_box();
        }
    }

    private void Buttons_Func()
    {
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_values();
                Fields_Checks();

                if(count == 4)
                {
                    Add_Ticket_Func();
                }
                else
                {
                    Toast.makeText(getContext(), "Payment Unsuccessfull", Toast.LENGTH_SHORT).show();
                }
            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(toggle.isChecked())
                {
                    phone_no.setHint("Easy Paysa Number");
                }
                else
                {
                    phone_no.setHint("Jazz Cash Number");
                }
            }
        });
    }

    private void dialog_box()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please chose one payment method");
        builder.setTitle("Payment !");
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    static boolean isInt(String s)
    {
        int a=0;

        for(int i=0; i<s.length(); i++)
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

        if(a == s.length())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void Add_Ticket_Func()
    {
        Ticket_Model ticket_model = new Ticket_Model(name, gender, cnic, seat, fromto, datetime, fare, type, image, user_id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(name).setValue(ticket_model);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Ticket Registration Completed", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getContext(), Home_Activity.class);
                        startActivity(i);
                    }

                }, 3000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Ticket Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}