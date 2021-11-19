package com.in.hitch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.in.hitch.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_profile_menu extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView tv_manage_profile, tv_payment_history, tv_my_metches, tv_upgrade_membership, tv_logout, tv_account_delete;
    PopupWindow dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_tab_menu);
        getSupportActionBar().hide();
        init();

    }

    private void init() {


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        tv_manage_profile = findViewById(R.id.manage_profile);
        tv_payment_history = findViewById(R.id.payment_history);
        tv_my_metches = findViewById(R.id.my_matches);
        tv_logout = findViewById(R.id.logout);
        tv_upgrade_membership = findViewById(R.id.upgrade_membership);
        tv_account_delete = findViewById(R.id.delete_account);

        tv_manage_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Activity_profile_menu.this, Activity_My_profile.class);
                startActivity(i);
            }
        });
        tv_upgrade_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Activity_profile_menu.this, Activity_Choose_plan.class);
                startActivity(i);
            }
        });
        tv_payment_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Activity_profile_menu.this, Activity_payment_history.class);
                startActivity(i);
            }
        });

        tv_my_metches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Activity_profile_menu.this, Activity_My_Matches.class);
                startActivity(i);
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog = new BottomSheetDialog(Activity_profile_menu.this);
                dialog.setContentView(R.layout.logout_pou_up);
                dialog.show();
            }
        });

        tv_account_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Activity_profile_menu.this, Activity_Delete_account.class);
                startActivity(i);
            }
        });

        bottomNavigationView.getMenu().findItem(R.id.account).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        Intent i = new Intent(Activity_profile_menu.this, Activity_Home.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.matches:
                        i = new Intent(Activity_profile_menu.this, Activity_top_hitches.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.chats:
                        i = new Intent(Activity_profile_menu.this, Activity_My_chats.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.account:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i = new Intent(Activity_profile_menu.this, Activity_Home.class);
        startActivity(i);
    }
}