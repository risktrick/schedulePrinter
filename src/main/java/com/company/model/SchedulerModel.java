package com.company.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SchedulerModel {
    public static final String MON_STR = "mon";
    public static final String TUE_STR = "tue";
    public static final String WED_STR = "wed";
    public static final String THU_STR = "thu";
    public static final String FRI_STR = "fri";
    public static final String SAT_STR = "sat";
    public static final String SUN_STR = "sun";

    private final HashMap<String, DayModel> hashMap = new HashMap<>(7);

    public SchedulerModel() {
        hashMap.put(MON_STR, null);
    }

    public void put(String day, DayModel dayModel) {
        hashMap.put(day, dayModel);
    }

    @Override
    public String toString() {
        String result = "SchedulerModel{" + "\r\n";

        Set<Map.Entry<String, DayModel>> entries = hashMap.entrySet();
        for (Map.Entry<String, DayModel> entry : entries) {
            result = result.concat(entry.getKey() + "=" + entry.getValue() + ", \r\n");
        }
        result = result.concat("}");

        return result;
    }
}
