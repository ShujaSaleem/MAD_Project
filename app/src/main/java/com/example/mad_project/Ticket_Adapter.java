package com.example.mad_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Ticket_Adapter extends RecyclerView.Adapter<Ticket_Adapter.ViewHolder> {

    ArrayList<Bus_Model> arrayList;
    Context context;
    Ticket_Adapter.BusClickInterface busClickInterface;
    Home home;
    int Fare;

    public Ticket_Adapter(ArrayList<Bus_Model> arrayList,Context context, Ticket_Adapter.BusClickInterface busClickInterface)
    {
        this.arrayList = arrayList;
        this.context = context;
        this.busClickInterface = busClickInterface;
    }

    @NonNull
    @Override
    public Ticket_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ticket_Adapter.ViewHolder holder, int position) {

        Picasso.get().load(arrayList.get(position).getBus_Image()).into(holder.bus_image);
        holder.bus_type.setText(arrayList.get(position).getBus_Type());
        holder.from_to_city.setText(home.DataArray[1]+"-"+home.DataArray[0]);
        holder.date.setText(home.DataArray[2]+"/"+home.DataArray[3]);

        if(arrayList.get(position).getBus_Type().matches("AC"))
        {
            Fare = (Integer.parseInt(home.DataArray[4])*2)+1000;
        }
        else
        {
            Fare = (Integer.parseInt(home.DataArray[4])*2)+300;
        }

        holder.fee.setText("Rs."+String.valueOf(Fare));

        int P = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                home.DataArray[4] = String.valueOf(Fare);
                busClickInterface.onBusselectClick(P);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView bus_image;
        TextView bus_type, from_to_city, date, fee;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bus_image = itemView.findViewById(R.id.bus_model_image);
            bus_type = itemView.findViewById(R.id.bus_type);
            date = itemView.findViewById(R.id.date_time);
            from_to_city = itemView.findViewById(R.id.from_to);
            fee = itemView.findViewById(R.id.fee);
        }
    }

    public interface BusClickInterface
    {
        void onBusselectClick(int position);
    }
}
