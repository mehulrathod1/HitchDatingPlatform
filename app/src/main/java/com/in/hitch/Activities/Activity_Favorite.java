package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.base_url;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.hitch.Adapter.MyMatchesAdapter;
import com.in.hitch.Model.MyMatchesModel;
import com.in.hitch.R;
import com.in.hitch.Utils.Glob;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Favorite extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    RecyclerView recyclerView;
    MyMatchesAdapter adapter;
    List<MyMatchesModel.MatchesData> list = new ArrayList<>();

    ProgressDialog progressDialog;

    ImageView myMatchesBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        getSupportActionBar().hide();
        init();
        getMyMatches(Glob.Token,"48");

    }

    private void init() {

        myMatchesBack = findViewById(R.id.myMatchesBack);
        recyclerView = findViewById(R.id.matchesRecycler);
        progressDialog = new ProgressDialog(Activity_Favorite.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


        myMatchesBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void getMyMatches(String token, String userId) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.getMyMatches(token, userId).enqueue(new Callback<MyMatchesModel>() {
            @Override
            public void onResponse(Call<MyMatchesModel> call, Response<MyMatchesModel> response) {

                MyMatchesModel myMatchesModel = response.body();

                List<MyMatchesModel.MatchesData> dataList = myMatchesModel.getMatchesData();

                for (int i = 0; i < dataList.size(); i++) {

                    MyMatchesModel.MatchesData model = dataList.get(i);

                    MyMatchesModel.MatchesData data = new MyMatchesModel.MatchesData(
                            model.getUser_id(),
                            model.getImage(),
                            model.getTitle(),
                            model.getKm_diff(),
                            model.getLiked()
                    );

                    list.add(data);
                }

                setMatchesData();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MyMatchesModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void setMatchesData() {

        adapter = new MyMatchesAdapter(list, getApplicationContext(), new MyMatchesAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

}