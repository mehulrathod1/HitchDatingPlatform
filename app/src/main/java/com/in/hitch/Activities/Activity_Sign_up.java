package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.Mobile_No;
import static com.in.hitch.Utils.Glob.User_Id;
import static com.in.hitch.Utils.Glob.base_url;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.hbb20.CountryCodePicker;
import com.in.hitch.Model.CommonModel;
import com.in.hitch.Model.LoginModel;
import com.in.hitch.R;
import com.in.hitch.Utils.Glob;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Sign_up extends AppCompatActivity {

    Button Sign_up;
    private CountryCodePicker sprCode;
    EditText mobile_no;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        getSupportActionBar().hide();

    }

    private void init() {

        Sign_up = findViewById(R.id.sign_up);
        sprCode = findViewById(R.id.sprCode);
        mobile_no = findViewById(R.id.mo_Number);

        progressDialog = new ProgressDialog(Activity_Sign_up.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Regex = "[^\\d]";
                String PhoneDigits = mobile_no.getText().toString().replaceAll(Regex, "");
                if (PhoneDigits.length() != 10) {
                    mobile_no.setError("Please Enter Mobile Number");
                } else {
                    Log.e("login", "onClick: " + "+" + sprCode.getSelectedCountryCode());
                    Mobile_No = mobile_no.getText().toString();
                    login(Glob.Token, "+" + sprCode.getSelectedCountryCode(), mobile_no.getText().toString());
//                    login(Token, "+" + sprCode.getSelectedCountryCode(), mobile_no.getText().toString());


                }
            }
        });
    }

    public void login(String token, String country_code, String mobile_no) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.login(token, country_code, mobile_no).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                LoginModel loginModel = response.body();

                LoginModel.LoginData model = loginModel.getLoginData();


                User_Id = model.getUser_id();
                Toast.makeText(Activity_Sign_up.this, "" + loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("user_id", "onResponse: " + User_Id);

                progressDialog.dismiss();

                Intent i = new Intent(Activity_Sign_up.this, Activity_Verification.class);
                startActivity(i);
                finish();


            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });
    }

}