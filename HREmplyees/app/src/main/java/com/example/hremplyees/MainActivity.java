package com.example.hremplyees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText edit_name = findViewById(R.id.edit_name);
        final EditText edit_position = findViewById(R.id.edit_position);
        Button btn = findViewById(R.id.btn_submit);
        Button btn_open = findViewById(R.id.btn_open);
        btn_open.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RV_Activity.class);
            startActivity(intent);
        });
        Dao_Employee dao = new Dao_Employee();
        Employee emp_edit = (Employee) getIntent().getSerializableExtra("EDIT");
        if(emp_edit != null) {
            btn.setText(R.string.update_text);
            edit_name.setText(emp_edit.getName());
            edit_position.setText(emp_edit.getPosition());
            //btn_open.setVisibility(View.GONE); // нквидимая .// VISIBLE для видимости
        } else {
            btn.setText(R.string.submit_text);
        }
        btn.setOnClickListener(view -> {
            Employee employee = new Employee(edit_name.getText().toString(), edit_position.getText().toString());
            if(emp_edit == null) {
                dao.add(employee).addOnSuccessListener(success -> {
                    Toast.makeText(this, "Employee added", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(err -> {
                    Toast.makeText(this, "Error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } else {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", edit_name.getText().toString());
                hashMap.put("position", edit_position.getText().toString());
                dao.update(emp_edit.getKey(), hashMap).addOnSuccessListener(success -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(err -> {
                    Toast.makeText(this, "Error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}