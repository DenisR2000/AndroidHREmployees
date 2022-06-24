package com.example.hremplyees;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RV_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    ArrayList<Employee> employees = new ArrayList<>();

    public RV_Adapter(Context context) {
        this.context = context;
    }

    public void setItems(ArrayList<Employee> list) {
        employees.addAll(list);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new VH_Employee(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        this.onBindViewHolder(holder, position, (Employee) null);
    }

    @SuppressLint("NonConstantResourceId")
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, Employee employee) {
        VH_Employee vh = (VH_Employee) holder;
        Employee emp = employee == null ? employees.get(position) : employee;
        vh.txt_name.setText(emp.getName());
        vh.txt_position.setText(emp.getPosition());
        vh.txt_option.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, vh.txt_option);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.menu_edit:
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("EDIT", emp);
                        context.startActivity(intent);
                        break;
                    case R.id.menu_remove:
                        Dao_Employee dao = new Dao_Employee();
                        dao.remove(emp.getKey()).addOnSuccessListener(success -> {
                            Toast.makeText(context, "Employee deleted", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            employees.remove(emp);
                        }).addOnFailureListener(err -> {
                            Toast.makeText(context, "Error: " + err.getMessage(), Toast.LENGTH_LONG).show();
                        });
                        break;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }
}
