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

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "To{" + hour + ':' + minute + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof To)) return false;

        To to = (To) o;

        if (hour != null ? !hour.equals(to.hour) : to.hour != null) return false;
        return minute != null ? minute.equals(to.minute) : to.minute == null;
    }

    @Override
    public int hashCode() {
        int result = hour != null ? hour.hashCode() : 0;
        result = 31 * result + (minute != null ? minute.hashCode() : 0);
        return result;
    }
}
