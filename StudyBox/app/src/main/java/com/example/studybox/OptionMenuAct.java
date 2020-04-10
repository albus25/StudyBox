package com.example.studybox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

public class OptionMenuAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mp:
                Intent it1 = new Intent(this,MyProfile.class);
                startActivity(it1);
                return true;

            case R.id.mc:
                Intent intent = new Intent(this,MyCourse.class);
                startActivity(intent);
                return true;

            case R.id.so:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent it = new Intent(this,MainActivity.class);
                startActivity(it);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
