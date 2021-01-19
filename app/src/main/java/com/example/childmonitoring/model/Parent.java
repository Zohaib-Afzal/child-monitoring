package com.example.childmonitoring.model;

public class Parent {
    private int id;
    private String parentName;
    private String parentEmail;
    private String childName;
    private String parentPassword;

    public Parent(int id, String parentName, String parentEmail, String childName, String parentPassword) {
        this.id = id;
        this.parentName = parentName;
        this.parentEmail = parentEmail;
        this.childName = childName;
        this.parentPassword = parentPassword;
    }

    public Parent(String parentName, String parentEmail, String childName, String parentPassword) {
        this.parentName = parentName;
        this.parentEmail = parentEmail;
        this.childName = childName;
        this.parentPassword = parentPassword;
    }

    public Parent() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getParentPassword() {
        return parentPassword;
    }

    public void setParentPassword(String parentPassword) {
        this.parentPassword = parentPassword;
    }
}
