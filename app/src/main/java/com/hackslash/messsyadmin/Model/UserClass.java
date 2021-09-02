package com.hackslash.messsyadmin.Model;

public class UserClass {

    String  Name , Email , Mobile, HostelName, Designation , imageUrl;

    public UserClass(){}

    public UserClass(String sName, String sEmail, String sMobile, String sHostelName, String sDesignation, String sImageUrl) {
        this.Name = sName;
        this.Email = sEmail;
        this.Mobile = sMobile;
        this.HostelName = sHostelName;
        this.Designation = sDesignation;
        this.imageUrl = sImageUrl;
    }

    public UserClass(String sName, String sEmail, String sMobile, String sHostelName, String sDesignation) {
        this.Name = sName;
        this.Email = sEmail;
        this.Mobile = sMobile;
        this.HostelName = sHostelName;
        this.Designation = sDesignation;
    }

    public UserClass(String sName, String sEmail, String sMobile , String sDesignation) {
        this.Name = sName;
        this.Email = sEmail;
        this.Mobile = sMobile;
        this.Designation =sDesignation;
    }

    public String getsName() {
        return Name;
    }

    public void setsName(String sName) {
        this.Name = sName;
    }

    public String getsEmail() {
        return Email;
    }

    public void setsEmail(String sEmail) {
        this.Email = sEmail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getsMobile() {
        return Mobile;
    }

    public void setsMobile(String sMobile) {
        this.Mobile = sMobile;
    }

    public String getsHostelName() {
        return HostelName;
    }

    public void setsHostelName(String sHostelName) {
        this.HostelName = sHostelName;
    }

    public String getsDesignation() {
        return Designation;
    }

    public void setsDesignation(String sDesignation) {
        this.Designation = sDesignation;
    }
}
