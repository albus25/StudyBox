package com.example.studybox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class RegisterStudent extends AppCompatActivity {
    Button br;
    EditText txtName,txtMob,txtPass,txtCPass,txtAge;
    Spinner sp;
    String[] city = new String[]{"Surat","Mumbai","Pune","Banglore","Delhi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
        setTitle("Student Registration");

        sp = findViewById(R.id.spStudCity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,city);
        sp.setAdapter(adapter);

        txtName = findViewById(R.id.txtStudName);
        txtMob = findViewById(R.id.txtStudMob);
        txtPass = findViewById(R.id.txtStudPass);
        txtCPass = findViewById(R.id.txtCPass);
        txtAge = findViewById(R.id.txtStudAge);
        br = findViewById(R.id.btnRegister);

        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txtName.getText().toString()))
                {
                    txtName.setError("Student Name is required");
                    txtName.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(txtMob.getText().toString()))
                {
                    txtMob.setError("Mobile No is required");
                    txtMob.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(txtPass.getText().toString()))
                {
                    txtPass.setError("Password is required");
                    txtPass.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(txtCPass.getText().toString()))
                {
                    txtCPass.setError("Confirm Password is required");
                    txtCPass.requestFocus();
                    return;
                }
                else if(!txtPass.getText().toString().equals(txtCPass.getText().toString()))
                {
                    txtCPass.setError("Confirm Password is not matched");
                    txtCPass.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(txtAge.getText().toString()))
                {
                    txtAge.setError("Age is required");
                    txtAge.requestFocus();
                    return;
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.Register_Student,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if(jsonObject.getInt("success") == 1)
                                {
                                    Toast.makeText(RegisterStudent.this, "Inserted", Toast.LENGTH_SHORT).show();

                                    Intent it = new Intent(RegisterStudent.this,MainActivity.class);
                                    startActivity(it);
                                }
                                else
                                {
                                    Toast.makeText(RegisterStudent.this, "Not inserted", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(RegisterStudent.this, "Err:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterStudent.this, "Vl:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("studName",txtName.getText().toString());
                        params.put("studMob",txtMob.getText().toString());
                        params.put("studPass",txtPass.getText().toString());
                        params.put("studAge",txtAge.getText().toString());
                        params.put("studCity",sp.getSelectedItem().toString());

                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });
    }
}
