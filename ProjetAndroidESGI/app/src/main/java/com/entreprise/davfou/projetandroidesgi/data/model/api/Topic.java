package com.entreprise.davfou.projetandroidesgi.data.model.api;

import java.util.ArrayList;

/**
 * Created by Pico on 28/06/2017.
 */

public class Topic  {



    private String _id;
    private String content;
    private String title;
    private String date;
    private String author;
    private ArrayList<Post> posts;

    public Topic() {
    }

    public Topic(String _id, String content, String title, String date,String author, ArrayList<Post> posts) {
        this._id = _id;
        this.content = content;
        this.title = title;
        this.date = date;
        this.posts = posts;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<Post> getPost() {
        return posts;
    }

    public void setPost(ArrayList<Post> posts) {
        this.posts = posts;
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
