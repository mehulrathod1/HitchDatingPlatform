package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.base_url;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.hitch.Adapter.MyMatchesAdapter;
import com.in.hitch.LocationRecever.Constants;
import com.in.hitch.LocationRecever.FetchAddressIntentServices;
import com.in.hitch.Model.MyMatchesModel;
import com.in.hitch.R;
import com.in.hitch.Utils.Glob;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Favorite extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    TextView header;
    RecyclerView recyclerView;
    MyMatchesAdapter adapter;
    List<MyMatchesModel.MatchesData> list = new ArrayList<>();

    ProgressDialog progressDialog;

    ImageView myMatchesBack;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    //    ProgressBar progressBar;
    ResultReceiver resultReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        getSupportActionBar().hide();


        init();
        getMyMatches(Glob.Token, "48");

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Activity_Favorite.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }

    }

    private void init() {

        header = findViewById(R.id.header);
        myMatchesBack = findViewById(R.id.myMatchesBack);
        recyclerView = findViewById(R.id.matchesRecycler);
        progressDialog = new ProgressDialog(Activity_Favorite.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


        myMatchesBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();


            }
        });


    }

    private void getMyMatches(String token, String userId) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.getFavorite(token, userId).enqueue(new Callback<MyMatchesModel>() {
            @Override
            public void onResponse(Call<MyMatchesModel> call, Response<MyMatchesModel> response) {

                MyMatchesModel myMatchesModel = response.body();

                List<MyMatchesModel.MatchesData> dataList = myMatchesModel.getMatchesData();

                for (int i = 0; i < dataList.size(); i++) {

                    MyMatchesModel.MatchesData model = dataList.get(i);

                    MyMatchesModel.MatchesData data = new MyMatchesModel.MatchesData(
                            model.getUser_id(),
                            model.getTitle(),
                            model.getLast_name(),
                            model.getAge(),
                            model.getKm_diff(),
                            model.getImage()

                    );

                    list.add(data);
                }

                setMatchesData();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MyMatchesModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void setMatchesData() {

        adapter = new MyMatchesAdapter(list, getApplicationContext(), new MyMatchesAdapter.Click() {
            @Override
            public void onItemClick(int position) {



                String profileId = list.get(position).getUser_id();
                Intent i = new Intent(Activity_Favorite.this, Activity_Profile_details.class);
                i.putExtra("profileId", profileId);
                i.putExtra("flag", "Activity_Favorite");
                startActivity(i);

            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {
//        progressBar.setVisibility(View.VISIBLE);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(Activity_Favorite.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(getApplicationContext())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocIndex = locationResult.getLocations().size() - 1;
                            double lati = locationResult.getLocations().get(latestlocIndex).getLatitude();
                            double longi = locationResult.getLocations().get(latestlocIndex).getLongitude();

//                            textLatLong.setText(String.format("Latitude : %s\n Longitude: %s", lati, longi));

                            Log.e("textLatLong", "onLocationResult: " + String.format("Latitude : %s\n Longitude: %s", lati, longi));

                            Location location = new Location("providerNA");
                            location.setLongitude(longi);
                            location.setLatitude(lati);

                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            List<Address> addresses = null;

                            try {
                                addresses = geocoder.getFromLocation(
                                        location.getLatitude(),
                                        location.getLongitude(),
                                        1);
                            } catch (Exception ioException) {
                                Log.e("Erasdror", "Error in getting address for the location");
                            }

                            if (addresses == null || addresses.size() == 0) {


                            } else {
                                Address address = addresses.get(0);
                                String str_postcode = address.getPostalCode();
                                String str_Country = address.getCountryName();
                                String str_state = address.getAdminArea();
                                String str_district = address.getSubAdminArea();
                                String str_locality = address.getLocality();
                                String str_address = address.getFeatureName();
//                                devliverResultToRecevier(Constants.SUCCESS_RESULT, str_address, str_locality, str_district, str_state, str_Country, str_postcode);
                                Log.e("Erasdror", "Error in getting address for the location" + str_locality);

                            }


                        } else {
//                            progressBar.setVisibility(View.GONE);

                        }
                    }
                }, Looper.getMainLooper());
    }


}