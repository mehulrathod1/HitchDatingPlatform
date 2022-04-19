package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.Plane_Name;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.in.hitch.R;
import com.in.hitch.Utils.Glob;


public class Splash extends AppCompatActivity {


    Handler handler;
    String loginId,plane_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        SharedPreferences prefs = getSharedPreferences("MyPref", 0);
        loginId = prefs.getString("key", "null");
        plane_name = prefs.getString("PlaneName","null");
        Plane_Name = plane_name;
        Log.e("getSharedPreferences", "onCreate: " + plane_name);

        if (loginId.equals("null")) {
            move(MainActivity.class);
        } else {
            Glob.User_Id = loginId;
            move(Activity_Home.class);
        }
    }


    public void move(Class c) {

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, c);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }

}