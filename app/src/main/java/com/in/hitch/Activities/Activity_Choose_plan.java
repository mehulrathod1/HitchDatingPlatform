package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.Token;
import static com.in.hitch.Utils.Glob.User_Id;
import static com.in.hitch.Utils.Glob.base_url;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.in.hitch.Adapter.UpgradeMembershipAdapter;
import com.in.hitch.Model.CommonModel;
import com.in.hitch.Model.MembershipPlaneModel;
import com.in.hitch.Model.PurchasePlaneModel;
import com.in.hitch.Model.TransactionModel;
import com.in.hitch.R;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Choose_plan extends AppCompatActivity {

    ImageView membershipBack;
    Button purchase;
    ProgressDialog progressDialog;


    ViewPager viewPager;
    ArrayList<MembershipPlaneModel.PlaneData> list = new ArrayList<>();
    LinearLayout layout_dot;
    TextView[] dot;
    String position = "8";

    String TAG = "Activity_Choose_plan";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_plan);
        getSupportActionBar().hide();


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        layout_dot = (LinearLayout) findViewById(R.id.layout_dot);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


            }

            @Override
            public void onPageSelected(int i) {
                addDot(i);
                position = list.get(i).getPlan_id();

            }

            @Override
            public void onPageScrollStateChanged(int i) {


            }
        });

        init();
        planeData();


    }

    private void init() {


        membershipBack = findViewById(R.id.membershipBack);
        purchase = findViewById(R.id.purchase);

        progressDialog = new ProgressDialog(Activity_Choose_plan.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


        membershipBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewPager.setCurrentItem(getItem(+1), true);

                purchasePlane(User_Id, position);

            }
        });

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void addDot(int page_position) {
        dot = new TextView[list.size()];
        layout_dot.removeAllViews();

        for (int i = 0; i < dot.length; i++) {

            dot[i] = new TextView(this);
            dot[i].setText(Html.fromHtml("&#9673;"));
            dot[i].setTextSize(35);
            dot[i].setTextColor(getResources().getColor(R.color.black));
            layout_dot.addView(dot[i]);
        }
        //active dot
        dot[page_position].setTextColor(getResources().getColor(R.color.md_pink_300));
    }


    public void planeData() {

        Api call = AppConfig.getClient(base_url).create(Api.class);

        progressDialog.show();

        call.getPlans(Token).enqueue(new Callback<MembershipPlaneModel>() {
            @Override
            public void onResponse(Call<MembershipPlaneModel> call, Response<MembershipPlaneModel> response) {

                MembershipPlaneModel model = response.body();

                List<MembershipPlaneModel.PlaneData> data = model.getPlaneData();

                for (int i = 0; i < data.size(); i++) {

                    MembershipPlaneModel.PlaneData planeDetail = data.get(i);


                    MembershipPlaneModel.PlaneData planeData = new MembershipPlaneModel.PlaneData(planeDetail.getPlan_id(),
                            planeDetail.getPlan_name(),
                            planeDetail.getDuration_in_days(),
                            planeDetail.getAmount(),
                            planeDetail.getUnhitch_rewind_your_swipe(),
                            planeDetail.getTravel_to_hitch_around_world(),
                            planeDetail.getUnlimited_right_swipes(),
                            planeDetail.getNo_sds(),
                            planeDetail.getHide_distance(),
                            planeDetail.getHide_age(),
                            planeDetail.getAppear_in_top_hitches(),
                            planeDetail.getKnow_in_advance_who_likes_you(),
                            planeDetail.getDirect_message_without_match());

                    list.add(planeData);
                }


                UpgradeMembershipAdapter pagerAdapter = new UpgradeMembershipAdapter(getApplicationContext(), list);
                viewPager.setAdapter(pagerAdapter);
                viewPager.setPageMargin(20);
                addDot(0);
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<MembershipPlaneModel> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
    }

    public void purchasePlane(String user_id, String plane_id) {


        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.purchasePlans(Token, user_id, plane_id).enqueue(new Callback<PurchasePlaneModel>() {
            @Override
            public void onResponse(Call<PurchasePlaneModel> call, Response<PurchasePlaneModel> response) {


                PurchasePlaneModel model = response.body();

//                Toast.makeText(getApplicationContext(), "" + model.getStatus(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onResponse: " + model.getMessage().getUrl_razorpay());

                progressDialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), RazorPay.class);
                intent.putExtra("url", model.getMessage().getUrl_razorpay());
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<PurchasePlaneModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}