package com.in.hitch.Activities;

import static android.media.CamcorderProfile.get;
import static com.in.hitch.Utils.Glob.Gender_Id;
import static com.in.hitch.Utils.Glob.Interested_Id;
import static com.in.hitch.Utils.Glob.Religion_Id;
import static com.in.hitch.Utils.Glob.Sexual_Id;
import static com.in.hitch.Utils.Glob.Token;
import static com.in.hitch.Utils.Glob.User_Id;
import static com.in.hitch.Utils.Glob.base_url;
import static com.in.hitch.Utils.Glob.get_gender_option_url;
import static com.in.hitch.Utils.Glob.get_interest_option_url;
import static com.in.hitch.Utils.Glob.get_religion_option_url;
import static com.in.hitch.Utils.Glob.get_sexual_option_url;
import static com.in.hitch.Utils.Glob.profile_completion_url;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.hitch.Model.CommonModel;
import com.in.hitch.R;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Profile_completion extends AppCompatActivity {

    Button submit;
    List<String> religion_list, gender_list, interest_list, sexual_orientation_list, year_list, month_list, date_list;
    Spinner sp_gender, sp_interest, sp_sexual_orientation, sp_religion, sp_year, sp_month, sp_date;
    ArrayAdapter<String> religion_adapter, gender_adapter, mutual_interest_adapter, sexual_orientation_adapter, year_adapter, month_adapter, date_adapter;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    EditText firstName, lastName, edt_date, Email, edt_day, edt_month, edt_year;
    ProgressDialog progressDialog;

    List<String> sexual_id_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_completion);
        getSupportActionBar().hide();
        init();

        Log.e("User_Id", "onCreate: " + User_Id);


    }

    private void init() {

        submit = findViewById(R.id.submit);
        sp_gender = findViewById(R.id.sp_gender);
        sp_interest = findViewById(R.id.sp_interest);
        sp_sexual_orientation = findViewById(R.id.sp_sexual_orientation);
        edt_date = findViewById(R.id.birth_date);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        Email = findViewById(R.id.Email);
        edt_day = findViewById(R.id.edt_day);
        edt_month = findViewById(R.id.edt_month);
        edt_year = findViewById(R.id.edt_year);
        sp_religion = findViewById(R.id.sp_religion);

        gender_list = new ArrayList<>();
        interest_list = new ArrayList<>();
        sexual_orientation_list = new ArrayList<>();
        religion_list = new ArrayList<>();
        year_list = new ArrayList<>();
        date_list = new ArrayList<>();
        month_list = new ArrayList<>();


        progressDialog = new ProgressDialog(Activity_Profile_completion.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message


        date_list.add("day");
        date_list.add("01");
        date_list.add("02");
        date_list.add("03");
        date_list.add("04");
        date_list.add("05");
        date_list.add("06");
        date_list.add("07");
        date_list.add("08");
        date_list.add("09");
        date_list.add("10");
        date_list.add("11");
        date_list.add("12");
        date_list.add("13");
        date_list.add("14");
        date_list.add("15");


        month_list.add("Month");
        month_list.add("01");
        month_list.add("02");
        month_list.add("03");
        month_list.add("04");
        month_list.add("05");
        month_list.add("06");
        month_list.add("07");
        month_list.add("08");
        month_list.add("09");
        month_list.add("10");
        month_list.add("11");
        month_list.add("12");

        year_list.add("year");
        year_list.add("2001");
        year_list.add("2002");
        year_list.add("2003");
        year_list.add("2004");
        year_list.add("2005");
        year_list.add("2006");
        year_list.add("2007");
        year_list.add("2008");
        year_list.add("2009");
        year_list.add("2010");
        year_list.add("2001");
        year_list.add("2002");
        year_list.add("2003");
        year_list.add("2004");
        year_list.add("2005");
        year_list.add("2006");
        year_list.add("2007");
        year_list.add("2008");
        year_list.add("2009");
        year_list.add("2010");
        year_list.add("2001");
        year_list.add("2002");
        year_list.add("2003");
        year_list.add("2004");
        year_list.add("2005");
        year_list.add("2006");
        year_list.add("2007");
        year_list.add("2008");
        year_list.add("2009");
        year_list.add("2010");

//        year_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview, year_list);
//        year_adapter.setDropDownViewResource(R.layout.dropdown_item);
//        sp_year.setAdapter(year_adapter);
//
//        month_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview, month_list);
//        month_adapter.setDropDownViewResource(R.layout.dropdown_item);
//        sp_month.setAdapter(month_adapter);
//
//        date_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview, date_list);
//        date_adapter.setDropDownViewResource(R.layout.dropdown_item);
//        sp_date.setAdapter(date_adapter);


        get_Gender_Option();
        get_interest_option();
        get_sexual_option();
        get_religion_option();


        edt_day.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_day.getText().toString().length() == 2) {
                    edt_month.requestFocus();
                    edt_month.setCursorVisible(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_month.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_month.getText().toString().length() == 2) {
                    edt_year.requestFocus();
                    edt_year.setCursorVisible(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String birth_date = edt_year.getText().toString() + "-" + edt_month.getText().toString() + "-" + edt_day.getText().toString();

                if (firstName.getText().toString().equals("")) {
                    firstName.setError("Please Enter FirstName");
                } else if (lastName.getText().toString().equals("")) {
                    lastName.setError("Please Enter LastName");
                } else if (Email.getText().toString().equals("")) {
                    Email.setError("Please Enter Email");
                } else {

                    Log.e("alll", "onClick: " + Gender_Id + Interested_Id + Religion_Id + Sexual_Id);
                    Log.e("alll", "onClick: " + firstName.getText().toString());
                    Log.e("alll", "onClick: " + Email.getText().toString());
                    Log.e("alll", "onClick: " + lastName.getText().toString());
                    Log.e("alll", "onClick: " + birth_date);


                    profileCompletion(Token, User_Id, firstName.getText().toString(), lastName.getText().toString(), Email.getText().toString(), birth_date, Gender_Id, Interested_Id, Religion_Id, sexual_id_list);

                }
            }
        });

        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(edt_date);
            }
        });

    }

    private void setDate(EditText editText) {

        calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR); // current year
        int mMonth = calendar.get(Calendar.MONTH); // current month
        int mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day


        // date picker dialog
        datePickerDialog = new DatePickerDialog(Activity_Profile_completion.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        editText.setText(year + "-" + checkDigit((monthOfYear + 1)) + "-" + checkDigit(dayOfMonth));
                    }
                }, mYear, mMonth, mDay);

        calendar.add(Calendar.YEAR, -100); // subtract 2 years from now
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        calendar.add(Calendar.YEAR, 200); // add 4 years to min date to have 2 years after now
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();

    }

    private String checkDigit(int number) {

        return number <= 9 ? "0" + number : String.valueOf(number);
    }


    public void profileCompletion(String token, String user_id, String first_name,
                                  String last_name, String email,
                                  String birthday, String gender_id,
                                  String interest_id, String religion_id, List<String> sexual_id) {
        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.ProfileCompletion(token, user_id, first_name, last_name, email, birthday, gender_id, interest_id, religion_id, (ArrayList<String>) sexual_id).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();
                Log.e("complete", "onResponse: " + commonModel.getStatus());
                Log.e("complete", "onResponse: " + commonModel.getMessage());
                Toast.makeText(getApplicationContext(), "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();

                Intent i = new Intent(Activity_Profile_completion.this, Activity_Home.class);
                startActivity(i);
                finish();

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

            }
        });
    }

    private void profile_Completion(String token, String user_id, String first_name,
                                    String last_name, String email,
                                    String birthday, String gender_id,
                                    String interest_id, String religion_id,
                                    String sexual_id) {


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, profile_completion_url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("xxxx", "onResponse: " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                    Intent i = new Intent(Activity_Profile_completion.this, Activity_Home.class);
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
                params.put("token", token);
                params.put("user_id", user_id);
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("email", email);
                params.put("birthday", birthday);
                params.put("gender_id", gender_id);
                params.put("interest_id", interest_id);
                params.put("religion_id", religion_id);
                params.put("sexual_id", sexual_id);
                return params;
            }
        };
        queue.add(request);
    }


    private void get_Gender_Option() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, get_gender_option_url, new com.android.volley.Response.Listener<String>() {
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

                        gender_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview, gender_list);
                        gender_adapter.setDropDownViewResource(R.layout.dropdown_item);
                        sp_gender.setAdapter(gender_adapter);

                        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                try {

                                    JSONObject Object = jsonArray.getJSONObject(position);
                                    String gender_id = Object.getString("id");
                                    Gender_Id = gender_id;
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

    private void get_interest_option() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, get_interest_option_url, new com.android.volley.Response.Listener<String>() {
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
                        interest_list.add(name);

                        mutual_interest_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview, interest_list);
                        mutual_interest_adapter.setDropDownViewResource(R.layout.dropdown_item);
                        sp_interest.setAdapter(mutual_interest_adapter);


                        sp_interest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                try {
                                    JSONObject Object = jsonArray.getJSONObject(position);
                                    String interested_id = Object.getString("id");
                                    Interested_Id = interested_id;
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

    private void get_religion_option() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, get_religion_option_url, new com.android.volley.Response.Listener<String>() {
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
                                    Religion_Id = religion_id;


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

    private void get_sexual_option() {


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, get_sexual_option_url, new com.android.volley.Response.Listener<String>() {
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


                        sp_sexual_orientation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                try {

                                    JSONObject Object = jsonArray.getJSONObject(position);
                                    String sp_sexual_orientation = Object.getString("id");
                                    Sexual_Id = sp_sexual_orientation;
                                    sexual_id_list.add(Sexual_Id);
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

}
