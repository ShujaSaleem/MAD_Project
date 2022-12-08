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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Edit_Buses extends Fragment {

    ImageView Bus_Photo;
    EditText Bus_Number;
    CheckBox AC, NONAC;
    Button Update, Delete;

    String bus_number, bus_type, bus_image, bid;
    int count;
    Uri bus;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Edit_Buses() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit__buses, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("BUSES");

        id_assigner(view);
        set_values();
        Buttons_Func();

        return view;
    }

    private void id_assigner(View view)
    {
        Bus_Photo = view.findViewById(R.id.edit_bus_photo);
        Bus_Number = view.findViewById(R.id.edit_bus_number);
        AC = view.findViewById(R.id.edit_check_ac);
        NONAC = view.findViewById(R.id.edit_check_nonac);
        Update= view.findViewById(R.id.submit_update);
        Delete = view.findViewById(R.id.submit_delete);
    }

    private void set_values()
    {
        bus_type = View_Buses.Bus_T;
        bus_number = View_Buses.Bus_N;
        bus_image = View_Buses.Bus_I;
        bid = View_Buses.Bus_ID;

        if(bus_type.matches("AC"))
        {
            AC.isChecked();
        }
        else {
            NONAC.isChecked();
        }

        Bus_Number.setText(bus_number);
        Picasso.get().load(bus_image).into(Bus_Photo);
    }

    private void get_values()
    {
        bus_number = Bus_Number.getText().toString().trim();
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
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_values();
                check_field();

                if(count == 1)
                {
                    Update_func();
                }
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete_func();
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

    private void Update_func()
    {
        if(count == 1)
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

                            Map<String, Object> map = new HashMap<>();
                            map.put("bus_Image", task.getResult().toString());
                            map.put("bus_Number", bus_number);
                            map.put("bus_Type", bus_type);
                            map.put("bus_id", bid);

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    databaseReference.child(bid).updateChildren(map);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(getView() != null){
                                                Toast.makeText (getContext(), "Bus details updated successfully", Toast.LENGTH_SHORT).show();
                                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.A_FRAME, new View_Buses()).commit();
                                            }
                                        }
                                    },3000);
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

    private void Delete_func()
    {
        databaseReference.child(bid).removeValue();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getView() != null){
                    Toast.makeText(getContext(), "Bus details deleted successfully", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.A_FRAME, new View_Buses()).commit();
                }
            }
        },3000);
    }
}