package com.example.hremplyees;

import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class Dao_Employee {

    private String dbURL;
    private DatabaseReference dbReference;

    public Dao_Employee() {
        this.dbURL = "https://hremployee-bc463-default-rtdb.firebaseio.com/";
        FirebaseDatabase db = FirebaseDatabase.getInstance(this.dbURL);
        dbReference = db.getReference(Employee.class.getSimpleName());
    }

    public Task<Void> add(Employee emp) {
        return dbReference.push().setValue(emp);
    }

    public Task<Void> update(String key, HashMap<String, Object> employee) {
        return dbReference.child(key).updateChildren(employee);
    }

    public Task<Void> remove(String key) {
        return dbReference.child(key).removeValue();
    }

    public Query get() {
        return dbReference;
    }

    public Query get(String key) {
        if(key == null) {
            return dbReference.orderByKey().limitToFirst(5);
        }
        return dbReference.orderByKey().startAfter(key).limitToFirst(8);
    }
}
