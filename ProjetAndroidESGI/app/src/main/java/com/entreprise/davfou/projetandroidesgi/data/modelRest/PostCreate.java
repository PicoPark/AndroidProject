package com.entreprise.davfou.projetandroidesgi.data.modelRest;

/**
 * Created by Pico on 04/07/2017.
 */

public class PostCreate {

    private String content;
    private String title;
    private String date;
    private String topic;


    public PostCreate(String content, String title, String date, String topic) {
        this.content = content;
        this.title = title;
        this.date = date;
        this.topic = topic;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


}
