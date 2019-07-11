package com.icezx.tools.Homework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icezx.tools.R;

import java.util.List;

public class HomeworkListAdapter extends BaseAdapter {

    private Context context;
    private List<HomeworkList> newsList;

    public HomeworkListAdapter(Context context, List<HomeworkList> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public HomeworkList getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 为news-item中的布局赋值
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) { //如果为空则重新创建
            convertView = LayoutInflater.from(context).inflate(R.layout.homework_item, null);
        }
        // 获得news-item中的控件
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);

        HomeworkList news = newsList.get(position);
        tvTitle.setText(news.getTitle());
        tvDesc.setText(news.getDesc());
        tvDate.setText(news.getDate());

        String content_url = news.getContent_url();

        return convertView;
    }

}

