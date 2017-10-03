package com.company.model;

import java.util.List;

public class ScheduleDay implements Comparable<ScheduleDay> {
    private WeekDay weekDay;
    private List<FromTo> fromToList;

    public ScheduleDay(WeekDay weekDay, List<FromTo> fromToList) {
        this.weekDay = weekDay;
        this.fromToList = fromToList;   //todo deep copy
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public List<FromTo> getFromToList() {
        return fromToList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduleDay)) return false;

        ScheduleDay that = (ScheduleDay) o;

        if (weekDay != null ? !weekDay.equals(that.weekDay) : that.weekDay != null) return false;
        return fromToList != null ? fromToList.equals(that.fromToList) : that.fromToList == null;
    }

    @Override
    public int hashCode() {
        int result = weekDay != null ? weekDay.hashCode() : 0;
        result = 31 * result + (fromToList != null ? fromToList.hashCode() : 0);
        return result;
    }



    @Override
    public String toString() {
        return "ScheduleDay{" +
                "weekDay=" + weekDay +
                ", fromToList=" + fromToList +
                '}';
    }

    @Override
    public int compareTo(ScheduleDay o) {
        return this.weekDay.compareTo(o.getWeekDay());
    }
}
