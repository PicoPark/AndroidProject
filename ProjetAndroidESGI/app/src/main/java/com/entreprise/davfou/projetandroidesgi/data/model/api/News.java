package com.entreprise.davfou.projetandroidesgi.data.model.api;

/**
 * Created by davidfournier on 28/06/2017.
 */

public class News {

    private String _id;
    private String author;
    private String content;
    private String title;
    private String date;


    public News(String _id, String author, String content, String title,String date) {
        this._id = _id;
        this.author = author;
        this.content = content;
        this.title = title;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
}
