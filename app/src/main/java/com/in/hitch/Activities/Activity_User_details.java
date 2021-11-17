package com.in.hitch.Activities;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.in.hitch.R;

public class Activity_User_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("User Name");
        init();
    }

    private void init(){

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}