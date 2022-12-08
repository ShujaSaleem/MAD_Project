package com.example.mad_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends Fragment {

    EditText Email;
    Button Submit, Back;
    String email;
    int count;
    FirebaseAuth mAuth;

    public Forgot_Password() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot__password, container, false);

        mAuth = FirebaseAuth.getInstance();
        id_assigner(view);
        Buttons_Func();

        return view;
    }

    private void id_assigner(View view)
    {
        Email = view.findViewById(R.id.RP_Email);
        Submit = view.findViewById(R.id.RP_Submit_Btn);
        Back = view.findViewById(R.id.RP_Back_Btn);
    }

    private void get_values()
    {
        email = Email.getText().toString().trim();
    }

    private void Fields_Checks()
    {
        count = 0;

        if(!Patterns.EMAIL_ADDRESS.matcher(email).find())
        {
            Email.setError("Please enter a valid email");
            Email.requestFocus();
            return;
        }
        else
        {
            count++;
        }
    }

    private void Buttons_Func()
    {
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_values();
                Fields_Checks();
                if(count == 1)
                {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Please check your Email", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.CL_FRAME, new Home()).commit();
                            }
                            else {
                                Toast.makeText(getContext(), "try Again Some Thing went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });
    }
}