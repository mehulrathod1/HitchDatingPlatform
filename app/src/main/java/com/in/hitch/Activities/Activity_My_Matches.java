package com.in.hitch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.in.hitch.Adapter.MyMatchesAdapter;
import com.in.hitch.Model.MyMatchesModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.hitch.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Activity_My_Matches extends AppCompatActivity {

    ImageView imageView;
    BottomNavigationView bottomNavigationView;

    RecyclerView recyclerView;
    MyMatchesAdapter adapter;
    List<MyMatchesModel> list = new ArrayList<>();


    ImageView myMatchesBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_matches_new);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        setTitle("My Matches");
        getSupportActionBar().hide();
        init();

        recyclerView = findViewById(R.id.matchesRecycler);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.account).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent i = new Intent(getApplicationContext(), Activity_Home.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.matches:
                        i = new Intent(getApplicationContext(), Activity_top_hitches.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.chats:

                        break;
                    case R.id.account:
                        i = new Intent(getApplicationContext(), Activity_profile_menu.class);
                        startActivity(i);
                        finish();
                        break;
                }
                return true;
            }
        });
        myMatchesBack = findViewById(R.id.myMatchesBack);
        myMatchesBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_profile_menu.class);
                startActivity(intent);
            }
        });

        adapter = new MyMatchesAdapter(list, getApplicationContext(), new MyMatchesAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    private void init() {


        MyMatchesModel model = new MyMatchesModel(R.drawable.boy_img,
                R.drawable.like_anable,
                "charllie Hamilton",
                "50 Km away");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}