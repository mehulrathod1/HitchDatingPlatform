package com.in.hitch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.in.hitch.Adapter.TopHitchesAdapter;
import com.in.hitch.Adapter.WhoLikeYouAdapter;
import com.in.hitch.Model.TopHitchesModel;
import com.in.hitch.Model.WhoLikesYouModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.hitch.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Activity_top_hitches extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView tv_top_hitches, tv_who_likes_you;

    RecyclerView topHitchesRecycle;
    WhoLikeYouAdapter adapter;
    public List<WhoLikesYouModel> list = new ArrayList<>();

    TopHitchesAdapter topHitchesAdapter;
    List<TopHitchesModel> topHitchesModelList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_hitches_who_likes);
        getSupportActionBar().hide();


        topHitchesRecycle = findViewById(R.id.topHitchesRecycle);
        init();
        whoLikesRecycler();

    }

    private void init() {

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        tv_top_hitches = findViewById(R.id.tv_top_hitches);
        tv_who_likes_you = findViewById(R.id.tv_who_likes_you);
        tv_who_likes_you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                whoLikesRecycler();
                tv_who_likes_you.setTextColor(getResources().getColor(R.color.black));
                tv_top_hitches.setTextColor(getResources().getColor(R.color.md_grey_500));

            }
        });
        tv_top_hitches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                topRecycler();
                tv_top_hitches.setTextColor(getResources().getColor(R.color.black));
                tv_who_likes_you.setTextColor(getResources().getColor(R.color.md_grey_500));
            }
        });

        bottomNavigationView.getMenu().findItem(R.id.matches).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent i = new Intent(Activity_top_hitches.this, Activity_Home.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.matches:
                        break;
                    case R.id.chats:
                        i = new Intent(Activity_top_hitches.this, Activity_My_chats.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.account:
                        i = new Intent(Activity_top_hitches.this, Activity_profile_menu.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });
    }
    public void whoLikesRecycler() {

        getData();
        adapter = new WhoLikeYouAdapter(list, getApplicationContext(), new WhoLikeYouAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }
        });
        topHitchesRecycle.setLayoutManager(new GridLayoutManager(this, 2));
        topHitchesRecycle.setAdapter(adapter);
    }
    public void topRecycler(){

        topRecyclerData();
        topHitchesAdapter = new TopHitchesAdapter(topHitchesModelList, getApplicationContext(), new WhoLikeYouAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }
        });
        topHitchesRecycle.setLayoutManager(new GridLayoutManager(this, 2));
        topHitchesRecycle.setAdapter(topHitchesAdapter);

    }
    public void topRecyclerData(){
        TopHitchesModel model= new TopHitchesModel(R.drawable.my_profile,"Jennifer, 25","50 KM");

        topHitchesModelList.add(model);
        topHitchesModelList.add(model);
        topHitchesModelList.add(model);
        topHitchesModelList.add(model);
        topHitchesModelList.add(model);
        topHitchesModelList.add(model);
        topHitchesModelList.add(model);
        topHitchesModelList.add(model);
        topHitchesModelList.add(model);

    }
    public void getData() {
        WhoLikesYouModel model = new WhoLikesYouModel(R.drawable.girl_img, R.drawable.nope_, R.drawable.like_,
                "Jennifer, 25","50 KM");

        list.add(model);
        list.add(model);
        list.add(model);
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
    public void onBackPressed() {

        Intent intent;
        intent = new Intent(getApplicationContext(), Activity_Home.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();

    }


}