package com.hackslash.messsyadmin.Model;

public class CreateNewNoticeClass {
    private String subject;
    private String Description;

    long timestamp;
    private String UserInfo;
    private String date;

    public CreateNewNoticeClass(String subject, String Description, String UserInfo, String date, long timestamp){

        this.subject=subject;
        this.Description= Description;
        this.UserInfo=UserInfo;
        this.date=date;
        this.timestamp=timestamp;

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
    public String getUserInfo(){
        return UserInfo;
    }
    public void setUserInfo(String UserInfo){
        this.subject=subject;
    }
    public long getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(long timestamp){
        this.timestamp=timestamp;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date=date;
    }
}