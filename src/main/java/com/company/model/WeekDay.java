package com.company.model;

import java.util.List;

public class WeekDay {
    private SchedulerModel.DAYS_OF_WEEK dayOfWeek;
    private List<FromTo> fromToList;

    public WeekDay(SchedulerModel.DAYS_OF_WEEK dayOfWeek, List<FromTo> fromToList) {
        this.dayOfWeek = dayOfWeek;
        this.fromToList = fromToList;   //todo deep copy
    }

    public SchedulerModel.DAYS_OF_WEEK getDayOfWeek() {
        return dayOfWeek;
    }

    public List<FromTo> getFromToList() {
        return fromToList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeekDay)) return false;

        WeekDay weekDay = (WeekDay) o;

        if (dayOfWeek != weekDay.dayOfWeek) return false;
        return fromToList != null ? fromToList.equals(weekDay.fromToList) : weekDay.fromToList == null;
    }

    @Override
    public int hashCode() {
        int result = dayOfWeek != null ? dayOfWeek.hashCode() : 0;
        result = 31 * result + (fromToList != null ? fromToList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeekDay{" +
                "dayOfWeek=" + dayOfWeek +
                ", fromToList=" + fromToList +
                '}';
    }
}
