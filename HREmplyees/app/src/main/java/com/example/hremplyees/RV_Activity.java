package com.example.hremplyees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.SnapshotHolder;

import java.util.ArrayList;

public class RV_Activity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerVie;
    RV_Adapter adapter;
    Dao_Employee dao;
    boolean isLoading = false;
    String key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        swipeRefreshLayout =  findViewById(R.id.swipe);
        recyclerVie = findViewById(R.id.rv);
        recyclerVie.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerVie.setLayoutManager(manager);
        adapter = new RV_Adapter(this);
        recyclerVie.setAdapter(adapter);
        dao = new Dao_Employee();

        loadData();
        recyclerVie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(manager == null) throw new ArithmeticException();
                int totalItem = manager.getItemCount();
                int lastVisible = manager.findLastVisibleItemPosition();
                if(totalItem < lastVisible) {
                    if (!isLoading) {
                        isLoading = true;
                        loadData();
                    }
                }
            }
        });
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        dao.get(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Employee> employees = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Employee emp = data.getValue(Employee.class);
                    if (emp == null) throw new AssertionError();
                    emp.setKey(data.getKey());
                    employees.add(emp);
                    key = data.getKey();
                }
                adapter.setItems(employees);
                adapter.notifyDataSetChanged();
                isLoading = false;
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}