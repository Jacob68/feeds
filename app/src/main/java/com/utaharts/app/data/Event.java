package com.utaharts.app.data;

/**
 * Event type contains: name, day, start time, end time, stage, genre, description and image.
 *
 * Created by jacob.lafazia on 8/21/2014.
 */
public class Event {
    public final String name;
    public final String day;
    public final String starttime;
    public final String endtime;
    public final String stage;
    public final String genre;
    public final String description;
    public final String imageURL;

    public Event(String name, String day, String starttime, String endtime, String stage, String genre, String description, String imageURL) {
        this.name = name;
        this.day = day;
        this.starttime = starttime;
        this.endtime = endtime;
        this.stage = stage;
        this.genre = genre;
        this.description = description;
        // TODO once implemented, store image as Bitmap
        this.imageURL = imageURL;
    }
}
