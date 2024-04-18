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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
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

    private void add(){
        Log.i(TAG, "register: ");
      //  final ProgressDialog dialog = ProgressDialog.show(this, "", "Please wait...", false, false);

        attendence a = new attendence();
        a.setAttendanceDate("2024-04-18T06:26:14.217Z");
        a.setStatusId(10);
        a.setEntryBy(10);

        attendence a1 = new attendence();
        a1.setAttendanceDate("2024-04-19T06:26:14.217Z");
        a1.setStatusId(15);
        a1.setEntryBy(10);

        ArrayList<attendence> attList = new ArrayList<>();
        attList.add(a);
        attList.add(a1);

        JSONArray array = crateObject();


        String postUrl = "https://api.fiacademybd.com/api/edu-erp/mobile-app/InsertStudentAttendance";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, postUrl, array, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(MainActivity.this, "" + response.toString(), Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private JSONArray crateObject(){
            JSONArray array = new JSONArray();
            for (int i = 0; i < 2; i++) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("AttendanceDate", i + " Apr 2024");
                    obj.put("StudentId", i);
                    obj.put("StatusId", i + 1);
                    obj.put("EntryBy", i + 2);
                } catch (Exception exception)
                {
                    Log.i(TAG, "crateObject: ");
                }
                array.put("list"  + obj);
            }
            return array;
    }


}