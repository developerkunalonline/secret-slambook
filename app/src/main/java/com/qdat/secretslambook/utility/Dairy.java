package com.qdat.secretslambook.utility;

import java.util.List;

public class Dairy {

    private long id;
    private String title;
    private MyDate date;
    private String body;
    private String images;


    public Dairy(long id, String title, MyDate date, String body, String images) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.body = body;
        this.images = images;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Dairy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date.getDate() +
                ", body='" + body + '\'' +
                ", images='" + images + '\'' +
                "}\n\n";
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MyDate getDate() {
        return date;
    }

    public void setDate(MyDate date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }


    public Dairy(String title, MyDate date, String body, String images) {
        this.title = title;
        this.date = date;
        this.body = body;
        this.images = images;
    }
}
