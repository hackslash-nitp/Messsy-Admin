package com.hackslash.messsyadmin.Model;

public class ComplaintBoxAdapterClass {

    String Issue, Explanation, ImageUrl, Date;
    //private  String sName , sDate, sHeading, sUpvotes , sComments, sComplainImage, sDescription;
    //private int mImageResourceId ;

    ComplaintBoxAdapterClass() {}

    public ComplaintBoxAdapterClass(String sIssue, String sExplanation, String sImageUrl, String sDate){

        this.Issue = sIssue;
        this.Explanation = sExplanation;
        this.ImageUrl = sImageUrl;
        this.Date = sDate;

    }

    public String getIssue() {
        return Issue;
    }

    public void setIssue(String issue) {
        Issue = issue;
    }

    public String getExplanation() {
        return Explanation;
    }

    public void setExplanation(String explanation) {
        Explanation = explanation;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


}


