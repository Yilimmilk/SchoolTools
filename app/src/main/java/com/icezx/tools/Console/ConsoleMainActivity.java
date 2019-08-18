package com.icezx.tools.Console;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.icezx.tools.R;
import com.icezx.tools.Utils.NavigationSelectUtil;

public class ConsoleMainActivity extends NavigationSelectUtil {

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
        View ConsoleLoginView=findViewById(R.id.default_include_console_login);
        View ConsoleMainView=findViewById(R.id.default_include_console_main);

        MainActivityView.setVisibility(View.GONE);
        CalculatorView.setVisibility(View.GONE);
        HomeWorkView.setVisibility(View.GONE);
        LogicalView.setVisibility(View.GONE);
        RadixView.setVisibility(View.GONE);
        ScheduleView.setVisibility(View.GONE);
        ConsoleLoginView.setVisibility(View.GONE);
        ConsoleMainView.setVisibility(View.VISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("控制台");
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
