package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.Token;
import static com.in.hitch.Utils.Glob.Update_Gender_Id;
import static com.in.hitch.Utils.Glob.Update_Sexual_Id;
import static com.in.hitch.Utils.Glob.User_Id;
import static com.in.hitch.Utils.Glob.base_url;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.hitch.Model.CommonModel;
import com.in.hitch.Model.MyMatchesModel;
import com.in.hitch.Model.MyProfileModel;
import com.in.hitch.R;
import com.in.hitch.Utils.Glob;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_My_profile extends AppCompatActivity implements LocationListener {

    Spinner sp_sexual_orientation, sp_gender;
    List<String> gender_list, sexual_orientation_list;
    ArrayAdapter<String> gender_adapter, sexual_orientation_adapter;
    CardView cv_add_photos;
    ImageView editProfileBack;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    double latitude, longitude;
    EditText edt_job_title, edt_city_name, edt_company_name, edt_school_name;
    Button btnUpdateProfile;
    ProgressDialog progressDialog;
    List<String> sexualIdList = new ArrayList<>();

    Switch ageSwitch, distanceSwitch;
    String showMyAge, showMyDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        setTitle("Profile");


        getSupportActionBar().hide();

        editProfileBack = findViewById(R.id.editProfileBack);
        edt_job_title = findViewById(R.id.edt_job_title);
        edt_city_name = findViewById(R.id.edt_city_name);
        edt_company_name = findViewById(R.id.edt_company_name);
        edt_school_name = findViewById(R.id.edt_school_name);

        init();
        getMyProfile(Token, "48");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    private void init() {


        sp_sexual_orientation = findViewById(R.id.sp_sexual_orientation);
        sp_gender = findViewById(R.id.sp_gender);
        cv_add_photos = findViewById(R.id.cv_add_photos);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        distanceSwitch = findViewById(R.id.distanceSwitch);
        ageSwitch = findViewById(R.id.ageSwitch);


        gender_list = new ArrayList<>();
        sexual_orientation_list = new ArrayList<>();


        progressDialog = new ProgressDialog(Activity_My_profile.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


        get_sexual_option();
        get_Gender_Option();
        cv_add_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Activity_My_profile.this, Activity_Add_photos.class);
                startActivity(i);
            }
        });

        editProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                dt_job_title,edt_city_name,edt_company_name,edt_school_name;


                if (ageSwitch.isChecked()) {
                    showMyAge = "y";

                    Log.e("showMyAge", "onClick: " + showMyAge);
                } else {
                    showMyAge = "n";
                    Log.e("showMyAge", "onClick: " + showMyAge);
                }

                if (distanceSwitch.isChecked()) {
                    showMyDistance = "y";
                    Log.e("showMyAge", "onClick: " + showMyDistance);
                } else {
                    showMyDistance = "n";
                    Log.e("showMyAge", "onClick: " + showMyDistance);
                }


                Log.e("est", "onClick: " + edt_job_title.getText().toString() +
                        edt_company_name.getText().toString() +
                        edt_school_name.getText().toString() +
                        edt_city_name.getText().toString() +
                        Update_Gender_Id + sexualIdList.get(0));

                String StringLatitude, StringLongitude;
                StringLatitude = String.valueOf(latitude);
                StringLongitude = String.valueOf(longitude);

                updateProfile(User_Id, edt_job_title.getText().toString(),
                        edt_company_name.getText().toString(),
                        edt_school_name.getText().toString(),
                        edt_city_name.getText().toString(), StringLatitude, StringLongitude, Update_Gender_Id,
                        sexualIdList, showMyAge, showMyDistance);

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.settings: {
                Intent i = new Intent(Activity_My_profile.this, Activity_Account_settings.class);
                startActivity(i);
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

//            txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

        longitude = location.getLongitude();
        latitude = location.getLatitude();

        Log.e("Location", "onLocationChanged: " + "Latitude:" + latitude + ", Longitude:" + longitude);

        String cityName = null;
        Geocoder gcd = new Geocoder(getBaseContext(),
                Locale.getDefault());
        List<Address> addresses;
        String ss;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location
                    .getLongitude(), 1);
            if (addresses.size() > 0)
                System.out.println(addresses.get(0).getLocality());
            cityName = addresses.get(0).getLocality();
            ss = addresses.get(0).getLocality();
            Log.e("s", "onLocationChanged: " + ss);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = cityName;
        edt_city_name.setText(s);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        Log.d("Latitude", "status");
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

        Log.d("Latitude", "enable");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

        Log.d("Latitude", "disable");
    }

    private void get_sexual_option() {

        String url = "https://www.hitch.notionprojects.tech/api/get_sexual_option.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject Object = jsonArray.getJSONObject(i);

                        String name = Object.getString("name");

                        sexual_orientation_list.add(name);

                        sexual_orientation_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview_for_profile, sexual_orientation_list);
                        sexual_orientation_adapter.setDropDownViewResource(R.layout.update_dropdown);
                        sp_sexual_orientation.setAdapter(sexual_orientation_adapter);


                        sp_sexual_orientation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                try {
                                    JSONObject Object = jsonArray.getJSONObject(position);
                                    String sp_sexual_orientation = Object.getString("id");
                                    Update_Sexual_Id = sp_sexual_orientation;

                                    sexualIdList.add(Update_Sexual_Id);
                                    Log.e("sp_sexual_orientation", "onItemSelected: " + sp_sexual_orientation);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        Log.e("name", "sex: " + name);

                    }

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
                return params;
            }
        };
        queue.add(request);
    }

    private void get_Gender_Option() {

        String url = "https://www.hitch.notionprojects.tech/api/get_gender_option.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject Object = jsonArray.getJSONObject(i);
                        String name = Object.getString("name");
                        String s = String.valueOf(jsonArray.getJSONObject(i));
                        Log.e("name", "onResponse: " + s);
                        gender_list.add(name);
                        gender_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview_for_profile, gender_list);
                        gender_adapter.setDropDownViewResource(R.layout.update_dropdown);
                        sp_gender.setAdapter(gender_adapter);
                        progressDialog.dismiss();

                        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                try {
                                    JSONObject Object = jsonArray.getJSONObject(position);
                                    String gender_id = Object.getString("id");
                                    Update_Gender_Id = gender_id;

                                    Log.e("gender_id", "onItemSelected: " + gender_id);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
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
                return params;
            }
        };
        queue.add(request);
    }

    private void UpdateProfile(String token, String user_id, String job_title,
                               String company_name, String school_name,
                               String current_location, String latitude,
                               String longitude, String gender_id,
                               String sexual_id, String show_my_age,
                               String show_my_distance) {


        String url = "https://www.hitch.notionprojects.tech/api/update_profile.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("xxxx", "onResponse: " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    String mesage = jsonObject.getString("message");
                    Toast.makeText(getApplicationContext(), mesage, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject Object = jsonArray.getJSONObject(i);


                    }
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
                params.put("user_id", "47");
                params.put("job_title", "Developer");
                params.put("company_name", "Notion");
                params.put("school_name", "Highschool");
                params.put("current_location", "Rajkot");
                params.put("latitude", "89555.90");
                params.put("longitude", "54545.45");
                params.put("gender_id", "1");
                params.put("sexual_id", "1");
                params.put("show_my_age", "y");
                params.put("show_my_distance", "n");
                return params;
            }
        };
        queue.add(request);
    }


    public void updateProfile(String user_id, String job_title,
                              String company_name, String school_name,
                              String current_location, String latitude,
                              String longitude, String gender_id,
                              List<String> sexual_id, String show_my_age,
                              String show_my_distance) {
        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.updateProfile(Token, user_id, job_title, company_name, school_name, current_location, latitude,
                longitude, gender_id, sexual_id, show_my_age, show_my_distance).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();

                Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }


    private void getMyProfile(String token, String userId) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();


        call.getMyProfile(token, userId).enqueue(new Callback<MyProfileModel>() {
            @Override
            public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {

                MyProfileModel myMatchesModel = response.body();

                MyProfileModel.ProfileData model = myMatchesModel.getProfileData();

                Log.e("onResponse", "onResponse: " + model.getBirthday());

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MyProfileModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

}