package com.in.hitch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;

import com.in.hitch.R;

public class Activity_Matches extends AppCompatActivity {


    LinearLayout linearLayout1, linearLayout2, linearLayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("My Matches");
        init();
    }

    private void init(){
        linearLayout1 = findViewById(R.id.ll_main);
        linearLayout2 = findViewById(R.id.ll_main_2);
        linearLayout3 = findViewById(R.id.ll_main_3);

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Matches.this, Activity_My_Matches.class);
                startActivity(i);
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Matches.this, Activity_My_Matches.class);
                startActivity(i);
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Matches.this, Activity_My_Matches.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}