package com.va.reminder.model;

public class Reminders {
    private String Content;
    private String Time;

    public String getContent() { return Content; }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }


    public Reminders(String Content, String Time) {
        this.Content = Content;
        this.Time = Time;
    }

    public Reminders() {
    }
}
