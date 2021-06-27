package com.hackslash.messsyadmin.Model;

public class AbsenteesListAdapterClass
{

    private String serialNumber;
    private String studentName;
    private int hostelIcon;
    private String rollNumber;

    public AbsenteesListAdapterClass(String serialNumber, String studentName, int hostelIcon, String rollNumber)
    {
        this.serialNumber=serialNumber;
        this.studentName=studentName;
        this.hostelIcon=hostelIcon;
        this.rollNumber=rollNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getHostelIcon() {
        return hostelIcon;
    }

    public String getRollNumber() {
        return rollNumber;
    }

}
