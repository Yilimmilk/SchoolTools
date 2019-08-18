package com.icezx.tools.Calculator;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.icezx.tools.Utils.NavigationSelectUtil;
import com.icezx.tools.R;

public class CalculatorActivity extends NavigationSelectUtil {

    private WebView main;

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
        CalculatorView.setVisibility(View.VISIBLE);
        HomeWorkView.setVisibility(View.GONE);
        LogicalView.setVisibility(View.GONE);
        RadixView.setVisibility(View.GONE);
        ScheduleView.setVisibility(View.GONE);
        ConsoleLoginView.setVisibility(View.GONE);
        ConsoleMainView.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("计算器");
        initializeToolbar();

        main=findViewById(R.id.wv_main);

        main.loadUrl("https://www.zybang.com/static/question/m-calculator/m-calculator.html");

        WebSettings webSettings = main.getSettings();
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //允许js代码
        webSettings.setJavaScriptEnabled(true);
        //允许SessionStorage/LocalStorage存储
        webSettings.setDomStorageEnabled(true);
        //禁用放缩
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(false);
        //禁用文字缩放
        webSettings.setTextZoom(100);
        //10M缓存，api 18后，系统自动管理。
        webSettings.setAppCacheMaxSize(5 * 1024 * 1024);
        //允许WebView使用File协议
        webSettings.setAllowFileAccess(true);
        //不保存密码
        webSettings.setSavePassword(false);
        //自动加载图片
        webSettings.setLoadsImagesAutomatically(true);

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
        if (id == R.id.main_reload) {
            main.reload();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK ) {  // goBack()表示返回WebView的上一页面

            main.goBack();

            return true;

        } else {

            //结束当前页
            return super.onKeyDown(keyCode, event);
        }

    }
}
