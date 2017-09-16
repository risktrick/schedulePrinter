package com.company.model;

import java.util.List;

public class DayModel {
    List<FromTo> fromToList;

    public DayModel(List<FromTo> fromToList) {
        this.fromToList = fromToList;
    }

    @Override
    public String toString() {
        return "DayModel{" +
                "fromToList=" + fromToList +
                '}';
    }
}
