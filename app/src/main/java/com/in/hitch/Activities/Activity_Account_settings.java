package com.in.hitch.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.in.hitch.R;

public class Activity_Account_settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Account Setting");
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