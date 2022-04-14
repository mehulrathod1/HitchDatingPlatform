package com.in.hitch.Activities;

import static android.view.KeyEvent.KEYCODE_BACK;
import static com.in.hitch.Utils.Glob.Mobile_No;
import static com.in.hitch.Utils.Glob.Token;
import static com.in.hitch.Utils.Glob.User_Id;
import static com.in.hitch.Utils.Glob.base_url;



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
import com.in.hitch.Model.CommonModel;
import com.in.hitch.Model.VerifyOtpModel;
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
//                sendOtp(User_Id, Otp);

                verifyOtp(Token, User_Id, Otp);

            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("userid", "onClick: " + User_Id);
                resendOtp(Token, User_Id);
            }
        });


        textChange();
    }




    private void verifyOtp(String token, String user_id, String otp) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.verifyOtp(token, user_id, otp).enqueue(new Callback<VerifyOtpModel>() {
            @Override
            public void onResponse(Call<VerifyOtpModel> call, Response<VerifyOtpModel> response) {

                VerifyOtpModel verifyOtpModel = response.body();

                VerifyOtpModel.VerifyData model = verifyOtpModel.getVerifyData();

                if (response.isSuccessful()){
                if (model.getIs_mobile_verify().equals("y") && model.getIs_completed_profile().equals("n")) {
                    Toast.makeText(getApplicationContext(), "" + verifyOtpModel.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent i = new Intent(Activity_Verification.this, Activity_Profile_completion.class);
                    startActivity(i);
                    finish();

                }
                if (model.getIs_mobile_verify().equals("y") && model.getIs_completed_profile().equals("y")) {
                    Toast.makeText(getApplicationContext(), "" + verifyOtpModel.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                    SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("key", User_Id);
                    editor.commit();

                    Intent i = new Intent(Activity_Verification.this, Activity_Home.class);
                    startActivity(i);
                    finish();
                }
                }
                else {

                    progressDialog.dismiss();

                }


            }

            @Override
            public void onFailure(Call<VerifyOtpModel> call, Throwable t) {


                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(), "" +"OTP invalid", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resendOtp(String token, String userId) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.resendOtp(token, userId).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();

                Toast.makeText(Activity_Verification.this, "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
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