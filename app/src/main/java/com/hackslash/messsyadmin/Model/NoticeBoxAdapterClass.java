package com.hackslash.messsyadmin.Model;

public class NoticeBoxAdapterClass {

   private int mImageResourceId,sDescription;
   private String sHostelMessName ,  sDate, sTime;


    public NoticeBoxAdapterClass(int imageResourceId, String hostelMessName, int description, String date, String time ){
        this.mImageResourceId = imageResourceId;
        this.sHostelMessName = hostelMessName;
        this.sDescription = description;
        this.sDate = date;
        this.sTime = time;
    }

    public String getsHostelMessName() {
        return sHostelMessName;
    }

    public void setsHostelMessName(String sHostelMessName) {
        this.sHostelMessName = sHostelMessName;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public void setmImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }

    public int getsDescription() {
        return sDescription;
    }

    public void setsDescription(int sDescription) {
        this.sDescription = sDescription;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }
}
