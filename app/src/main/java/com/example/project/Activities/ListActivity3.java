package com.example.project.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.project.Adapter.RequestAdapter3;
import com.example.project.DataModels.RequestDataModel3;
import com.example.project.R;
import com.example.project.Utils.Endpoints;
import com.example.project.Utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity3 extends AppCompatActivity {

    private RecyclerView recyclerView3;
    private List<RequestDataModel3> requestDataModels3;
    private RequestAdapter3 requestAdapter3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list3);
        requestDataModels3 = new ArrayList<>();


        recyclerView3 = findViewById(R.id.recycleView3);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(layoutManager);
        requestAdapter3 = new RequestAdapter3(requestDataModels3, this);
        recyclerView3.setAdapter(requestAdapter3);
        populateHomePage();

    }

    private void populateHomePage(){
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, Endpoints.get_request3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<RequestDataModel3>>(){}.getType();
                List<RequestDataModel3> dataModels = gson.fromJson(response, type);
                requestDataModels3.addAll(dataModels);
                requestAdapter3.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListActivity3.this, "오류 발생", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest3);
    }

}