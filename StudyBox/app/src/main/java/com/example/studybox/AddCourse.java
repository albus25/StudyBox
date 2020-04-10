package com.example.studybox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddCourse extends AppCompatActivity {
    Button btnAdd;
    Spinner sp;
    String[] cc = new String[]{"Science","Commerce","Arts","Professional","Vocational"};
    EditText txtTitle,txtDescription,txtDuration,dtStartDate;
    DatePickerDialog datePickerDialog;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setTitle("Add Course");

        btnAdd = findViewById(R.id.btnAddCourse);
        sp = findViewById(R.id.spCourseCategory);
        txtTitle = findViewById(R.id.txtCourseTitle);
        txtDescription = findViewById(R.id.txtCourseDescription);
        txtDuration = findViewById(R.id.txtCourseDuration);
        dtStartDate = findViewById(R.id.dtStartDate);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dt = year + "-" + (month+1) + "-" + dayOfMonth;
                dtStartDate.setText(dt);
            }
        },year,month,day);
        dtStartDate.setInputType(InputType.TYPE_NULL);
        dtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cc);
        sp.setAdapter(arrayAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txtTitle.getText().toString()))
                {
                    txtTitle.setError("Title is required");
                    txtTitle.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty((txtDescription.getText().toString())))
                {
                    txtDescription.setError("Description is required");
                    txtDescription.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(txtDuration.getText().toString()))
                {
                    txtDuration.setError("Duration is required");
                    txtDuration.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(dtStartDate.getText().toString()))
                {
                    dtStartDate.setError("Date is required");
                    dtStartDate.requestFocus();
                    return;
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URIs.Add_Course,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if(jsonObject.getInt("success") == 1)
                                {
                                    Toast.makeText(AddCourse.this, "Inserted", Toast.LENGTH_SHORT).show();

                                    Intent it = new Intent(AddCourse.this,MainActivity.class);
                                    startActivity(it);
                                }
                                else
                                {
                                    Toast.makeText(AddCourse.this, "Not inserted", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(AddCourse.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddCourse.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("courseCategory",sp.getSelectedItem().toString());
                        params.put("courseTitle",txtTitle.getText().toString());
                        params.put("courseDescription",txtDescription.getText().toString());
                        params.put("courseDuration",txtDuration.getText().toString());
                        params.put("courseStartDate",dtStartDate.getText().toString());

                        return params;
                    }
                };
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });
    }
}
