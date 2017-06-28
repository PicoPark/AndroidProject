package com.entreprise.davfou.projetandroidesgi.data.modelRest;

/**
 * Created by Pico on 28/06/2017.
 */

public class Comment {

    private String _id;

    private String author;
    private String title;
    private String content;
    private String news;


    public Comment(String _id, String author, String title, String content, String news) {
        this._id = _id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.news = news;
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
}



