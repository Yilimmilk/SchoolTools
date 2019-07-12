package com.icezx.tools.Logical;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.icezx.tools.Utils.NavigationSelectUtil;
import com.icezx.tools.R;

public class LogicalActivity extends NavigationSelectUtil {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_nav_view);

        View MainActivityView=findViewById(R.id.default_include_mainactivity);
        View CalculatorView=findViewById(R.id.default_include_calculator);
        View HomeWorkView=findViewById(R.id.default_include_homework);
        View LogicalView=findViewById(R.id.default_include_logical);
        View RadixView=findViewById(R.id.default_include_radix);
        View ScheduleView=findViewById(R.id.default_include_schedule);

        MainActivityView.setVisibility(View.GONE);
        CalculatorView.setVisibility(View.GONE);
        HomeWorkView.setVisibility(View.GONE);
        LogicalView.setVisibility(View.VISIBLE);
        RadixView.setVisibility(View.GONE);
        ScheduleView.setVisibility(View.GONE);

        setTitle("进制转换");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeToolbar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
