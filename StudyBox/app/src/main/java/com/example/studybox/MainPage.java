package com.example.studybox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainPage extends OptionMenuAct {
    ListView lst;
    private String[] cc = new String[]{"Science","Commerce","Arts","Professional","Vocational"};
    private Integer[] ci = new Integer[]{R.drawable.deadpool,R.drawable.punisher,R.drawable.spider_man,R.drawable.ironman,R.drawable.wonder_woman};
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setTitle("Categories");

        tv = findViewById(R.id.txtSessionName);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String StudName = sharedPreferences.getString("StudName","");
        if(StudName!=null && !StudName.equals(""))
        {
            tv.setText("Hola " + StudName);
        }

        lst = findViewById(R.id.cList);
        CategoryList list = new CategoryList(this,cc,ci);
        lst.setAdapter(list);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainPage.this,ApplyCourse.class);
                intent.putExtra("CategoryName",cc[position]);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainPage.class));
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