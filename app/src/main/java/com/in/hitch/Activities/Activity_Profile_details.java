package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.Token;
import static com.in.hitch.Utils.Glob.base_url;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.in.hitch.Adapter.SliderPagerAdapter;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.in.hitch.Model.CommonModel;
import com.in.hitch.Model.ProfileCardModel;
import com.in.hitch.Model.ProfileDetailModel;
import com.in.hitch.R;
import com.in.hitch.Utils.Glob;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Profile_details extends AppCompatActivity {

    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    private TextView[] dots;
    int page_position = 0;
    RelativeLayout rl_main;
    TextView reportUser, blockUser, cancel;
    ProgressDialog progressDialog;
    TextView userName, gender, profession, universityName, location, distance;

    ArrayList<ProfileDetailModel.ProfileDetail.ImageList> imageLists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail_new);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        init();
        addBottomDots(0);


        Intent intent = getIntent();

        getProfileDetail(Token, "59", "53");

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == imageLists.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {


            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);


    }

    private void init() {


        userName = findViewById(R.id.userName);
        gender = findViewById(R.id.gender);
        profession = findViewById(R.id.profession);
        universityName = findViewById(R.id.universityName);
        location = findViewById(R.id.location);
        distance = findViewById(R.id.distance);

        vp_slider = (ViewPager) findViewById(R.id.vp_slider);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);



        rl_main = findViewById(R.id.main_layout);

        progressDialog = new ProgressDialog(Activity_Profile_details.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.matching_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_more: {

                final BottomSheetDialog dialog;

                dialog = new BottomSheetDialog(Activity_Profile_details.this);
                dialog.setContentView(R.layout.pop_up_block_report);
                dialog.show();

                reportUser = dialog.findViewById(R.id.report);
                blockUser = dialog.findViewById(R.id.blockUser);
                cancel = dialog.findViewById(R.id.cancel);

                reportUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        reportUser("100", "200", "Test");
                        dialog.dismiss();
                    }
                });
                blockUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        blockUser("100", "200");
                        dialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[imageLists.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#FFFFFF"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#F3BEDC"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void reportUser(String user_id, String report_user_id, String comment) {


        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();
        call.reportUser(Token, user_id, report_user_id, comment).enqueue(new Callback<CommonModel>() {
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

    public void blockUser(String user_id, String report_user_id) {
        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();
        call.blockUser(Token, user_id, report_user_id).enqueue(new Callback<CommonModel>() {
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


    private void getProfileDetail(String token, String user_id, String profile_id) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.getProfileDetail(token, user_id, profile_id).enqueue(new Callback<ProfileDetailModel>() {
            @Override
            public void onResponse(Call<ProfileDetailModel> call, Response<ProfileDetailModel> response) {

                ProfileDetailModel profileDetailMode = response.body();

                ProfileDetailModel.ProfileDetail model = profileDetailMode.getProfileDetail();


                userName.setText(model.getFirst_name() + " , " + model.getAge());
                gender.setText(model.getGender());
                profession.setText(model.getJob_title());
                universityName.setText(model.getSchool_name());
                location.setText(model.getCurrent_location());
                distance.setText(model.getKm_diff());




                List<ProfileDetailModel.ProfileDetail.ImageList> image = model.getImageLists();
                for (int j =0;j<image.size();j++){

                    ProfileDetailModel.ProfileDetail.ImageList data = image.get(j);

                    ProfileDetailModel.ProfileDetail.ImageList a = new ProfileDetailModel.ProfileDetail.ImageList(
                            data.getImage_name()
                    );

                    imageLists.add(a);
                    Log.e("imageLists", "onResponse: "+a.getImage_name());
                }



                sliderPagerAdapter = new SliderPagerAdapter(Activity_Profile_details.this, imageLists);
                vp_slider.setAdapter(sliderPagerAdapter);

                vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        addBottomDots(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProfileDetailModel> call, Throwable t) {

                progressDialog.dismiss();
            }
        });

    }
}
