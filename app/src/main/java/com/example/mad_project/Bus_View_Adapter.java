package com.example.mad_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bus_View_Adapter extends RecyclerView.Adapter<Bus_View_Adapter.ViewHolder> {

    ArrayList<Bus_Model> arrayList;
    Context context;
    BusClickInterface busClickInterface;

    public Bus_View_Adapter(ArrayList<Bus_Model> arrayList,Context context, BusClickInterface busClickInterface)
    {
        this.arrayList = arrayList;
        this.context = context;
        this.busClickInterface = busClickInterface;
    }

    @NonNull
    @Override
    public Bus_View_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bus_type.setText(arrayList.get(position).getBus_Type());
        holder.bus_number.setText(arrayList.get(position).getBus_Number());

        Picasso.get().load(arrayList.get(position).getBus_Image()).into(holder.bus_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                busClickInterface.onBusClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bus_type, bus_number;
        ImageView bus_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bus_image = itemView.findViewById(R.id.view_busimage);
            bus_number = itemView.findViewById(R.id.BuS_NuMbEr);
            bus_type = itemView.findViewById(R.id.BuS_TyPe);
        }
    }

    public interface BusClickInterface
    {
        void onBusClick(int position);
    }
}
