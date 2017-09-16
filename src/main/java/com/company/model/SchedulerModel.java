package com.company.model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SchedulerModel {

    public enum DAYS_OF_WEEK
    {   MON("mon"),
        TUE("tue"),
        WED("wed"),
        THU("thu"),
        FRI("fri"),
        SAT("sat"),
        SUN("sun");

        String code;
        DAYS_OF_WEEK(String code) {
            this.code = code;
        }
    };

    private final TreeMap<DAYS_OF_WEEK, DayModel> daysMap = new TreeMap<>();

    public SchedulerModel() {
        daysMap.put(DAYS_OF_WEEK.MON, null);
        daysMap.put(DAYS_OF_WEEK.TUE, null);
        daysMap.put(DAYS_OF_WEEK.WED, null);
        daysMap.put(DAYS_OF_WEEK.THU, null);
        daysMap.put(DAYS_OF_WEEK.FRI, null);
        daysMap.put(DAYS_OF_WEEK.SAT, null);
        daysMap.put(DAYS_OF_WEEK.SUN, null);

        String a = DAYS_OF_WEEK.MON.code;
    }

    public void put(DAYS_OF_WEEK day, DayModel dayModel) {
        daysMap.put(day, dayModel);
    }

    public Map<DAYS_OF_WEEK, DayModel> getDays() {
        return daysMap;
    }

    @Override
    public String toString() {
        String result = "SchedulerModel{" + "\r\n";

        Set<Map.Entry<DAYS_OF_WEEK, DayModel>> entries = daysMap.entrySet();
        for (Map.Entry<DAYS_OF_WEEK, DayModel> entry : entries) {
            result = result.concat(entry.getKey() + "=" + entry.getValue() + ", \r\n");
        }
        result = result.concat("}");
        return result;
    }
}
