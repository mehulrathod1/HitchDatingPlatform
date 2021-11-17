package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.Mobile_No;
import static com.in.hitch.Utils.Glob.User_Id;
import static com.in.hitch.Utils.Glob.login_url;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.hbb20.CountryCodePicker;
import com.in.hitch.R;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
                    login("+" + sprCode.getSelectedCountryCode(), mobile_no.getText().toString());
//                    login(Token, "+" + sprCode.getSelectedCountryCode(), mobile_no.getText().toString());


                }
            }
        });
    }
//    public void login(String token, String country_code, String mobile_no) {
//
//        Api call = AppConfig.getClient(base_url).create(Api.class);
//        progressDialog.show();
//        call.login(token, country_code, mobile_no).enqueue(new Callback<CommonModel>() {
//            @Override
//            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
//
//                CommonModel commonModel = response.body();
//
//                Log.e("commonModel", "onResponse: " + commonModel.getStatus());
//                Log.e("commonModel", "onResponse: " + commonModel.getMessage());
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<CommonModel> call, Throwable t) {
//
//            }
//        });
//    }

    private void login(String country_code, String mobile_no) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, login_url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject Object = jsonObject.getJSONObject("data");

                    Log.e("user_id", "onResponse: " + Object.getString("user_id"));
                    User_Id = Object.getString("user_id");
                    Log.e("user_id", "onResponse: " + User_Id);
                    Toast.makeText(getApplicationContext(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    Log.e("message", "onResponse: " + jsonObject.getString("message"));
                    progressDialog.dismiss();

                    Intent i = new Intent(Activity_Sign_up.this, Activity_Verification.class);
                    startActivity(i);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "123456789");
                params.put("country_code", country_code);
                params.put("mobile_no", mobile_no);
                return params;
            }
        };
        queue.add(request);
    }
}