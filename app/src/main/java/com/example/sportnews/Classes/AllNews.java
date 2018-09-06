package com.example.sportnews.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllNews {
    @SerializedName("events")
    @Expose
    private List<NewsItem> events;

    public List<NewsItem> getEvents() {
        return events;
    }

    public void setEvents(List<NewsItem> events) {
        this.events = events;
    }
}
