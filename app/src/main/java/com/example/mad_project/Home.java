package com.example.mad_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Home extends Fragment {

    private ImageView profile_image, home_image;
    private Button search, logout;
    private Spinner TO, FROM, DATE, TIME;
    private String [] Cities = new String[]{"Abbottabad", "Bahawalpur", "Chakwal", "Daska", "Faisalabad", "Gujrat", "Hyderabad", "Islamabad", "Jhelum","Karachi", "Lahore", "Multan", "Nawabshah", "Okara", "Peshawar", "Rawalpindi", "Shekhupura", "Taxila", "Wazirabad"};
    private String [] Distances = new String[]{"0", "710", "219", "525", "413", "1288", "1350", "133", "250", "1502", "468", "628", "1228", "509", "204", "134", "412", "78", "321",};
    private String [] Dates = new String[5];
    private String [] AM_Times = new String[]{ "01:00 AM", "04:30 AM", "08:00 AM", "11:30 AM"};
    private String [] PM_Times = new String[]{ "03:00 PM", "07:00 PM", "09:30: PM", "11:30 PM"};
    private String [] Times = new String[5];
    private String [] Sub_Times;
    private String  Email, date = "", time = "", to = "", from = "";
    private int count, fare = 0, si = 0, di = 0;
    private Calendar calendar;
    private int dd,mm ,yy , h, m , aa;
    static public String[] DataArray = new String[5];
    static public String UserID;

    ArrayList<User_Detail_Model> user_detail_model = new ArrayList<>();

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Home() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("USERS");
        calendar = Calendar.getInstance();
        id_assigner(view);
        get_User();
        get_User_Image();
        lists();
        get_values();
        Buttons_func();

        return view;
    }

    private void check_time()
    {
        aa = calendar.get(Calendar.AM_PM);
        h = calendar.get(Calendar.HOUR);

        if(aa == 0)
        {
            int i, j,ch=0;
            for(i=0; i<4; i++)
            {
                if(h < Integer.parseInt(AM_Times[i].substring(0, AM_Times[i].indexOf(":"))))
                {
                    Sub_Times = new String[4-i];
                    Times = new String[8-i];

                    for(j=0; j<4-i; j++)
                    {
                        Sub_Times[j] = AM_Times[i+j];
                        Times[j] = AM_Times[i+j];
                    }

                    for(int k=0; k<4; k++)
                    {
                        if(ch<=5)
                        {
                            Times[k+j] = PM_Times[k];
                        }
                    }
                    break;
                }
            }
        }
        else
        {
            int i, j, ch=0;
            for(i=0; i<4; i++)
            {
                if(h < Integer.parseInt(PM_Times[i].substring(0, PM_Times[i].indexOf(":"))))
                {
                    Sub_Times = new String[4-i];
                    Times = new String[8-i];

                    for(j=0; j<4-i; j++)
                    {
                        Sub_Times[j] = PM_Times[i+j];
                        Times[j] = PM_Times[i+j];
                    }

                    for(int k=0; k<4; k++)
                    {
                        if(ch<=5)
                        {
                            Times[k+j] = AM_Times[k];
                        }
                    }
                    break;
                }
            }
        }
    }

    private void get_date()
    {
        dd = calendar.get(Calendar.DAY_OF_MONTH);
        mm = calendar.get(Calendar.MONTH);

        Dates[0] = dd+"-"+mm;

        for(int i=1; i<5;i++)
        {
            if(dd == 30)
            {
                dd=1;
                Dates[i] = dd+"-"+(mm+1);
            }

            if(mm == 12)
            {
                if(dd == 30)
                {
                    mm = 1;
                }
            }

            dd = dd+1;
            Dates[i] = dd+"-"+mm;
        }
    }

    private void lists()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,Cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FROM.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,Cities);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TO.setAdapter(adapter1);
        get_date();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,Dates);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DATE.setAdapter(adapter2);
        check_time();
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,Times);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TIME.setAdapter(adapter3);
    }

    private void id_assigner(View view)
    {
        profile_image = view.findViewById(R.id.profile_image);
        search = view.findViewById(R.id.Bus);
        TO = view.findViewById(R.id.To_Spinner);
        FROM = view.findViewById(R.id.From_Spinner);
        DATE = view.findViewById(R.id.Date_Spinner);
        TIME = view.findViewById(R.id.Time_Spinner);
        home_image = view.findViewById(R.id.home_image);
        logout = view.findViewById(R.id.Logout_User);
    }

    private void get_values()
    {
        TO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                to = Cities[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FROM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                from = Cities[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DATE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                date = Dates[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TIME.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time = Times[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void get_User_Image()
    {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                user_detail_model.add(snapshot.getValue(User_Detail_Model.class));

                for (int i=0; i < user_detail_model.size(); i++)
                {
                    if(UserID.matches(user_detail_model.get(i).getUserID()))
                    {
                        Picasso.get().load(user_detail_model.get(i).getImage()).into(profile_image);
                        break;
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void get_User()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Email = user.getEmail();
            int i=Email.indexOf("@");
            UserID = Email.substring(0,i);
        }
    }

    private void Buttons_func()
    {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count = 0;

                if(to.isEmpty())
                {
                    Toast.makeText(getContext(), "Please select a Destination City", Toast.LENGTH_SHORT).show();
                }
                else if(from.isEmpty())
                {
                    Toast.makeText(getContext(), "Please select a Current City", Toast.LENGTH_SHORT).show();
                }
                else if(to.matches(from))
                {
                    Toast.makeText(getContext(), "Please select a different destination", Toast.LENGTH_SHORT).show();
                }
                else if(time.isEmpty())
                {
                    Toast.makeText(getContext(), "Please select a timen", Toast.LENGTH_SHORT).show();
                }
                else if(date.isEmpty())
                {
                    Toast.makeText(getContext(), "Please select a date", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    for(int i=0; i<Cities.length; i++)
                    {
                        if(Cities[i].matches(to))
                        {
                            si = i;
                        }

                        if(Cities[i].matches(from))
                        {
                            di = i;
                        }
                    }

                    fare = Integer.parseInt(Distances[si])- Integer.parseInt(Distances[di]);
                    if(fare < 0)
                    {
                        fare = fare*(-1);
                    }
                    count++;
                }

                if(count == 1)
                {
                    DataArray[0] = to;
                    DataArray[1] = from;
                    DataArray[2] = date;
                    DataArray[3] = time;
                    DataArray[4] = String.valueOf(fare);
                    Toast.makeText(getContext(), "F: "+fare, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getContext(), Buses.class);
                    startActivity(intent);
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getContext(), Create_Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}