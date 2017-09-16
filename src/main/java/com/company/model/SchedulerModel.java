package com.company.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchedulerModel {
    public static final String MON_STR = "mon";
    public static final String TUE_STR = "tue";
    public static final String WED_STR = "wed";
    public static final String THU_STR = "thu";
    public static final String FRI_STR = "fri";
    public static final String SAT_STR = "sat";
    public static final String SUN_STR = "sun";

    public DayModel mon;
    public DayModel tue;
    public DayModel wed;
    public DayModel thu;
    public DayModel fri;
    public DayModel sat;
    public DayModel sun;

    @Override
    public String toString() {
        return "SchedulerModel{" + "\r\n" +
                "mon=" + mon + ",\r\n" +
                "tue=" + tue + ",\r\n" +
                "wed=" + wed + ",\r\n" +
                "thu=" + thu + ",\r\n" +
                "fri=" + fri + ",\r\n" +
                "sat=" + sat + ",\r\n" +
                "sun=" + sun + ",\r\n" +'}';
    }
}
