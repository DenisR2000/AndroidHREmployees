package com.example.hremplyees;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VH_Employee extends RecyclerView.ViewHolder {

    public TextView txt_name;
    public TextView txt_position;
    public TextView txt_option;

    public VH_Employee(@NonNull View view) {
        super(view);
        txt_name = view.findViewById(R.id.txt_name);
        txt_position = view.findViewById(R.id.txt_position);
        txt_option = view.findViewById(R.id.txt_option);
    }
}
