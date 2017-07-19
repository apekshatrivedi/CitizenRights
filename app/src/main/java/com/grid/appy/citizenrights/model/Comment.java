package com.grid.appy.citizenrights.model;

/**
 * Created by Appy on 13-Jul-17.
 */

public class Comment {
    private String username, comment, date,time;

    public Comment() {
    }

    public Comment(String username, String comment, String date,String time) {
        this.username = username;
        this.comment = comment;
        this.date = date;
        this.time=time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}