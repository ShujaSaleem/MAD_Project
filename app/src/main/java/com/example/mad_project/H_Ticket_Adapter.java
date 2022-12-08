package com.example.mad_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class H_Ticket_Adapter extends RecyclerView.Adapter<H_Ticket_Adapter.ViewHolder> {

    ArrayList<H_Ticket_Model> arrayList;
    ClickInterface ClickInterface;
    FirebaseUser user;
    String user_id="";


    public H_Ticket_Adapter(ArrayList<H_Ticket_Model> arrayList, ClickInterface ClickInterface) {
        this.arrayList = arrayList;
        this.ClickInterface = ClickInterface;
    }

    @NonNull
    @Override
    public H_Ticket_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_history_model, parent, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull H_Ticket_Adapter.ViewHolder holder, int position) {

//        if(user_id.matches(arrayList.get(position).getUser_id()))
//        {
//
//        }

            holder.name.setText(arrayList.get(position).getName());
            holder.gender.setText(arrayList.get(position).getGender());
            holder.cnic.setText(arrayList.get(position).getCnic());
            holder.seat.setText(arrayList.get(position).getSeat());
            holder.fromto.setText(arrayList.get(position).getFromto());
            holder.datetime.setText(arrayList.get(position).getDatetime());
            holder.fare.setText(arrayList.get(position).getFare());
            holder.type.setText(arrayList.get(position).getType());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClickInterface.onTicketClick(position);
                }
            });
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, gender, cnic, seat, fromto, datetime, fare, type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.H_name);
            gender= itemView.findViewById(R.id.H_gender);
            cnic  = itemView.findViewById(R.id.H_cnic);
            seat   = itemView.findViewById(R.id.H_seatno);
            fromto    = itemView.findViewById(R.id.H_from_to);
            datetime     = itemView.findViewById(R.id.H_date_time);
            fare      = itemView.findViewById(R.id.H_Fare);
            type       = itemView.findViewById(R.id.H_bustype);
        }
    }

    public interface ClickInterface
    {
        void onTicketClick(int position);
    }
}
