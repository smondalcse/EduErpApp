package com.edu.erpapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText etLoginID, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("Attendance");
        toolbar.hide();

        etLoginID = findViewById(R.id.etLoginID);
        etPass = findViewById(R.id.etPass);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etLoginID.getText().toString();
                String pass = etPass.getText().toString();
                if (id.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Enter Login ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Enter Pass", Toast.LENGTH_SHORT).show();
                    return;
                }
                GetUser(id, pass);
            }
        });

    }

    private void GetUser(String id, String pass){
        Log.i(TAG, "register: ");
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Please wait...", false, false);
        String URL = "https://api.fiacademybd.com/api/edu-erp/mobile-app/GetUser?userName="+ id+"&password=" + pass;
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: ");
                Log.i(TAG, "onResponse: " + response);

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                UserModel model = gson.fromJson(response, UserModel.class);
                if (model.getStatus().equalsIgnoreCase("Invalid")) {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, model.getMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, AttendanceActivity.class);
                intent.putExtra("UserModel", model);
                dialog.dismiss();
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.i(TAG, "onErrorResponse: " + error.getMessage());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }


}