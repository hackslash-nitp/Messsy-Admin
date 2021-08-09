package com.hackslash.messsyadmin.Model;

public class MessMenuAdapterClass {

    private  String mealtime , mMonday, mTuesday, mWednesday , mThursday,mFriday,mSaturday,mSunday;

    public MessMenuAdapterClass( String mealtime , String mMonday, String mTuesday ,  String mWednesday,
                                 String mThursday,String mFriday, String mSaturday, String mSunday){


        this.mealtime = mealtime;
        this.mMonday =mMonday;
        this.mTuesday = mTuesday;
        this.mWednesday = mWednesday;
        this.mThursday = mThursday;
        this.mFriday = mFriday;
        this.mSaturday = mSaturday;
        this.mSunday = mSunday;
    }

    public String getmealtime() {
        return mealtime;
    }

    public void setDay(String mealtime) {
        this.mealtime = mealtime;
    }

    public String getmMonday() {
        return mMonday;
    }

    public void setmMonday(String mMonday) {
        this.mMonday= mMonday;
    }

    public String getmTuesday() {
        return mTuesday;
    }

    public void setmTuesday(String mTuesday) {
        this.mTuesday = mTuesday;
    }

    public String getmWednesday() {
        return mWednesday;
    }

    public void setmWednesday(String mWednesday) {
        this.mWednesday = mWednesday;
    }

    public String getmThursday() {
        return mThursday;
    }

    public void setmThursday(String mThursday) {this.mThursday = mThursday; }
    public String getmFriday() {
        return mFriday;
    }

    public void setmFriday(String mFriday) {this.mFriday = mFriday; }

    public String getmSaturday() {
        return mSaturday;
    }

    public void setmSaturday(String mSaturday) {this.mSaturday = mSaturday; }

    public String getmSunday() {
        return mSunday;
    }

    public void setmSunday(String mSunday) {this.mSunday = mSunday; }


}
