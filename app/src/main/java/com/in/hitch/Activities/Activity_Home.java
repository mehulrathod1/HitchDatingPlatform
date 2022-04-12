package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.Filter_Interested_Id;
import static com.in.hitch.Utils.Glob.Filter_Religion_Id;
import static com.in.hitch.Utils.Glob.Filter_Sexual_Id;
import static com.in.hitch.Utils.Glob.Token;
import static com.in.hitch.Utils.Glob.User_Id;
import static com.in.hitch.Utils.Glob.base_url;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.in.hitch.Adapter.CardStackAdapter;
import com.in.hitch.Model.CommonModel;
import com.in.hitch.Model.GetUserFilterModel;
import com.in.hitch.Model.ItemModel;
import com.in.hitch.Model.Profile;
import com.in.hitch.Model.ProfileCardModel;
import com.in.hitch.R;
import com.in.hitch.Utils.Glob;
import com.in.hitch.Utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipeDirectionalView;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CardView cv_filters, cv_notifications;
    FrameLayout source_frame, main_frameLayout;
    SwipePlaceHolderView mSwipeView;
    private AdView adView;
    FrameLayout frameLayout;
    Spinner sp_sexual_orientation, sp_religion, sp_interested_in;
    List<String> religion_list, interested_in_list, sexual_orientation_list;
    ArrayAdapter<String> interested_in_adapter, religion_adapter, sexual_orientation_adapter;
    RelativeLayout rl_filter;
    Button btn_reset, btn_apply;
    AlertDialog.Builder alertDialog;
    AlertDialog alert;
    Button btn_send_message, btn_continue_hitches;
    ImageView close, closeDialog;
    TinderCard card;
    SwipeDirectionalView SwipeView;
    TextView MinAge, MaxAge, MinDistance, MaxDistance;
    List<String> sexual_id_list = new ArrayList<>();
    ProgressDialog progressDialog;

    List<ProfileCardModel.ProfileCard> profileCardModelList = new ArrayList<>();

    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    CardStackView cardStackView;


    CrystalRangeSeekbar seekbar, DistanceSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        init();
        getProfileCard(Token, "48");


//        getUserFilter(User_Id);
    }

    private void init() {


        mSwipeView = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        cv_filters = findViewById(R.id.account_settinngs);
        cv_notifications = findViewById(R.id.notifications);
        source_frame = findViewById(R.id.sourceFrame);
        source_frame = findViewById(R.id.sourceFrame);
        main_frameLayout = findViewById(R.id.frame_layout);
        frameLayout = findViewById(R.id.adaptive_adview);

        final BottomSheetDialog dialog = new BottomSheetDialog(Activity_Home.this);
        dialog.setContentView(R.layout.activity_filters);

        seekbar = new CrystalRangeSeekbar(getApplicationContext());
        DistanceSeekbar = new CrystalRangeSeekbar(getApplicationContext());
        DistanceSeekbar = (CrystalRangeSeekbar) dialog.findViewById(R.id.DistanceSeekbar);
        seekbar = (CrystalRangeSeekbar) dialog.findViewById(R.id.mute_seekbar);

        MinDistance = dialog.findViewById(R.id.MinDistance);
        MaxDistance = dialog.findViewById(R.id.MaxDistance);
        MinAge = dialog.findViewById(R.id.minvalue);
        MaxAge = dialog.findViewById(R.id.maxvalue);
        rl_filter = dialog.findViewById(R.id.main_layout);
        btn_reset = dialog.findViewById(R.id.reset);
        btn_apply = dialog.findViewById(R.id.apply);

        progressDialog = new ProgressDialog(Activity_Home.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


        cardStackView = findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(this);
        manager.setStackFrom(StackFrom.None);

        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());


        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("alll", "onClick: " + Filter_Religion_Id + Filter_Interested_Id + Filter_Sexual_Id
                        + MinAge.getText().toString() + MaxAge.getText().toString() +
                        MinDistance.getText().toString() + MaxDistance.getText().toString());

                Log.e("TAG", "onClick: " + sexual_id_list.get(0));
                updateFilter(User_Id, Filter_Interested_Id, Filter_Religion_Id, sexual_id_list,
                        MinAge.getText().toString(), MaxAge.getText().toString(),
                        MinDistance.getText().toString(), MaxDistance.getText().toString());
                dialog.dismiss();
            }
        });


        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        seekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue));
                Log.d("CRS=>", String.valueOf(maxValue));
                MinAge.setText(String.valueOf(minValue));
                MaxAge.setText(String.valueOf(maxValue));

            }
        });

        seekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {

            }
        });

        DistanceSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue));
                Log.d("CRS=>", String.valueOf(maxValue));
                MinDistance.setText(String.valueOf(minValue));
                MaxDistance.setText(String.valueOf(maxValue));
            }
        });

        sp_sexual_orientation = dialog.findViewById(R.id.sp_sexual_orientation);
        sp_interested_in = dialog.findViewById(R.id.sp_interested_in);
        sp_religion = dialog.findViewById(R.id.sp_religion);

        closeDialog = dialog.findViewById(R.id.closeDialog);

        interested_in_list = new ArrayList<>();
        sexual_orientation_list = new ArrayList<>();
        religion_list = new ArrayList<>();


        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:

                        break;
                    case R.id.matches:

                        Intent i = new Intent(Activity_Home.this, Activity_top_hitches.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0, 0);

                        break;
                    case R.id.chats:

                        i = new Intent(Activity_Home.this, Activity_My_chats.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0, 0);

                        break;
                    case R.id.account:
                        i = new Intent(Activity_Home.this, Activity_profile_menu.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0, 0);

                        break;
                }
                return true;
            }
        });

        cv_filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                get_sexual_option();
                get_religion_option();
                get_interest_option();

                Log.e("id", "onClick: " + Filter_Religion_Id + Filter_Interested_Id + Filter_Sexual_Id);
                dialog.show();

            }
        });
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cv_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Activity_Home.this, Activity_Notifications.class);
                startActivity(i);

            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        mSwipeView.getBuilder()
                .setDisplayViewCount(2)
                .setIsUndoEnabled(true)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth((int) (0.96 * width))
                        .setViewHeight((int) (0.75 * height))
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.swipe_out_msg_view));


        for (Profile profile : Utils.loadProfiles(this.getApplicationContext())) {
            mSwipeView.addView(new TinderCard(getApplicationContext(), profile, mSwipeView, new TinderCard.OnItemClickListener() {
                @Override
                public void onItemClick() {

                    Intent i = new Intent(Activity_Home.this, Activity_Profile_details.class);
                    startActivity(i);

                }
            }));
        }
        mSwipeView.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {

                if (count > 2) {
                    alertDialog = new AlertDialog.Builder(Activity_Home.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialoglayout = inflater.inflate(R.layout.activity_match_screen, null);
                    btn_send_message = dialoglayout.findViewById(R.id.send_message);
                    close = dialoglayout.findViewById(R.id.closeAdd);
                    btn_continue_hitches = dialoglayout.findViewById(R.id.continue_hitching);
                    alertDialog.setView(dialoglayout);
                    alert = alertDialog.create();
                    alert.show();

                    btn_continue_hitches.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            alert.dismiss();
                        }
                    });
                    btn_send_message.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i = new Intent(Activity_Home.this, Activity_chat_dashboard.class);
                            startActivity(i);
                        }
                    });
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                        }
                    });
                }

            }
        });

        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.banner_ad_unit));
        frameLayout = findViewById(R.id.adaptive_adview);
        // frameLayout.addView(adView);
        //loadBanner();
    }

    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest =
                new AdRequest.Builder().build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);


        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    private void getUpdateFilter(String user_id, String interest_id,
                                 String religion_id, String sexual_id,
                                 String start_age, String end_age,
                                 String start_distance, String end_distance) {

        String url = "https://www.hitch.notionprojects.tech/api/update_filter.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("onResponse", "onResponse: " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
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
                params.put("user_id", user_id);
                params.put("interest_id", interest_id);
                params.put("religion_id", religion_id);
                params.put("sexual_id", sexual_id);
                params.put("start_age", start_age);
                params.put("end_age", end_age);
                params.put("start_distance", start_distance);
                params.put("end_distance", end_distance);
                return params;
            }
        };
        queue.add(request);

    }

    public void updateFilter(String user_id, String interest_id,
                             String religion_id, List<String> sexual_id,
                             String start_age, String end_age,
                             String start_distance, String end_distance) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();


        call.UpdateFilter(Token, user_id, interest_id, religion_id, sexual_id, start_age, end_age, start_distance, end_distance).enqueue(new Callback<CommonModel>() {

            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel model = response.body();
                Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getUserFilter(String user_id) {

        Api call = AppConfig.getClient(base_url).create(Api.class);


        call.GetUserFilter(Token, user_id).enqueue(new Callback<GetUserFilterModel>() {
            @Override
            public void onResponse(Call<GetUserFilterModel> call, Response<GetUserFilterModel> response) {

                GetUserFilterModel model = response.body();
                List<GetUserFilterModel.GetFilter> list = model.getGetFilterList();
                GetUserFilterModel.GetFilter data = list.get(0);


                MinAge.setText(data.getStart_age());
                MaxAge.setText(data.getEnd_age());

                MinDistance.setText(data.getStart_distance());
                MaxDistance.setText(data.getEnd_distance());

            }

            @Override
            public void onFailure(Call<GetUserFilterModel> call, Throwable t) {

                Log.e("model", "onResponse: " + t.getMessage());
                progressDialog.dismiss();

            }
        });

    }

    private void get_sexual_option() {

        String url = "https://www.hitch.notionprojects.tech/api/get_sexual_option.php";
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

                        sexual_orientation_list.add(name);

                        sexual_orientation_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview, sexual_orientation_list);
                        sexual_orientation_adapter.setDropDownViewResource(R.layout.dropdown_item);
                        sp_sexual_orientation.setAdapter(sexual_orientation_adapter);

                        progressDialog.dismiss();

                        sp_sexual_orientation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                try {
                                    JSONObject Object = jsonArray.getJSONObject(position);
                                    String sp_sexual_orientation = Object.getString("id");
                                    Filter_Sexual_Id = sp_sexual_orientation;
                                    sexual_id_list.add(Filter_Sexual_Id);
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

    private void get_religion_option() {

        String url = "https://www.hitch.notionprojects.tech/api/get_religion_option.php";
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
                        Log.e("name", "relignon: " + name);

                        religion_list.add(name);
                        religion_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview, religion_list);
                        religion_adapter.setDropDownViewResource(R.layout.dropdown_item);
                        sp_religion.setAdapter(religion_adapter);

                        sp_religion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                try {
                                    JSONObject Object = jsonArray.getJSONObject(position);
                                    String religion_id = Object.getString("id");
                                    Filter_Religion_Id = religion_id;


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

    private void get_interest_option() {
        String url = "https://www.hitch.notionprojects.tech/api/get_interest_option.php";
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
                        String id = Object.getString("id");

                        Log.e("name", "interest: " + id + name);
                        interested_in_list.add(name);

                        interested_in_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview, interested_in_list);
                        interested_in_adapter.setDropDownViewResource(R.layout.dropdown_item);
                        sp_interested_in.setAdapter(interested_in_adapter);


                        sp_interested_in.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                try {
                                    JSONObject Object = jsonArray.getJSONObject(position);
                                    String interested_id = Object.getString("id");
                                    Filter_Interested_Id = interested_id;
                                    Log.e("interested_id", "onItemSelected: " + interested_id);

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

    private void getProfileCard(String token, String userId) {

        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.getProfileCard(token, userId).enqueue(new Callback<ProfileCardModel>() {
            @Override
            public void onResponse(Call<ProfileCardModel> call, Response<ProfileCardModel> response) {

                ProfileCardModel profileCardModel = response.body();

                List<ProfileCardModel.ProfileCard> dataList = profileCardModel.getProfileCardList();

                for (int i = 0; i < dataList.size(); i++) {

                    ProfileCardModel.ProfileCard model = dataList.get(i);

                    ProfileCardModel.ProfileCard data = new ProfileCardModel.ProfileCard(
                            model.getId(),
                            model.getUser_name(),
                            model.getProfile(),
                            model.getAge(),
                            model.getJob_title(),
                            model.getLiked(),
                            model.getFavorite(),
                            model.getSuperLike()
                    );

                    profileCardModelList.add(data);

                    adapter = new CardStackAdapter(profileCardModelList, getApplicationContext(), new CardStackAdapter.Click() {
                        @Override
                        public void onClickItem(int position) {
                            Intent i = new Intent(Activity_Home.this, Activity_Profile_details.class);
                            startActivity(i);
                        }

                        @Override
                        public void onClickReload(int position) {

                            RewindAnimationSetting setting11 = new RewindAnimationSetting.Builder()
                                    .setDirection(Direction.Left)
                                    .setDuration(Duration.Normal.duration)
                                    .setInterpolator(new AccelerateInterpolator())
                                    .build();

                            manager.setRewindAnimationSetting(setting11);
                            manager.setCanScrollVertical(true);
                            manager.setCanScrollHorizontal(true);
                            manager.setDirections(Direction.FREEDOM);
                            manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
                            manager.setOverlayInterpolator(new AccelerateInterpolator());
                            manager.setStackFrom(StackFrom.None);
                            cardStackView.rewind();

                        }

                        @Override
                        public void onClickNope(int position) {

                            SwipeAnimationSetting setting11 = new SwipeAnimationSetting.Builder()
                                    .setDirection(Direction.Left)
                                    .setDuration(Duration.Normal.duration)
                                    .setInterpolator(new AccelerateInterpolator())
                                    .build();

                            manager.setSwipeAnimationSetting(setting11);
                            manager.setCanScrollVertical(true);
                            manager.setCanScrollHorizontal(true);
                            manager.setDirections(Direction.FREEDOM);
                            manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
                            manager.setOverlayInterpolator(new AccelerateInterpolator());
                            manager.setStackFrom(StackFrom.None);
                            cardStackView.swipe();
                        }

                        @Override
                        public void onClickLike(int position) {


                            SwipeAnimationSetting setting11 = new SwipeAnimationSetting.Builder()
                                    .setDirection(Direction.Right)
                                    .setDuration(Duration.Normal.duration)
                                    .setInterpolator(new AccelerateInterpolator())
                                    .build();

                            manager.setSwipeAnimationSetting(setting11);
                            manager.setCanScrollVertical(true);
                            manager.setCanScrollHorizontal(true);
                            manager.setDirections(Direction.FREEDOM);
                            manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
                            manager.setOverlayInterpolator(new AccelerateInterpolator());
                            manager.setStackFrom(StackFrom.None);
                            cardStackView.swipe();

                            addLike(Token, "48", profileCardModelList.get(position).getId());
                        }

                        @Override
                        public void onClickSuperLike(int position) {


                            SwipeAnimationSetting setting11 = new SwipeAnimationSetting.Builder()
                                    .setDirection(Direction.Top)
                                    .setDuration(Duration.Normal.duration)
                                    .setInterpolator(new AccelerateInterpolator())
                                    .build();

                            manager.setSwipeAnimationSetting(setting11);
                            manager.setCanScrollVertical(true);
                            manager.setCanScrollHorizontal(true);
                            manager.setDirections(Direction.FREEDOM);
                            manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
                            manager.setOverlayInterpolator(new AccelerateInterpolator());
                            manager.setStackFrom(StackFrom.None);
                            cardStackView.swipe();

                            addSupperLike(Token, "48", profileCardModelList.get(position).getId());
                        }

                        @Override
                        public void onClickFavourite(int position) {


                            addToFavourite(Token, "48", profileCardModelList.get(position).getId());

                        }
                    });


                    cardStackView.setLayoutManager(manager);
                    cardStackView.setAdapter(adapter);
                    cardStackView.setItemAnimator(new DefaultItemAnimator());

                    progressDialog.dismiss();

                    Log.e("profileCardModelList", "onResponse: " + model.getAge() + profileCardModelList.size());

                }

            }

            @Override
            public void onFailure(Call<ProfileCardModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("profileCardModelList", "onResponse: " + t.getMessage());
            }
        });

    }

    private void addLike(String token, String login_user_id, String like_user_id) {

        Api call = AppConfig.getClient(base_url).create(Api.class);

        call.addLike(token, login_user_id, like_user_id).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();
                Toast.makeText(Activity_Home.this, "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });

    }

    private void addSupperLike(String token, String login_user_id, String supper_like_user_id) {

        Api call = AppConfig.getClient(base_url).create(Api.class);

        call.addSupperLike(token, login_user_id, supper_like_user_id).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {


                CommonModel commonModel = response.body();
                Toast.makeText(Activity_Home.this, "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });

    }


    private void addToFavourite(String token, String login_user_id, String favorite_user_id) {

        Api call = AppConfig.getClient(base_url).create(Api.class);

        call.addToFavourite(token, login_user_id, favorite_user_id).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();
                Toast.makeText(Activity_Home.this, "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}