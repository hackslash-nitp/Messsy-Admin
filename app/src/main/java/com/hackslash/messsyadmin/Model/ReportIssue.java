package com.hackslash.messsyadmin.Model;

public class ReportIssue {

    String Issue, Explanation, ImageUrl;

    public ReportIssue(){}


    public  ReportIssue(String sIssue, String sExplanation, String sImageUrl)
    {
        this.Issue = sIssue;
        this.Explanation = sExplanation;
        this.ImageUrl = sImageUrl;
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

    public String getImageUrl() { return ImageUrl; }

    public void setImageUrl(String imageUrl) { ImageUrl = imageUrl; }
}
