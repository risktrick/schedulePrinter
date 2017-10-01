package com.company.model;

import java.util.List;

public class DayModel {
    private List<FromTo> fromToList;

    public DayModel(List<FromTo> fromToList) {
        this.fromToList = fromToList;
    }

    public List<FromTo> getFromToList() {
        return fromToList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayModel)) return false;

        DayModel dayModel = (DayModel) o;

        return fromToList != null ? fromToList.equals(dayModel.fromToList) : dayModel.fromToList == null;
    }

    @Override
    public int hashCode() {
        return fromToList != null ? fromToList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DayModel{" +
                "fromToList=" + fromToList +
                '}';
    }
}
