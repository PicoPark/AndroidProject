package com.entreprise.davfou.projetandroidesgi.data.model.local;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pico on 28/06/2017.
 */

public class CommentRealm extends RealmObject {

    @PrimaryKey
    private long id;

    private String _id;
    private String author;
    private String content;
    private String title;
    private String date;
    private String news;


    public CommentRealm() {
    }

    public CommentRealm(long id, String _id, String author, String content, String title, String date, String news) {
        this.id = id;
        this._id = _id;
        this.author= author;
        this.content = content;
        this.title = title;
        this.date = date;
        this.news = news;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}


