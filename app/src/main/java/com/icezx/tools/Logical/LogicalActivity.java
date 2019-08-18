package com.icezx.tools.Logical;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.icezx.tools.R;
import com.icezx.tools.Utils.NavigationSelectUtil;

public class LogicalActivity extends NavigationSelectUtil implements View.OnClickListener {

    private EditText etLogicalMain,etLogicalResult;
    private Button btYu,btHuo,btFei,btClear,btStart;

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
        View TranslateMainView=findViewById(R.id.default_include_translate_main);

        MainActivityView.setVisibility(View.GONE);
        CalculatorView.setVisibility(View.GONE);
        HomeWorkView.setVisibility(View.GONE);
        LogicalView.setVisibility(View.VISIBLE);
        RadixView.setVisibility(View.GONE);
        ScheduleView.setVisibility(View.GONE);
        ConsoleLoginView.setVisibility(View.GONE);
        ConsoleMainView.setVisibility(View.GONE);
        TranslateMainView.setVisibility(View.GONE);

        setTitle("逻辑运算");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeToolbar();

        initView();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initView(){
        etLogicalMain=findViewById(R.id.et_logical_main);
        etLogicalResult=findViewById(R.id.et_logical_result);
        btYu=findViewById(R.id.bt_yu);
        btHuo=findViewById(R.id.bt_huo);
        btFei=findViewById(R.id.bt_fei);
        btClear=findViewById(R.id.bt_clear);
        btStart=findViewById(R.id.bt_start);

        btYu.setOnClickListener(this);
        btHuo.setOnClickListener(this);
        btFei.setOnClickListener(this);
        btClear.setOnClickListener(this);
        btStart.setOnClickListener(this);
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

    //点击监听事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_yu:

                break;

            case R.id.bt_huo:

                break;

            case R.id.bt_fei:

                break;

            case R.id.bt_clear:
                etLogicalMain.setText("");
                etLogicalResult.setText("");
                break;

            case R.id.bt_start:
                try {
                    if (!TextUtils.isEmpty(etLogicalMain.getText().toString())){
                        if (LogicalUtil.compareTo(etLogicalMain.getText().toString())) {
                            etLogicalResult.setText("True");
                        }else {
                            etLogicalResult.setText("False");
                        }
                    }else {
                        Toast.makeText(LogicalActivity.this,"公式不能为空",Toast.LENGTH_SHORT).show();
                    }
                    break;
                }catch (Exception e) {

                }
        }
    }
}
