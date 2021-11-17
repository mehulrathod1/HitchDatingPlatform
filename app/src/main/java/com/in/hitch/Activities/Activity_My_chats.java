package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.Token;
import static com.in.hitch.Utils.Glob.User_Id;
import static com.in.hitch.Utils.Glob.base_url;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.in.hitch.Adapter.ChatAdapter;
import com.in.hitch.Model.ChatModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.hitch.Model.GetUserImageModel;
import com.in.hitch.R;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_My_chats extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    List<ChatModel.ChatModelData> list = new ArrayList<>();
    RecyclerView recyclerView;
    ChatAdapter adapter;
    public String TAG = "Activity_My_chats";
    ProgressDialog progressDialog;
    TextView searchText;
    ImageView search, cancelSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_items);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        setTitle("Chats");

        getSupportActionBar().hide();
        init();


        getChatList(User_Id, "");


    }


    private void init() {

        searchText = findViewById(R.id.searchText);
        search = findViewById(R.id.search);
        cancelSearch = findViewById(R.id.cancelSearch);

        recyclerView = (RecyclerView) findViewById(R.id.ChatList);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigationn);

        progressDialog = new ProgressDialog(Activity_My_chats.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (searchText.getText().toString().equals(null)) {

                } else {
                    String text = searchText.getText().toString();

                    search.setVisibility(View.GONE);
                    cancelSearch.setVisibility(View.VISIBLE);
                    getChatList(User_Id, text);

                }

            }
        });
        cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelSearch.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                searchText.setText("");
                getChatList(User_Id, "");
            }
        });

        bottomNavigationView.getMenu().findItem(R.id.chats).setChecked(true);

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
    }

    public void getChatList(String user_id, String search_keyword) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();
        call.getChatList(Token, user_id, search_keyword).enqueue(new Callback<ChatModel>() {
            @Override
            public void onResponse(Call<ChatModel> call, Response<ChatModel> response) {

                list.clear();
                ChatModel model = response.body();
                Log.e(TAG, "onResponse: " + model.getMessage());
                List<ChatModel.ChatModelData> data = model.getChatDataList();
                for (int i = 0; i < data.size(); i++) {

                    ChatModel.ChatModelData chatModelData = data.get(i);

                    ChatModel.ChatModelData chatData = new ChatModel.ChatModelData(chatModelData.getChat_id(),
                            chatModelData.getUser_id(), chatModelData.getImage(),
                            chatModelData.getUsername(), chatModelData.getMessage(),
                            chatModelData.getDatetime(), chatModelData.getUnread_msg());

                    list.add(chatData);

                    Log.e(TAG, "onResponse: " + chatData.getMessage());
                }
                setData();

                if (model.getStatus().equals("false")) {
                    Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ChatModel> call, Throwable t) {

            }
        });
    }

    public void setData() {
        adapter = new ChatAdapter(list, getApplicationContext(), new ChatAdapter.Click() {
            @Override
            public void onItemClick(int position) {

                Log.e(TAG, "onItemClick: " + list.get(position).getChat_id() + "---" + list.get(position).getUser_id() + "---" + list.get(position).getImage());

                String chat_id = list.get(position).getChat_id();
                String user_id = list.get(position).getUser_id();
                String user_image = list.get(position).getImage();
                String user_name = list.get(position).getUsername();

                Intent i = new Intent(Activity_My_chats.this, Activity_chat_dashboard.class);
                i.putExtra("chatId", chat_id);
                i.putExtra("userId", user_id);
                i.putExtra("userImage", user_image);
                i.putExtra("userName", user_name);
                startActivity(i);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i = new Intent(Activity_My_chats.this, Activity_Home.class);
        startActivity(i);
    }
}