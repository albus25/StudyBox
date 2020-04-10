package com.example.studybox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class MyCourse extends OptionMenuAct {
    ListView lst;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String, String>>();
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        setTitle("My Courses");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyCourse.this);
        final String StudID = sharedPreferences.getString("StudID","");

        lst = findViewById(R.id.lstMyCourse);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.My_Course,
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
                            map.put("courseTitle",jsonObject1.getString("courseTitle"));
                            map.put("courseDescription",jsonObject1.getString("courseDescription"));
                            map.put("courseDuration",jsonObject1.getString("courseDuration"));
                            map.put("courseStartDate",jsonObject1.getString("courseStartDate"));

                            arrayList.add(map);
                        }
                        SimpleAdapter adapter = new SimpleAdapter(MyCourse.this,arrayList,R.layout.mycourse,
                                new String[]{"courseTitle","courseDescription","courseDuration","courseStartDate"},
                                new int[]{R.id.txtmctitle,R.id.txtmcdesc,R.id.txtmcdur,R.id.txtmcsdt});
                        lst.setAdapter(adapter);
                    } catch (JSONException e) {
                        Toast.makeText(MyCourse.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MyCourse.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("studID",StudID);

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
