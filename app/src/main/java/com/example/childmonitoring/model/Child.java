package com.example.childmonitoring.model;

public class Child {
    private int id;
    private String childName;
    private boolean status;

    //three argument constructor
    public Child(int id, String childName, boolean status) {
        this.id = id;
        this.childName = childName;
        this.status = status;
    }
    //two argument constructor
    public Child(String childName, boolean status) {
        this.childName = childName;
        this.status = status;
    }
    //one argument constructor
    public Child(String childName) {
        this.childName = childName;
    }

    //no argument constructor
    public Child() {
    }

    //getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
