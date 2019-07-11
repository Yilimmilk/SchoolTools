package com.icezx.tools;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends NavigationSelectActivity {

    // 定义变量
    private ProgressDialog progressDialog;
    private String info = "";
    private String time = "";
    private String pic_url = "";
    // SQL帮助类，参数用于设置连接字符串，参数1：主机ip，参数2：数据库名，参数3：用户名，参数4：用户密码
    private SqlHelper serverlink = new SqlHelper("hk.icezx.com", "classinfo", "classinfo", "333333");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("通知");
        initializeToolbar();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        progressDialog = new ProgressDialog(MainActivity.this);//1.创建一个ProgressDialog的实例
        progressDialog.setTitle("请稍候...");//2.设置标题
        progressDialog.setMessage("正在读取数据库...");//3.设置显示内容
        progressDialog.setCancelable(false);//4.设置可否用back键关闭对话框
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
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 调用super的方法，传入handler对象接收到的msg对象
            //super.handleMessage(msg);
            String jsonResult = (String) msg.obj;
            System.out.println(jsonResult);
            // 控制台输出，用于监视，与实际使用无关
            TextView message_main = (TextView) findViewById(R.id.message_main);
            TextView message_time = (TextView) findViewById(R.id.message_time);
            ImageView message_pic = (ImageView) findViewById(R.id.message_pic);

            try {
                //下边是解析json
                JSONArray jsonArray = new JSONArray(jsonResult);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object2 = jsonArray.getJSONObject(i);
                    String info = object2.getString("info");
                    String time = object2.getString("time");
                    String pic_url = object2.getString("pic_url");

                    message_main.setText(info);
                    message_time.setText(time);
                    HttpUtils.setPicBitmap(message_pic, pic_url);
                    HttpUtils.setPicBitmap(message_pic, pic_url);
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
        String sql = "SELECT * FROM `mobile`";
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

    //界面创建时
    protected void onStart() {

        Log.i("RadixActivity", "onStart");

        if (!isNetworkAvailable(this)) {
            showSetNetworkUI(this);
        } else {
            //Toast.makeText(this, "连接服务器...", Toast.LENGTH_SHORT).show();
        }
        super.onStart();
    }

    //网络检查dialog，若失败则跳转到系统WIFI设置，使用网络检查方法。
    public void showSetNetworkUI(final Context context) {
        // 提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog show = builder.setTitle("网络检查")
                .setMessage("您可能未连接网络，是否跳转到设置?")
                .setCancelable(false)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Intent intent = null;
                        //判断手机系统的版本 即API大于10 就是3.0或以上版本
                        if (Build.VERSION.SDK_INT > 10) {
                            intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        } else {
                            intent = new Intent();
                            ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                            intent.setComponent(component);
                            intent.setAction("android.intent.action.VIEW");
                        }
                        context.startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"只能使用课程表功能",Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    //返回键事件
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //右上角菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    //当被选中时，开启新的线程
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_reload) {
            progressDialog = new ProgressDialog(MainActivity.this);//1.创建一个ProgressDialog的实例
            progressDialog.setTitle("请稍候...");//2.设置标题
            progressDialog.setMessage("正在读取数据库...");//3.设置显示内容
            progressDialog.setCancelable(false);//4.设置可否用back键关闭对话框
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //网络检查方法
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }
}