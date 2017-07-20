package com.grid.appy.citizenrights.model;

/**
 * Created by Appy on 20-Jul-17.
 */

public class People {

    private String name, dept;

    public People() {
    }

    public People(String name, String dept) {
        this.name = name;
        this.dept = dept;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

}
