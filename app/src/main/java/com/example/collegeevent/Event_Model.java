package com.example.collegeevent;

public class Event_Model {
    private String           title;
    private String           eventDate;
    private String           eventTime;
    private String           venue;
    private String           reminderSet;
    private String           description;


    public Event_Model() {
    }

    public Event_Model(String title, String eventDate, String eventTime, String venue, String reminderSet, String description) {
        this.title          = title;
        this.eventDate      = eventDate;
        this.eventTime      = eventTime;
        this.venue          = venue;
        this.reminderSet    = reminderSet;
        this.description    = description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getReminderSet() {
        return reminderSet;
    }

    public void setReminderSet(String reminderSet) {
        this.reminderSet = reminderSet;
    }
}
