package com.example.assad.socialmediaappjava.Models;


public class EventModel {

    private String imageUrl;
    private String eventTitle;
    private String eventID;
    private String eventTime;
    private String eventAuthor;
    private boolean hasBeenLiked;
    private String eventDescription;
    private boolean hasBeenJoined;
    private String timeofEvent;


    public EventModel(String imageUrl, String eventTitle, String eventID, String eventTime, String eventAuthor, String eventDescription, boolean hasBeenLiked, boolean hasBeenJoined, String timeofEvent) {
        this.imageUrl = imageUrl;
        this.eventTitle = eventTitle;
        this.eventID = eventID;
        this.eventTime = eventTime;
        this.eventAuthor = eventAuthor;
        this.eventDescription = eventDescription;
        this.hasBeenLiked = hasBeenLiked;
        this.hasBeenJoined = hasBeenJoined;
        this.timeofEvent = timeofEvent;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventAuthor() {
        return eventAuthor;
    }

    public void setEventAuthor(String eventAuthor) {
        this.eventAuthor = eventAuthor;
    }

    public boolean hasBeenLiked() {
        return hasBeenLiked;
    }

    public void setHasBeenLiked(boolean hasBeenLiked) {
        this.hasBeenLiked = hasBeenLiked;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public boolean isHasBeenJoined() {
        return hasBeenJoined;
    }

    public void setHasBeenJoined(boolean hasBeenJoined) {
        this.hasBeenJoined = hasBeenJoined;
    }

    public String getTimeofEvent() {
        return timeofEvent;
    }

    public void setTimeofEvent(String timeofEvent) {
        this.timeofEvent = timeofEvent;
    }
}

