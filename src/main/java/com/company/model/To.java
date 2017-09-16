package com.company.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class To {

    @SerializedName("hour")
    @Expose
    public String hour;
    @SerializedName("minute")
    @Expose
    public String minute;

    @Override
    public String toString() {
        return "To{" +
                "hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                '}';
    }
}
