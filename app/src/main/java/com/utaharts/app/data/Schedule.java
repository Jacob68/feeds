package com.utaharts.app.data;

import android.graphics.Bitmap;

/**
 * Created by jacoblafazia on 5/14/14.
 */
public class Schedule {

    private Bitmap image;
    private String title;
    private String time;
    private String location;
    private String description;

    public Schedule() {

    }

    public Schedule(Bitmap image, String title, String time, String location, String description) {
        this.image = image;
        this.title = title;
        this.time = time;
        this.location = location;
        this.description = description;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return this.image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
