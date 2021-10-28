package com.hackslash.messsyadmin.Model;

public class UsersAdapterClass {

    private String sName , sLastMessage , sTime;
    int sImageResourceId;

    public UsersAdapterClass(){}

    public UsersAdapterClass(String sName, String sLastMessage, String sTime, int sImageResourceId) {
        this.sName = sName;
        this.sLastMessage = sLastMessage;
        this.sTime = sTime;
        this.sImageResourceId = sImageResourceId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsLastMessage() {
        return sLastMessage;
    }

    public void setsLastMessage(String sLastMessage) {
        this.sLastMessage = sLastMessage;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public int getsImageResourceId() {
        return sImageResourceId;
    }

    public void setsImageResourceId(int sImageResourceId) {
        this.sImageResourceId = sImageResourceId;
    }
}
