package com.hackslash.messsyadmin.Model;

public class AbsenteesListAdapterClass
{

    String studentName;
    String hostelIcon;
    String rollNumber;

    AbsenteesListAdapterClass() {}

    public AbsenteesListAdapterClass( String studentName, String hostelIcon, String rollNumber)
    {
        this.studentName=studentName;
        this.hostelIcon=hostelIcon;
        this.rollNumber=rollNumber;
    }

    public String getStudentName() { return studentName; }

    public String getHostelIcon() { return hostelIcon; }

    public String getRollNumber() { return rollNumber; }

    public void setStudentName(String studentName) { this.studentName = studentName; }

    public void setHostelIcon(String hostelIcon) { this.hostelIcon = hostelIcon; }

    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }


}
