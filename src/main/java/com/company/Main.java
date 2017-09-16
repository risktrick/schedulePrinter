package com.company;

import com.company.model.DayModel;
import com.company.model.FromTo;
import com.company.model.SchedulerDeserializer;
import com.company.model.SchedulerModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collection;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
        System.out.println("hi");


//        getScheduleString(JsonStrings.JSON1);

    }

    public static String getScheduleString(String jsonStr) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SchedulerModel.class, new SchedulerDeserializer())
                .create();
        SchedulerModel schedulerModel = gson.fromJson(jsonStr, SchedulerModel.class);

        System.out.println(schedulerModel);

        Map<SchedulerModel.DAYS_OF_WEEK, DayModel> scheduler = schedulerModel.getDays();


        if (isDailyAroundTheClock(scheduler)) {
            return Constants.ежедневно;
        } else {
            String time = isOneTimeForAllDays(scheduler);
            if (time != null) {
                return Constants.ежедневно + " " + time;
            }
        }

        return null;
    }

    private static String isOneTimeForAllDays(Map<SchedulerModel.DAYS_OF_WEEK, DayModel> scheduler) {
        Collection<DayModel> days = scheduler.values();




        return null;
    }

    private static boolean isDailyAroundTheClock(Map<SchedulerModel.DAYS_OF_WEEK, DayModel> scheduler) {
        Collection<DayModel> days = scheduler.values();

        if (hasNotNullDay(days) == false || hasNotEmptyDays(days) == false) {
            return true;
        }

        return false;
    }

    private static boolean hasNotNullDay(Collection<DayModel> days) {
        boolean hasNotNull = false;
        for (DayModel day : days) {
            if (day != null) {
                hasNotNull = true;
                break;
            }
        }
        return hasNotNull;
    }

    private static boolean hasNotEmptyDays(Collection<DayModel> days) {
        boolean hasNotEmpty = false;

        metka: for (DayModel day : days) {
            if (day != null) {
                for (FromTo fromTo : day.getFromToList()) {
                    if (fromTo.isEmpty() == false) {
                        hasNotEmpty = true;
                        break metka;
                    }
                }
            }
        }

        return hasNotEmpty;
    }
}
