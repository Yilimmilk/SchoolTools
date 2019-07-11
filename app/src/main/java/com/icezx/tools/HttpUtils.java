package com.icezx.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 访问网络的工具类
 *
 * @author xuliugen
 *
 */
public class HttpUtils {

    public static void getNewsJSON(final String url, final Handler handler) {

        //要访问网络，开启一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn;
                InputStream inputStream;
                try {
                    conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.setRequestMethod("GET");
                    inputStream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    StringBuilder result = new StringBuilder();  //StringBuilder初始化不可以null
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    //使用handler的话要使用Message
                    Message msg = new Message();
                    msg.obj = result.toString();

                    // 通知主线程handler
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 设置图片的信息
     *
     * 在适配器里边调用
     *
     * @param message_pic 需要设置的view组件
     * @param pic_url 图片的地址
     */
    public static void setPicBitmap(final ImageView message_pic, final String pic_url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(pic_url).openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    message_pic.setImageBitmap(bitmap);
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}