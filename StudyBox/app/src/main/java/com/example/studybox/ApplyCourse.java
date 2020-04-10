package com.example.studybox;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApplyCourse extends OptionMenuAct {
    JSONArray jsonArray;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String, String>>();
    ListView lv;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_course);
        final Intent intent = getIntent();
        setTitle("Courses of "+intent.getStringExtra("CategoryName"));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplyCourse.this);
        final String StudID = sharedPreferences.getString("StudID","");
        final String StudName = sharedPreferences.getString("StudName","");

        tv = findViewById(R.id.txtSession);
        tv.setText(StudName);

        lv = findViewById(R.id.lstCourse);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String,String> map = arrayList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(ApplyCourse.this);
                builder.setMessage("Are sure you want to enroll for "+map.get("courseTitle"));
                builder.setPositiveButton("Yeah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URIs.Enroll_Course,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        if(jsonObject.getInt("success") == 1)
                                        {
                                            Toast.makeText(ApplyCourse.this, "Enrolled", Toast.LENGTH_SHORT).show();

                                            Intent intent1 = new Intent(ApplyCourse.this,MyCourse.class);
                                            startActivity(intent1);
                                        }
                                        else
                                        {
                                            Toast.makeText(ApplyCourse.this, "Not Enrolled", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(ApplyCourse.this,"Json:"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ApplyCourse.this,"Volley:"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> params = new HashMap<>();
                                params.put("studID",StudID);
                                params.put("courseID",map.get("courseID"));
                                params.put("studName",StudName);
                                params.put("courseTitle",map.get("courseTitle"));
                                params.put("courseDuration",map.get("courseDuration"));
                                params.put("courseStartDate",map.get("courseStartDate"));

                                return params;
                            }
                        };
                        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest1);
                    }
                });
                builder.setNegativeButton("Naah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ApplyCourse.this, "You cancel the operation", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.List_Course,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        jsonArray = jsonObject.getJSONArray("course");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            HashMap<String,String> map = new HashMap<String, String>();
                            map.put("courseID",jsonObject1.getString("courseID"));
                            map.put("courseTitle",jsonObject1.getString("courseTitle"));
                            map.put("courseDuration",jsonObject1.getString("courseDuration"));
                            map.put("courseStartDate",jsonObject1.getString("courseStartDate"));

                            arrayList.add(map);
                        }
                        SimpleAdapter adapter = new SimpleAdapter(ApplyCourse.this,arrayList,R.layout.courselayout,
                                new String[]{"courseTitle","courseDuration","courseStartDate"},
                                new int[]{R.id.txtctitle,R.id.txtcdur,R.id.txtcsdt});
                        lv.setAdapter(adapter);
                    } catch (JSONException e) {
                        Toast.makeText(ApplyCourse.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ApplyCourse.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("courseCategory",intent.getStringExtra("CategoryName"));

                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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
}
