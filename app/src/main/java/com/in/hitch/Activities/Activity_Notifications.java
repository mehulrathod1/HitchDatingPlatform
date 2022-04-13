package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.base_url;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.in.hitch.Adapter.NotificationAdapter;
import com.in.hitch.Model.NotificationModel;
import com.in.hitch.R;
import com.in.hitch.Utils.Glob;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Notifications extends AppCompatActivity {

    RecyclerView notificationRecycler;
    NotificationAdapter adapter;
    ImageView notificationBack;
    List<NotificationModel.NotificationData> notificationList = new ArrayList<>();
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_list_items);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        setTitle("Notifications");
        getSupportActionBar().hide();
        init();
        getNotification(Glob.Token,"49");

        notificationBack = findViewById(R.id.notificationBack);
        notificationRecycler = findViewById(R.id.notificationRecycler);

        notificationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


    }

    private void init() {

        progressDialog = new ProgressDialog(Activity_Notifications.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


    }

    private void getNotification(String token, String userId) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();


        call.getNotification(token, userId).enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {

                NotificationModel notificationModel = response.body();

                List<NotificationModel.NotificationData> dataList = notificationModel.getNotificationList();

                for (int i = 0; i < dataList.size(); i++) {

                    NotificationModel.NotificationData model = dataList.get(i);

                    NotificationModel.NotificationData data = new NotificationModel.NotificationData(
                            model.getUsername(),
                            model.getProfile_img(),
                            model.getNotification(),
                            model.getTime()
                    );

                    notificationList.add(data);
                }
                notificationData();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {

                progressDialog.dismiss();


            }
        });
    }
    private void notificationData(){

        adapter = new NotificationAdapter(notificationList, getApplicationContext(), new NotificationAdapter.Click() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(Activity_Notifications.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        notificationRecycler.setLayoutManager(mLayoutManager);
        notificationRecycler.setAdapter(adapter);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}