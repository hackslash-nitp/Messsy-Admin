package com.hackslash.messsyadmin.Model;

public class ChatAdapterClass {

    private String sName , sLastMessage , sTime;
    private int mImageResourceId;

    public  ChatAdapterClass(){}

    public ChatAdapterClass(String sName, String sLastMessage, String sTime, int mImageResourceId) {
        this.sName = sName;
        this.sLastMessage = sLastMessage;
        this.sTime = sTime;
        this.mImageResourceId = mImageResourceId;
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

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public void setmImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }
}
