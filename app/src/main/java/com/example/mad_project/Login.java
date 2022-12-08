package com.example.mad_project;

import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Login extends Fragment {

    private Button signin, register, forgot;
    private EditText Email, Pass;
    private String email, pass;
    private int count;

    FirebaseAuth mAuth;

    public Login() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        id_assigner(view);
        Buttons_func();

        return view;
    }

    private void id_assigner(View view)
    {
        signin = view.findViewById(R.id.btn_signin);
        register = view.findViewById(R.id.btn_register);
        Email = view.findViewById(R.id.email);
        Pass = view.findViewById(R.id.password);
        forgot = view.findViewById(R.id.btn_forgot);
    }

    private void get_values()
    {
        email = Email.getText().toString().trim();
        pass = Pass.getText().toString().trim();
    }

    private void fields_checks()
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

        if(pass.isEmpty())
        {
            Pass.setError("Please enter a password");
            Pass.requestFocus();
            return;
        }
        else
        {
            count++;
        }
    }

    private void Buttons_func()
    {
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_values();
                fields_checks();

                if(count >= 2)
                {
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                if(email.matches("admin@gmail.com"))
                                {
                                    Intent intent = new Intent(getContext(), Admin_Activity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), Home_Activity.class);
                                    startActivity(intent);
                                }
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.CL_FRAME, new Registration()).commit();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.CL_FRAME, new Forgot_Password()).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null)
        {
            if(user.getUid().matches("0IlqDPYzLud8pq44IGIh1zVzra23"))
            {
                mAuth.signOut();
            }
            else
            {
                Intent intent = new Intent(getContext(), Home_Activity.class);
                startActivity(intent);
            }
        }
    }
}