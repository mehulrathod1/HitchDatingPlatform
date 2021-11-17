package com.in.hitch.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.in.hitch.Model.CommonModel;
import com.in.hitch.R;
import com.in.hitch.Utils.Glob;
import com.in.hitch.Utils.KeyHashes;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button btn_login_with_number, login_with_google;
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 9000;
    private String TAG = "LandingActivity";
    String loginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_verification);
        getSupportActionBar().hide();
        init();

        SharedPreferences prefs = getSharedPreferences("MyPref", 0);
        loginId = prefs.getString("key", "null");
        Log.e("getSharedPreferences", "onCreate: " + loginId);


        KeyHashes.Key(this);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void init() {

        btn_login_with_number = findViewById(R.id.login_with_number);
        login_with_google = findViewById(R.id.login_with_google);
        login_with_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        btn_login_with_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, Activity_Sign_up.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }


    }

    public void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {

            Toast.makeText(getApplicationContext(), "Sign in Complete", Toast.LENGTH_SHORT).show();
            GoogleSignInAccount acct = result.getSignInAccount();

            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();

            Uri PersonNo = acct.getPhotoUrl();

            Log.e(TAG, "personal Name " + personName);
            Log.e(TAG, "personGivenName " + personGivenName);
            Log.e(TAG, "personFamilyName " + personFamilyName);
            Log.e(TAG, "personEmail " + personEmail);
            Log.e(TAG, "personId " + personId);
//                Log.e("personPhoto ", "" + personPhoto);
            Log.e(TAG, "display name: " + acct.getDisplayName());
            Log.e(TAG, "display name: " + acct.getPhotoUrl());

//            socialLogin(personGivenName,personFamilyName,personEmail,"","g");


        } else {

            Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
        }

    }

    public void socialLogin(String first_name, String last_name, String email, String profile, String platform) {

        Api call = AppConfig.getClient(Glob.base_url).create(Api.class);

        call.socialLogin("123456789", first_name, last_name, email, profile, platform).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {
                CommonModel model = response.body();
                Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Activity_Home.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStart() {

        if (loginId.equals("null")) {
        } else {
            Glob.User_Id = loginId;
            Intent i = new Intent(MainActivity.this, Activity_Home.class);
            startActivity(i);
            finish();
        }
        super.onStart();
    }
}