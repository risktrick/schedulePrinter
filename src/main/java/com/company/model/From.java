package com.company.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class From {

    @SerializedName("hour")
    @Expose
    public String hour;
    @SerializedName("minute")
    @Expose
    public String minute;

    @Override
    public String toString() {
        return "From{" +
                "hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                '}';
    }
}
