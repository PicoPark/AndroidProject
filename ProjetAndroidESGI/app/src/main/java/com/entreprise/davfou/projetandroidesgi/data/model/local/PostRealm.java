package com.entreprise.davfou.projetandroidesgi.data.model.local;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pico on 28/06/2017.
 */

public class PostRealm extends RealmObject {



    @PrimaryKey
    private String _id;
    private String author;
    private String content;
    private String title;
    private String topic;
    private String date;



    public PostRealm(){

    }

    public PostRealm(String _id, String content,String date, String auteur, String title, String topic) {
        this._id = _id;
        this.content = content;
        this.date = date;
        this.author = auteur;
        this.title = title;
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
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
