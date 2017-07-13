package com.grid.appy.citizenrights.model;

/**
 * Created by Appy on 13-Jul-17.
 */

public class Dept {
    private String username, title, date;

    public Dept() {
    }

    public Dept(String title, String username, String date) {
        this.title = title;
        this.username = username;
        this.date = date;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}