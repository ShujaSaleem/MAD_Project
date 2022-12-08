package com.example.mad_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Seat_Selection_Adapter extends BaseAdapter {

    Context context;
    String IMAGE;
    int[] images;
    String [] numbers;

    LayoutInflater inflater;

    public Seat_Selection_Adapter(Context context, String IMAGE, int[] images, String [] numbers) {
        this.context = context;
        this.IMAGE = IMAGE;
        this.images = images;
        this.numbers = numbers;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.seat_selection_sayout, null);

        ImageView seatimage;
        seatimage = view.findViewById(R.id.seat_image);
        seatimage.setImageResource(images[i]);

        TextView seatnumber;
        seatnumber = view.findViewById(R.id.seat_number_tv);
        seatnumber.setText(numbers[i]);

        return view;
    }
}
