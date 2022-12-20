package com.example.project.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.project.R;
import com.example.project.Utils.Endpoints;
import com.example.project.Utils.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameT, idT, pwT, cityT, phoneT;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameT = findViewById(R.id.name);
        idT = findViewById(R.id.id);
        pwT = findViewById(R.id.pw);
        cityT = findViewById(R.id.city);
        phoneT = findViewById(R.id.phone);
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, id, pw, city, phone;
                name = nameT.getText().toString();
                id = idT.getText().toString();
                pw = pwT.getText().toString();
                city = cityT.getText().toString();
                phone = phoneT.getText().toString();
                if(isValid(name,id,pw,city,phone)){
                    register(name,id,pw,city,phone);
                }
            }
        });

    }

    private void register(String name, String id, String pw, String city, String phone){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("회원가입 성공")){
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    RegisterActivity.this.finish();
                }else{
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "오류 발생", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("id", id);
                params.put("pw", pw);
                params.put("city", city);
                params.put("phone", phone);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private boolean isValid(String name, String id, String pw, String city, String phone){
        if(name.isEmpty()){
            showMessage("성명을 입력해주세요.");
            return false;
        }else if(id.isEmpty()){
            showMessage("아이디를 입력해주세요.");
            return false;
        }else if(pw.isEmpty()) {
            showMessage("비밀번호를 입력해주세요.");
            return false;
        }else if(city.isEmpty()) {
            showMessage("지역을 입력해주세요.");
            return false;
        }else if(phone.isEmpty()) {
            showMessage("전화번호를 입력해주세요.");
            return false;
        }else if(phone.length() != 11){
            showMessage("전화번호 '-'를 제외하고 입력해주세요.");
            return false;
        }
        return true;
    }

    private void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}