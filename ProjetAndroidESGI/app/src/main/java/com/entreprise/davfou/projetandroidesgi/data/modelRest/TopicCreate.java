package com.entreprise.davfou.projetandroidesgi.data.modelRest;

/**
 * Created by Pico on 28/06/2017.
 */

public class TopicCreate {

    private String content;
    private String title;
    private String date;

    public TopicCreate( String content, String title, String date) {
        this.content = content;
        this.title = title;
        this.date = date;
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
