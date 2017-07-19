package com.entreprise.davfou.projetandroidesgi.data.model.api;

/**
 * Created by Pico on 28/06/2017.
 */

public class Post {

    private String _id;
    private String content;
    private String title;
    private String topic;
    private String date;
    private String author;

    public Post(String _id, String content,String date, String author, String title, String topic) {
        this._id = _id;
        this.content = content;
        this.date = date;
        this.title = title;
        this.author = author;
        this.topic = topic;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
