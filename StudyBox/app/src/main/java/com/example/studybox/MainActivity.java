package com.example.studybox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button bl,br;
    EditText tm,tp;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");

        bl = findViewById(R.id.btnLogin);
        br = findViewById(R.id.btnLRegister);
        tm = findViewById(R.id.txtLM);
        tp = findViewById(R.id.txtLP);

        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(tm.getText().toString()))
                {
                    tm.setError("Mobile No is required");
                    tm.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(tp.getText().toString()))
                {
                    tp.setError("Password is required");
                    tp.requestFocus();
                    return;
                }

                if(tm.getText().toString().equals("23646") && tp.getText().toString().equals("albus25"))
                {
                    Intent it = new Intent(MainActivity.this,AddCourse.class);
                    startActivity(it);
                }
                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.Login,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    jsonArray = jsonObject.getJSONArray("stud");

                                    for(int i=0;i<jsonArray.length();i++)
                                    {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        HashMap<String,String> map = new HashMap<String, String>();
                                        map.put("studID",jsonObject1.getString("studID"));
                                        map.put("studName",jsonObject1.getString("studName"));
                                        map.put("studMob",jsonObject1.getString("studMob"));

                                        Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();

                                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("MobileNo",tm.getText().toString());
                                        editor.putString("StudID",map.get("studID"));
                                        editor.putString("StudName",map.get("studName"));
                                        editor.commit();

                                        Intent it = new Intent(MainActivity.this,MainPage.class);
                                        startActivity(it);
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(MainActivity.this, "Err:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            params.put("studMob",tm.getText().toString());
                            params.put("studPass",tp.getText().toString());

                            return params;
                        }
                    };
                    VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                }
            }
        });
        loadSharedPreference();

        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,RegisterStudent.class);
                startActivity(it);
            }
        });
    }

    public void loadSharedPreference()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String MobileNo = sharedPreferences.getString("MobileNo","");
        if(MobileNo != null && !MobileNo.equals(""))
        {
            Intent it = new Intent(MainActivity.this,MainPage.class);
            startActivity(it);
        }
        else
        {
            Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show();
        }
    }
}
