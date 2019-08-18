package com.icezx.tools.Console;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.icezx.tools.R;
import com.icezx.tools.Utils.NavigationSelectUtil;
import com.icezx.tools.Utils.RC4Util;
import com.icezx.tools.Utils.SqlHelperUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsoleLoginActivity extends NavigationSelectUtil {

    private EditText etUser,etPass;
    private FloatingActionButton fab;
    private ProgressDialog progressDialog;
    private String unReadPassword,readablePasssword;
    private SharedPreferences.Editor editor;

    // SQL帮助类，参数用于设置连接字符串，参数1：主机ip，参数2：数据库名，参数3：用户名，参数4：用户密码
    private SqlHelperUtil serverlink = new SqlHelperUtil("hk.icezx.com", "console", "console", "222222");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_nav_view);

        //SharedPreference用来储存设置
        SharedPreferences lastlogin = getSharedPreferences("AccountLogin", MODE_PRIVATE);
        editor = lastlogin.edit();

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
        ConsoleLoginView.setVisibility(View.VISIBLE);
        ConsoleMainView.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("控制台");
        initializeToolbar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        etUser=findViewById(R.id.et_user);
        etPass=findViewById(R.id.et_password);
        fab = findViewById(R.id.fab_login);

        etUser.setText(lastlogin.getString("User","0"));
        etUser.setText(lastlogin.getString("Pass","0"));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etUser.getText().toString())||TextUtils.isEmpty(etPass.getText().toString())){
                    Toast.makeText(ConsoleLoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (etUser.getText().toString().equals("admin")){
                        progressDialog = new ProgressDialog(ConsoleLoginActivity.this);//1.创建一个ProgressDialog的实例
                        progressDialog.setTitle("正在验证...");//2.设置标题
                        progressDialog.setMessage("正在验证密码...");//3.设置显示内容
                        progressDialog.setCancelable(true);//4.设置可否用back键关闭对话框
                        progressDialog.show();//5.将ProgessDialog显示出来

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                // 通过Message类来传递结果值，先实例化
                                String jsonResult = Select();
                                Message msg = new Message();
                                // 下面分别是增删改查方法
                                msg.obj = jsonResult;
                                // 执行完以后，把msg传到handler，并且触发handler的响应方法
                                handler.sendMessage(msg);
                            }
                        });
                        // 进程开始，这行代码不要忘记
                        thread.start();
                    }else {
                        Toast.makeText(ConsoleLoginActivity.this,"用户名错误",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 调用super的方法，传入handler对象接收到的msg对象
            //super.handleMessage(msg);
            String jsonResult = (String) msg.obj;
            System.out.println(jsonResult);

            try {
                //下边是解析json
                JSONArray jsonArray = new JSONArray(jsonResult);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object2 = jsonArray.getJSONObject(i);
                    String password = object2.getString("password");
                    unReadPassword=password;
                    //Log.d("未解迷数据",unReadPassword);
                }
                readablePasssword= RC4Util.decry_RC4(unReadPassword,"43");
                //Log.d("已解迷数据",readablePasssword);
                if (readablePasssword.equals(etPass.getText().toString())){
                    startActivity(new Intent(ConsoleLoginActivity.this,ConsoleMainActivity.class));

                    editor.putString("User",etUser.getText().toString());
                    editor.putString("Pass",etPass.getText().toString());
                    editor.commit();
                    finish();
                }else {
                    Toast.makeText(ConsoleLoginActivity.this,"验证失败",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // log提示
            Log.d("调试","连接服务器正常");
            progressDialog.dismiss();
        }
    };

    public String Select() {
        String sql = "SELECT * FROM `admin`";
        String jsonResult = null;
        try {
            // serverlink.ExecuteQuery()，参数1：查询语句，参数2：查询用到的变量，用于本案例不需要参数，所以用空白的new
            // ArrayList<Object>()
            jsonResult = serverlink.ExecuteQuery(sql, new ArrayList<Object>());
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            return null;
        }
        return jsonResult;
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
