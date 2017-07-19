package com.entreprise.davfou.projetandroidesgi.data.model.api;

/**
 * Created by Pico on 28/06/2017.
 */

public class Comment {

    private String _id;

    private String author;
    private String title;
    private String content;
    private String news;
    private String date;


    public Comment(String _id, String author, String title, String content, String news, String date) {
        this._id = _id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.news = news;
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}



