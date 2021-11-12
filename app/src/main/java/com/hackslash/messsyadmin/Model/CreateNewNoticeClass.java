package com.hackslash.messsyadmin.Model;

public class CreateNewNoticeClass {
    private String subject;
    private String Description;

    String timestamp;
    private String date, designation, hostel;

    public CreateNewNoticeClass(String subject, String Description, String date, String timestamp, String designation, String hostel){

        this.subject=subject;
        this.Description= Description;
        this.date=date;
        this.timestamp=timestamp;
        this.designation=designation;
        this.hostel=hostel;

    }
    public CreateNewNoticeClass(){

    }
    public String getSubject(){
        return subject;
    }
    public void setSubject(String subject){
        this.subject=subject;
    }
    public String getDescription(){
        return Description;
    }
    public void setDescription(String Description){
        this.Description=Description;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }
}