package com.hackslash.messsyadmin.Model;

import android.widget.ImageView;

public class ReportIssue {

    String Issue, Explanation;
    ImageView ReportIssueImage;

    public ReportIssue(){}

    public  ReportIssue(String sIssue, String sExplanation, ImageView sImage) {

        this.Issue = sIssue;
        this.Explanation = sExplanation;
        this.ReportIssueImage = sImage;
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

    public ImageView getReportIssueImage() {
        return ReportIssueImage;
    }

    public void setReportIssueImage(ImageView reportIssueImage) {
        ReportIssueImage = reportIssueImage;
    }

}
