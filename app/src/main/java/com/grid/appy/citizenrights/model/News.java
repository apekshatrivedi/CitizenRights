package com.grid.appy.citizenrights.model;

/**
 * Created by Appy on 12-Jul-17.
 */

public class News {

    private String title, username,date;

    public News() {
    }

    public News(String title, String username, String date) {
        this.title = title;
        this.username = username;
        this.date=date;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }






}




