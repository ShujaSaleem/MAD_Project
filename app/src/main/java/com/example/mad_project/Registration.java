package com.example.mad_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Registration extends Fragment {

    private EditText username, email, password, phonenumber, confirmpassword;
    private Button login, submit;
    private int count;
    private String UserName, Email, Password, PhoneNumber, ConfirmPassword, UserID, DefaultImage;
    private Uri Image;
    User_Detail_Model user_detail_model;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Registration() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("USERS");

        id_assigner(view);
        buttons_fun();

        return view;
    }

    private void id_assigner(View view)
    {
        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        phonenumber = view.findViewById(R.id.phonenumber);
        confirmpassword = view.findViewById(R.id.confirmpassword);

        login = view.findViewById(R.id.ahaa);
        submit = view.findViewById(R.id.signup);
    }

    private void getvalues()
    {
        UserName = username.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();
        PhoneNumber = phonenumber.getText().toString().trim();
        ConfirmPassword = confirmpassword.getText().toString().trim();
        get_UserID();
        Image = Uri.parse("android.resource://com.example.mad_project/drawable/defaultuser");
        DefaultImage = Image.toString();
    }

    private void get_UserID()
    {
        if(!Email.isEmpty())
        {
            int i = Email.indexOf("@");
            UserID = Email.substring(0,i);
        }
    }

    private void fields_checks()
    {
        count = 0;

        if(UserName.isEmpty())
        {
            username.setError("Please Enter a Valid User Name");
            username.requestFocus();
            return;
        }
        else
        {
            count++;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).find())
        {
            email.setError("Please Enter a Valid Email");
            email.requestFocus();
            return;
        }
        else
        {
            count++;
        }

        if(PhoneNumber.isEmpty())
        {
            phonenumber.setError("Please Enter a Phone Number");
            phonenumber.requestFocus();
            return;
        }
        else
        {
            if(PhoneNumber.length()>11)
            {
                phonenumber.setError("Phone Number is too long");
                phonenumber.requestFocus();
                return;
            }
            else
            {
                boolean check = isInt(PhoneNumber);

                if(check == true)
                {
                    count++;
                }
                else
                {
                    phonenumber.setError("Please Enter a Valid Phone Number");
                    phonenumber.requestFocus();
                    return;
                }
            }
        }

        if(Password.isEmpty())
        {
            password.setError("Please Enter a Valid Password");
            password.requestFocus();
            return;
        }
        else
        {
            count++;
        }

        if(ConfirmPassword.isEmpty())
        {
            confirmpassword.setError("Please Enter a Valid Password");
            confirmpassword.requestFocus();
            return;
        }
        else
        {
            if (ConfirmPassword.matches(Password))
            {
                count++;
            }
            else
            {
                confirmpassword.setError("Password do not match");
                confirmpassword.requestFocus();
                return;
            }

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

    private void buttons_fun()
    {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.CL_FRAME, new Login()).commit();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getvalues();
                fields_checks();

                if(count >= 5)
                {
                    FireBase_Register();
                }
            }
        });
    }

    private void FireBase_Register()
    {
        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Add_User_Detailes();
                    Toast.makeText(getContext(), "Registration Successfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Home_Activity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Add_User_Detailes()
    {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference filePath = firebaseStorage.getReference().child("images/").child(Image.getLastPathSegment());

        filePath.putFile(Image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        user_detail_model = new User_Detail_Model(UserName, Email, PhoneNumber, task.getResult().toString(), UserID);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                databaseReference.child(UserID).setValue(user_detail_model);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getContext(), Home_Activity.class);
                                        startActivity(i);
                                    }

                                }, 3000);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(), "User detailes not inserted", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
            }
        });
    }
}