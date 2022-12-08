package com.example.mad_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Error extends Fragment {

    TextView error_text;
    Button go_back;
    String Error, Fragment;
    int Frame_ID;

    public Error(int Frame_ID, String Fragment, String Error) {

        this.Fragment = Fragment;
        this.Frame_ID =Frame_ID;
        this.Error = Error;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_error, container, false);

        error_text = view.findViewById(R.id.error_text);
        error_text.setText(Error);

        go_back = view.findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        return view;
    }
}