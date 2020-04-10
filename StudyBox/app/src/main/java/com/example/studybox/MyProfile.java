package com.example.studybox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyProfile extends OptionMenuAct {
    JSONArray jsonArray;
    Spinner sp;
    EditText txtsid,txtsnm,txtsmb,txtage,txtpass;
    Button bu;
    String[] city = new String[]{"Surat","Mumbai","Pune","Banglore","Delhi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("My Profile");

        sp = findViewById(R.id.spEStudCity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,city);
        sp.setAdapter(adapter);

        txtsid = findViewById(R.id.txtEStudID);
        txtsnm = findViewById(R.id.txtEStudName);
        txtsmb = findViewById(R.id.txtEStudMob);
        txtpass = findViewById(R.id.txtEPass);
        txtage = findViewById(R.id.txtEStudAge);
        bu = findViewById(R.id.btnUpdate);

        final String MobileNo = loadSharedPreference();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.Get_Student,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(response);
                        jsonArray = jsonObject1.getJSONArray("stud");

                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            HashMap<String,String> map = new HashMap<String,String>();
                            map.put("studID",jsonObject.getString("studID"));
                            map.put("studName",jsonObject.getString("studName"));
                            map.put("studMob",jsonObject.getString("studMob"));
                            map.put("studPass",jsonObject.getString("studPass"));
                            map.put("studAge",jsonObject.getString("studAge"));
                            map.put("studCity",jsonObject.getString("studCity"));


                            txtsid.setText(map.get("studID"));
                            txtsid.setEnabled(false);
                            txtsid.setVisibility(View.GONE);
                            txtsnm.setText(map.get("studName"));
                            txtsmb.setText(map.get("studMob"));
                            txtsmb.setEnabled(false);
                            txtpass.setText(map.get("studPass"));
                            txtpass.setVisibility(View.GONE);
                            txtage.setText(map.get("studAge"));
                            int x = Arrays.asList(city).indexOf(map.get("studCity"));
                            sp.setSelection(x);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(MyProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MyProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> params = new HashMap<>();
                    params.put("studMob",MobileNo);

                    return params;
                }
            };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URIs.Update_Student,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if(jsonObject.getInt("success") == 1)
                                {
                                    Toast.makeText(MyProfile.this, "Updated", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MyProfile.this,MainPage.class);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                Toast.makeText(MyProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MyProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        params.put("studID",txtsid.getText().toString());
                        params.put("studName",txtsnm.getText().toString());
                        params.put("studMob",txtsmb.getText().toString());
                        params.put("studPass",txtpass.getText().toString());
                        params.put("studAge",txtage.getText().toString());
                        params.put("studCity",sp.getSelectedItem().toString());

                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public String loadSharedPreference()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String MobileNo = sharedPreferences.getString("MobileNo","");
        if(MobileNo != null && !MobileNo.equals(""))
        {
            return MobileNo;
        }
        else
        {
            return "";
        }
    }
}
