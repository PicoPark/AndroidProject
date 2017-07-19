package com.entreprise.davfou.projetandroidesgi.data.model.local;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pico on 28/06/2017.
 */

public class TopicRealm extends RealmObject {


    @PrimaryKey

    private String _id;
    private String content;
    private String title;
    private String date;
    private String author;
    private RealmList<PostRealm> postRealms;

    public TopicRealm() {
    }

    public TopicRealm(String _id, String content, String title, String date,String author, RealmList<PostRealm> postRealms) {
        this._id = _id;
        this.content = content;
        this.title = title;
        this.date = date;
        this.postRealms = postRealms;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public RealmList<PostRealm> getPostRealms() {
        return postRealms;
    }

    public void setPostRealms(RealmList<PostRealm> postRealms) {
        this.postRealms = postRealms;
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
}
