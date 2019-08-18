package com.icezx.tools.Schedule;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.icezx.tools.Utils.NavigationSelectUtil;
import com.icezx.tools.R;

import java.util.Calendar;

public class ScheduleActivity extends NavigationSelectUtil {

    private TextView tv12,tv13,tv14,tv15,tv16;

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
        ScheduleView.setVisibility(View.VISIBLE);
        ConsoleLoginView.setVisibility(View.GONE);
        ConsoleMainView.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("课程表");
        initializeToolbar();

        tv12=findViewById(R.id.tv_1_2);
        tv13=findViewById(R.id.tv_1_3);
        tv14=findViewById(R.id.tv_1_4);
        tv15=findViewById(R.id.tv_1_5);
        tv16=findViewById(R.id.tv_1_6);

        if (getWeek().equals("1")){

        }else if (getWeek().equals("2")){
            tv12.setTextColor(Color.RED);
        }else if (getWeek().equals("3")) {
            tv13.setTextColor(Color.RED);
        }else if (getWeek().equals("4")){
            tv14.setTextColor(Color.RED);
        }else if (getWeek().equals("5")){
            tv15.setTextColor(Color.RED);
        }else if (getWeek().equals("6")){
            tv16.setTextColor(Color.RED);
        }else if (getWeek().equals("7")){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    /*获取星期几*/
    public static String getWeek() {
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                //星期日
                return "1";
            case 2:
                //星期一
                return "2";
            case 3:
                //星期二
                return "3";
            case 4:
                //星期三
                return "4";
            case 5:
                //星期四
                return "5";
            case 6:
                //星期五
                return "6";
            case 7:
                //星期六
                return "7";
            default:
                return "";
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}