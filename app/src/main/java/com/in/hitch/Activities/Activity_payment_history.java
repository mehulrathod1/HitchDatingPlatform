package com.in.hitch.Activities;

import static com.in.hitch.Utils.Glob.Token;
import static com.in.hitch.Utils.Glob.User_Id;
import static com.in.hitch.Utils.Glob.base_url;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.in.hitch.Adapter.TransactionAdapter;
import com.in.hitch.Model.TransactionModel;
import com.in.hitch.R;
import com.in.hitch.retrofit.Api;
import com.in.hitch.retrofit.AppConfig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_payment_history extends AppCompatActivity {

    ImageView paymentHistoryBack, search, cancelSearch;
    TextView searchText;
    RecyclerView recyclerView;
    TransactionAdapter adapter;
    List<TransactionModel.TransactionHistory> list = new ArrayList<>();

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_history);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        setTitle("Payment History");
        getSupportActionBar().hide();

        init();

        paymentHistory("49", "");

    }


    private void init() {

        paymentHistoryBack = findViewById(R.id.paymentHistoryBack);
        search = findViewById(R.id.search);
        cancelSearch = findViewById(R.id.cancelSearch);
        searchText = findViewById(R.id.searchText);
        recyclerView = findViewById(R.id.Payment_recycler);

        progressDialog = new ProgressDialog(Activity_payment_history.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");

        paymentHistoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_profile_menu.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (searchText.getText().toString().equals(null)) {

                } else {
                    String text = searchText.getText().toString();

                    search.setVisibility(View.GONE);
                    cancelSearch.setVisibility(View.VISIBLE);
                    paymentHistory("49", text);

                }

            }
        });
        cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelSearch.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                searchText.setText("");
                paymentHistory("49", "");

            }
        });
    }


    public void paymentHistory(String userid, String search_keyword) {
        Api call = AppConfig.getClient(base_url).create(Api.class);
        progressDialog.show();

        call.paymentHistory(Token, userid, search_keyword).enqueue(new Callback<TransactionModel>() {
            @Override
            public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {


                list.clear();
                TransactionModel model = response.body();
                List<TransactionModel.TransactionHistory> data = model.getPaymentHistoryData();

                for (int i = 0; i < data.size(); i++) {

                    TransactionModel.TransactionHistory historyData = data.get(i);
                    TransactionModel.TransactionHistory historyItem = new TransactionModel.TransactionHistory(
                            historyData.getTransaction_id(), historyData.getDescription(),
                            historyData.getAmount(),
                            historyData.getDate()
                    );

                    list.add(historyItem);
                }

                recycler();
                if (model.getStatus().equals("false")) {
                    Toast.makeText(getApplicationContext(), "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<TransactionModel> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
    }

    public void recycler() {


        adapter = new TransactionAdapter(list, getApplicationContext(), new TransactionAdapter.Click() {
            @Override
            public void onItemClick(int position) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}