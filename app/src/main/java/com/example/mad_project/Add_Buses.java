package com.example.mad_project;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Add_Buses extends Fragment {

    ImageView Bus_Photo;
    EditText Bus_Number;
    CheckBox AC, NONAC;
    Button Submit;
    StorageReference storageReference;
    String bus_number, bus_type, bus_image, BUS_ID;
    int count;
    Uri bus;
    Bus_Model bus_model;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_add__buses, container, false);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("BUSES");

       id_assigner(view);
       Buttons_Func();

       return view;
    }

    private void id_assigner(View view)
    {
        Bus_Photo = view.findViewById(R.id.bus_photo);
        Bus_Number = view.findViewById(R.id.a_bus_number);
        AC = view.findViewById(R.id.check_ac);
        NONAC = view.findViewById(R.id.check_nonac);
        Submit = view.findViewById(R.id.submit_add);
    }

    private void get_values()
    {
        bus_number = Bus_Number.getText().toString().trim();
        BUS_ID= bus_number;
    }

    private void check_field()
    {
        count = 0;

        if(bus_number.isEmpty())
        {
            Bus_Number.setError("Enter a bus number");
            Bus_Number.requestFocus();
            return;
        }
        else if(bus_number.length() > 6)
        {
            Bus_Number.setError("Enter a valid bus number");
            Bus_Number.requestFocus();
            return;
        }
        else
        {
            if(AC.isChecked() && NONAC.isChecked())
            {
                dialog_box();
            }
            else if(!AC.isChecked() && !NONAC.isChecked())
            {
                dialog_box();
            }
            else
            {
                if(AC.isChecked())
                {
                    bus_type = "AC";
                }
                else
                {
                    bus_type = "Non AC";
                }
                count++;
            }
        }
    }

    private void dialog_box()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please select one bus type");
        builder.setTitle("Bus Type");
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void Buttons_Func()
    {
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_values();
                check_field();
                if(count == 1)
                {
                    Add_Bus_Func();
                }
            }
        });

        Bus_Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_image();
            }
        });
    }

    private void select_image()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && data != null && data.getData() != null)
        {
            bus = data.getData();
            Bus_Photo.setImageURI(bus);
        }
    }

    private void Add_Bus_Func()
    {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference filePath = firebaseStorage.getReference().child("images/").child(bus.getLastPathSegment());

        filePath.putFile(bus).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        bus_model = new Bus_Model(BUS_ID, bus_number, task.getResult().toString(), bus_type);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                databaseReference.child(BUS_ID).setValue(bus_model);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getContext(), Admin_Activity.class);
                                        startActivity(i);
                                    }

                                }, 3000);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(), "Bus details insertion failed", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
            }
        });
    }
}