package com.company.model;

import java.util.List;

public class DayModel {
    List<FromTo> fromToList;

    public DayModel(List<FromTo> fromToList) {
        this.fromToList = fromToList;
    }

    public List<FromTo> getFromToList() {
        return fromToList;
    }

    @Override
    public String toString() {
        return "DayModel{" +
                "fromToList=" + fromToList +
                '}';
    }
}
