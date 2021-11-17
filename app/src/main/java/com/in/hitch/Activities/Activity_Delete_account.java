package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.Token;
import static com.in.hitch.Utils.Glob.base_url;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.in.hitch.Model.CommonModel;
import com.in.hitch.R;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Delete_account extends AppCompatActivity {
    ImageView deleteBack;
    Button deleteAccount;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_account);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        setTitle("Delete Account");
        getSupportActionBar().hide();
        init();


    }

    private void init() {

        deleteBack = findViewById(R.id.deleteBack);
        deleteAccount = findViewById(R.id.deleteAccount);


        progressDialog = new ProgressDialog(Activity_Delete_account.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount("70");
            }
        });
        deleteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_profile_menu.class);
                startActivity(intent);
            }
        });
    }

    public void deleteAccount(String user_id) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();
        call.deleteAccount(Token, user_id).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();

                Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}