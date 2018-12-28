package com.example.filip.schoolproject.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.filip.schoolproject.R;

public class MovieHolder extends RecyclerView.ViewHolder {
    TextView bus_title;


    public MovieHolder(@NonNull View itemView) {
        super(itemView);
        bus_title = itemView.findViewById(R.id.tv_bus_title);

    }
}
