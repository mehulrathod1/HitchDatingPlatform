package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.base_url;

import android.app.ProgressDialog;
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
import com.in.hitch.Utils.Glob;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_top_hitches extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView tv_top_hitches, tv_who_likes_you;

    RecyclerView topHitchesRecycle;
    WhoLikeYouAdapter adapter;
    public List<WhoLikesYouModel.WhoLikesYou> list = new ArrayList<>();

    TopHitchesAdapter topHitchesAdapter;
    List<WhoLikesYouModel.WhoLikesYou> topHitchesModelList = new ArrayList<>();


    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_hitches_who_likes);
        getSupportActionBar().hide();


        topHitchesRecycle = findViewById(R.id.topHitchesRecycle);
        init();
        getWhoLikeYou(Glob.Token, Glob.User_Id);

    }

    private void init() {

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        tv_top_hitches = findViewById(R.id.tv_top_hitches);
        tv_who_likes_you = findViewById(R.id.tv_who_likes_you);

        progressDialog = new ProgressDialog(Activity_top_hitches.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


        tv_who_likes_you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_who_likes_you.setTextColor(getResources().getColor(R.color.black));
                tv_top_hitches.setTextColor(getResources().getColor(R.color.md_grey_500));

                getWhoLikeYou(Glob.Token, Glob.User_Id);

            }
        });
        tv_top_hitches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_top_hitches.setTextColor(getResources().getColor(R.color.black));
                tv_who_likes_you.setTextColor(getResources().getColor(R.color.md_grey_500));
                getTopHitches(Glob.Token, Glob.User_Id);
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

    private void whoLikesRecycler() {

        adapter = new WhoLikeYouAdapter(list, getApplicationContext(), new WhoLikeYouAdapter.Click() {
            @Override
            public void onItemClick(int position) {

                String profileId = list.get(position).getUser_id();
                Intent i = new Intent(Activity_top_hitches.this, Activity_Profile_details.class);
                i.putExtra("profileId", profileId);
                i.putExtra("flag", "Activity_top_hitches");
                startActivity(i);

            }

            @Override
            public void onItemLike(int position) {

            }

            @Override
            public void onItemRemove(int position) {

            }
        });
        topHitchesRecycle.setLayoutManager(new GridLayoutManager(this, 2));
        adapter.notifyDataSetChanged();
        topHitchesRecycle.setAdapter(adapter);

    }

    private void topRecycler() {


        topHitchesAdapter = new TopHitchesAdapter(topHitchesModelList, getApplicationContext(), new TopHitchesAdapter.Click() {
            @Override
            public void onItemClick(int position) {

                String profileId = topHitchesModelList.get(position).getUser_id();
                Intent i = new Intent(Activity_top_hitches.this, Activity_Profile_details.class);
                i.putExtra("profileId", profileId);
                i.putExtra("flag", "Activity_top_hitches");
                startActivity(i);
            }
        });

        topHitchesRecycle.setLayoutManager(new GridLayoutManager(this, 2));
        adapter.notifyDataSetChanged();
        topHitchesRecycle.setAdapter(topHitchesAdapter);

    }



    private void getWhoLikeYou(String token, String user_id) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.getWhoLikeYou(token, user_id).enqueue(new Callback<WhoLikesYouModel>() {
            @Override
            public void onResponse(Call<WhoLikesYouModel> call, Response<WhoLikesYouModel> response) {

                WhoLikesYouModel whoLikesYouModel = response.body();

                list.clear();
                List<WhoLikesYouModel.WhoLikesYou> dataList = whoLikesYouModel.getChatDataList();

                for (int i = 0; i < dataList.size(); i++) {

                    WhoLikesYouModel.WhoLikesYou model = dataList.get(i);

                    WhoLikesYouModel.WhoLikesYou data = new WhoLikesYouModel.WhoLikesYou(
                            model.getUser_id(),model.getFirst_name(), model.getLast_name(),
                            model.getAge(), model.getKm_diff(),
                            model.getImage()
                    );
                    list.add(data);
                }
                whoLikesRecycler();
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<WhoLikesYouModel> call, Throwable t) {

                progressDialog.dismiss();
            }
        });


    }

    private void getTopHitches(String token, String user_id) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.getTopHitches(token, user_id).enqueue(new Callback<WhoLikesYouModel>() {
            @Override
            public void onResponse(Call<WhoLikesYouModel> call, Response<WhoLikesYouModel> response) {

                WhoLikesYouModel whoLikesYouModel = response.body();

                topHitchesModelList.clear();
                List<WhoLikesYouModel.WhoLikesYou> dataList = whoLikesYouModel.getChatDataList();

                for (int i = 0; i < dataList.size(); i++) {

                    WhoLikesYouModel.WhoLikesYou model = dataList.get(i);

                    WhoLikesYouModel.WhoLikesYou data = new WhoLikesYouModel.WhoLikesYou(
                            model.getUser_id(),model.getFirst_name(), model.getLast_name(),
                            model.getAge(), model.getKm_diff(),
                            model.getImage()
                    );
                    topHitchesModelList.add(data);
                }
                topRecycler();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<WhoLikesYouModel> call, Throwable t) {

                progressDialog.dismiss();
            }
        });


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