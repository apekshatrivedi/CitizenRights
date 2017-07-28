package com.grid.appy.citizenrights.adapter;

/**
 * Created by Appy on 27-Jul-17.
 */

public class GetDataAdapter {

    public String ImageServerUrl;
    public String ImageTitleName;
    public String home_title;
    public String home_issueid;
    public String home_date;
    public String home_username;
    public String history_issueid;
    public String history_date;
    public String history_username;
    public String history_title;


    public String getImageServerUrl() {
        return ImageServerUrl;
    }

    public void setImageServerUrl(String imageServerUrl) {
        this.ImageServerUrl = imageServerUrl;
    }

    public String getImageTitleName() {
        return ImageTitleName;
    }

    public void setImageTitleNamee(String Imagetitlename) {
        this.ImageTitleName = Imagetitlename;
    }

    public String getHome_title() {
        return home_title;
    }

    public String getHome_issueid() {
        return home_issueid;
    }

    public void setHome_issueid(String home_issueid) {
        this.home_issueid = home_issueid;
    }
   public void setHome_title(String home_title) {
        this.home_title = home_title;
    }

    public String getHome_date() {
        return home_date;
    }

    public void setHome_date(String home_date) {
        this.home_date = home_date;
    }

    public String getHome_username() {
        return home_username;
    }

    public void setHome_username(String home_username) {
        this.home_username = home_username;
    }

    public String getHistory_issueid(){return  home_issueid;}

    public void setHistory_issueid(String history_issueid){this.history_issueid=history_issueid;}

    public String getHistory_title(){return  home_title;}

    public void setHistory_title(String history_title){this.history_title=history_title;}

    public String getHistory_username(){return  home_username;}

    public void setHistory_username(String history_username){this.history_issueid=history_username;}

    public String getHistory_date(){return  home_date;}

    public void setHistory_date(String history_date){this.history_date=history_date;}


}