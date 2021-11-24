package com.in.hitch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.in.hitch.Adapter.NotificationAdapter;
import com.in.hitch.Model.NotificationModel;
import com.in.hitch.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Activity_Notifications extends AppCompatActivity {

    RecyclerView notificationRecycler;
    NotificationAdapter adapter;
    ImageView notificationBack;
    List<NotificationModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_list_items);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        setTitle("Notifications");
        getSupportActionBar().hide();
        init();
        notificationBack = findViewById(R.id.notificationBack);
        notificationRecycler = findViewById(R.id.notificationRecycler);

        notificationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Activity_Home.class);
                startActivity(intent);
            }
        });

        adapter = new NotificationAdapter(list, getApplicationContext(), new NotificationAdapter.Click() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(Activity_Notifications.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        notificationRecycler.setLayoutManager(mLayoutManager);
        notificationRecycler.setAdapter(adapter);

    }

    private void init() {


//        NotificationModel model = new NotificationModel(R.drawable.my_profile, "Lorem ipsum dolor sit er elite lamet,consecetetaur.", "28/12/2080");
//        list.add(model);
//        list.add(model);
//        list.add(model);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}