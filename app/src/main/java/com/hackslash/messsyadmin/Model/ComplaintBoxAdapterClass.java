package com.hackslash.messsyadmin.Model;

public class ComplaintBoxAdapterClass {

    private  String sName , sDate, sHeading, sUpvotes , sComments;
    private int mImageResourceId , sDescription;

    public ComplaintBoxAdapterClass(int imageResourceId , String name , String date, String heading , int description, String upvotes, String comments){

        this.mImageResourceId = imageResourceId;
        this.sName = name;
        this.sComments =comments;
        this.sDate = date;
        this.sHeading = heading;
        this.sDescription = description;
        this.sUpvotes = upvotes;

    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsHeading() {
        return sHeading;
    }

    public void setsHeading(String sHeading) {
        this.sHeading = sHeading;
    }

    public int getsDescription() {
        return sDescription;
    }

    public void setsDescription(int sDescription) {
        this.sDescription = sDescription;
    }

    public String getsUpvotes() {
        return sUpvotes;
    }

    public void setsUpvotes(String sUpvotes) {
        this.sUpvotes = sUpvotes;
    }

    public String getsComments() {
        return sComments;
    }

    public void setsComments(String sComments) {
        this.sComments = sComments;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public void setmImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }
}


