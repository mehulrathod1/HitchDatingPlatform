package com.in.hitch.Activities;

import static android.view.KeyEvent.KEYCODE_BACK;
import static com.in.hitch.Utils.Glob.Mobile_No;
import static com.in.hitch.Utils.Glob.User_Id;
import static com.in.hitch.Utils.Glob.resend_otp_url;
import static com.in.hitch.Utils.Glob.verify_otp_url;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.hitch.R;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_Verification extends AppCompatActivity {

    Button verify;
    EditText otp1, otp2, otp3, otp4;
    TextView resend, send_otp_no;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();

        sharedPreferences = getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key", User_Id);
        editor.commit();

    }

    private void init() {

        send_otp_no = findViewById(R.id.send_otp_no);
        verify = findViewById(R.id.verify);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        resend = findViewById(R.id.resend);


        send_otp_no.setText("Enter the OTP sent to " + Mobile_No);
        progressDialog = new ProgressDialog(Activity_Verification.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Otp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString();
                Log.e("TAG", "onClick: " + Otp);
                sendOtp(User_Id, Otp);


            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("userid", "onClick: " + User_Id);
                resendOtp(User_Id);
            }
        });


        textChange();
    }

    private void sendOtp(String user_id, String otp) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, verify_otp_url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String asa = jsonObject.getString("message");
                    if (jsonObject.getString("status").equals("false")) {
                        Toast.makeText(getApplicationContext(), "" + asa, Toast.LENGTH_SHORT).show();
                        Log.e("message", "onResponse: " + jsonObject.getString("message"));
                        progressDialog.dismiss();

                    }

                    JSONObject Object = jsonObject.getJSONObject("data");
                    String is_mobile_verfied = Object.getString("is_mobile_verfied");
                    String is_completed_profile = Object.getString("is_completed_profile");
                    Log.e("is_completed_profile", "onResponse: " + is_completed_profile);

                    if (is_mobile_verfied.equals("y") && is_completed_profile.equals("n")) {
                        Toast.makeText(getApplicationContext(), "" + asa, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent i = new Intent(Activity_Verification.this, Activity_Profile_completion.class);
                        startActivity(i);
                        finish();
//
                    }
                    if (is_mobile_verfied.equals("y") && is_completed_profile.equals("y")) {
//                    if (is_completed_profile.equals("y")) {
                        Toast.makeText(getApplicationContext(), "" + asa, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent i = new Intent(Activity_Verification.this, Activity_Home.class);
                        startActivity(i);
                        finish();
                    }

//                    Log.e("verify", "onResponse: " + is_verify_otp + is_completed_profile);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "123456789");
                params.put("user_id", user_id);
                params.put("otp", otp);
                return params;
            }
        };
        queue.add(request);
    }

    private void resendOtp(String userId) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, resend_otp_url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("message", "onResponse: " + jsonObject.getString("status"));
                    String message = jsonObject.getString("message");
                    Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
                    Log.d("message", "onResponse: " + jsonObject.getString("message"));
                    progressDialog.dismiss();

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
                params.put("user_id", userId);
                return params;
            }
        };
        queue.add(request);
    }

    public void textChange() {

        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (otp1.getText().toString().length() == 1) {
                    otp1.clearFocus();
                    otp2.requestFocus();
                    otp2.setCursorVisible(true);
                }

            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (otp2.getText().toString().length() == 1) {
                    otp2.clearFocus();
                    otp3.requestFocus();
                    otp3.setCursorVisible(true);
                }
                if (otp2.getText().toString().length() == 0) {
                    otp2.clearFocus();
                    otp1.requestFocus();
                    otp1.setCursorVisible(true);
                }


            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (otp3.getText().toString().length() == 1) {
                    otp3.clearFocus();
                    otp4.requestFocus();
                    otp4.setCursorVisible(true);
                }
                if (otp3.getText().toString().length() == 0) {
                    otp3.clearFocus();
                    otp2.requestFocus();
                    otp2.setCursorVisible(true);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (otp4.getText().toString().length() == 0) {
                    otp4.clearFocus();
                    otp3.requestFocus();
                    otp3.setCursorVisible(true);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}