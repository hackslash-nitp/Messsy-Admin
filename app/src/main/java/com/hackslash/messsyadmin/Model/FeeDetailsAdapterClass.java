package com.hackslash.messsyadmin.Model;

public class FeeDetailsAdapterClass {

    private String serial_number;
    private String StudentName;
    private String RollNumber;

    public FeeDetailsAdapterClass(String serial_number, String StudentName, String RollNumber)
    {
        this.serial_number=serial_number;
        this.RollNumber=RollNumber;
        this.StudentName=StudentName;
    }

    public String getRollNumber() {
        return RollNumber;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public String getStudentName() {
        return StudentName;
    }
}
