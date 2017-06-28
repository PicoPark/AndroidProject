package com.entreprise.davfou.projetandroidesgi.data.modelRest;

/**
 * Created by Pico on 28/06/2017.
 */

public class Comment {

    private String _id;

    private String title;
    private String content;
    private String news;
    private String date;

    public Comment(String _id, String content, String title, String date, String news) {
        this._id = _id;
        this.content = content;
        this.title = title;
        this.date = date;
        this.news = news;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}



