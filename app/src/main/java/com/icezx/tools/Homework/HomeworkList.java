package com.icezx.tools.Homework;

/**
 * 与json数据相对应的bean
 */
public class HomeworkList {

    private String title;
    private String desc;
    private String date;
    private String content_url;

    /**
     * 全参的构造函数
     *
     * @param title
     * @param desc
     * @param content_url
     */
    public HomeworkList(String date , String title, String desc, String content_url) {
        setTitle(title);
        setDesc(desc);
        setDate(date);
        setContent_url(content_url);
    }

    //日期
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //标题
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //文本
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    //链接
    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

}