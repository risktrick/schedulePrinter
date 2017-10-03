package com.company.model;

import java.util.*;

public class SchedulerModel {

    private final TreeMap<WeekDay, ScheduleDay> daysMap = new TreeMap<>();

    public SchedulerModel(ArrayList<WeekDay> daysOnWeek) {
        for (WeekDay weekDay : daysOnWeek) {
            daysMap.put(weekDay, null);
        }
    }

    public Map<WeekDay, ScheduleDay> getDaysMap() {
        return daysMap;
    }

    public void put(String jsonCode, List<FromTo> fromToList) {
        Set<WeekDay> weekDays = daysMap.keySet();

        WeekDay foundedWeekDay = null;
        for (WeekDay weekDay : weekDays) {
            if (weekDay.getJsonCode().contentEquals(jsonCode)) {
                foundedWeekDay = weekDay;
                break;
            }
        }

        if (foundedWeekDay != null) {
            ScheduleDay scheduleDay = new ScheduleDay(foundedWeekDay, fromToList);
            daysMap.put(foundedWeekDay, scheduleDay);
        }
    }

    @Override
    public String toString() {
        String result = "SchedulerModel{" + "\n";

        Set<Map.Entry<WeekDay, ScheduleDay>> entries = daysMap.entrySet();
        for (Map.Entry<WeekDay, ScheduleDay> entry : entries) {
            result = result.concat(entry.getKey() + "=" + entry.getValue() + ", \r\n");
        }
        result = result.concat("}");
        return result;
    }
}
