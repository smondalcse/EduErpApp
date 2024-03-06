package com.edu.erpapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {
    private static final String TAG = "AttendenceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("Attendance");
        toolbar.setHomeButtonEnabled(true);

        classDropDownList();
    }
    ArrayAdapter<ClassModel> adapter;
    List<ClassModel> classModels;

    private void classDropDownList(){

        Spinner spinnerClass = findViewById(R.id.spinnerClass);
        classModels = new ArrayList<>();
        adapter = new ArrayAdapter<ClassModel>(this, R.layout.spinner_row, classModels);
        adapter.setDropDownViewResource(R.layout.spinner_row);
        spinnerClass.setAdapter(adapter);

        GetAllClassList();
    }

    private void GetAllClassList(){
        Log.i(TAG, "register: ");
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Please wait...", false, false);
        String URL = "https://api.fiacademybd.com/api/edu-erp/mobile-app/GetAllClassList";
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: ");
                Log.i(TAG, "onResponse: " + response);

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                classModels.clear();

                List<ClassModel> models = Arrays.asList(gson.fromJson(response, ClassModel[].class));

                if (models.size() > 0) {
                    classModels.addAll(models);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
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