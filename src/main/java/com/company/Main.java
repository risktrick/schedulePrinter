package com.company;

import com.company.model.DayModel;
import com.company.model.FromTo;
import com.company.model.SchedulerDeserializer;
import com.company.model.SchedulerModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Main {


    public static void main(String[] args) {
        System.out.println("hi");


//        getScheduleString(JsonStrings.JSON1);

    }

    public static String getScheduleString(String jsonStr) {

        SchedulerModel schedulerModel = parseJson(jsonStr);
        System.out.println(schedulerModel);
        Map<SchedulerModel.DAYS_OF_WEEK, DayModel> scheduler = schedulerModel.getDays();


        if (isDailyAroundTheClock(scheduler.values())) {
            return Constants.ежедневно;
        } else {
            String time = isOneTimeForAllDays(scheduler);
            if (time != null) {
                return Constants.ежедневно + " " + time;
            }
        }

        return null;
    }

    private static SchedulerModel parseJson(String jsonStr) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SchedulerModel.class, new SchedulerDeserializer())
                .create();
        SchedulerModel schedulerModel = gson.fromJson(jsonStr, SchedulerModel.class);
        return schedulerModel;
    }

    private static String isOneTimeForAllDays(Map<SchedulerModel.DAYS_OF_WEEK, DayModel> scheduler) {
        Collection<DayModel> days = scheduler.values();


        List<FromTo> tmpFromToList = null;
        int status = 0; //initial state
        for (DayModel day : days) {
            if (day != null) {
                List<FromTo> fromToList = day.getFromToList();

                if (fromToList == null) {
                    continue;
                }


                if (tmpFromToList == null) {    //до этого не было сохранено
                    tmpFromToList = fromToList; //сохраняем
                } else {                                        //  было сохранено
                    if (tmpFromToList.equals(fromToList)) {     //  проверяем совпдают ли они
                        status = 1; //equals was found
                    } else {
                        status = 2; //equals was found but then was found different value
                    }
                }
            }
        }

        System.out.println("status=" + status + " tmpFromToList=" + tmpFromToList);
        if (tmpFromToList != null && status == 1) {
            return toReadableString(tmpFromToList);
        }
        return null;
    }

    private static String toReadableString(List<FromTo> tmpFromToList) {
        String result = null;
        for (FromTo fromTo : tmpFromToList) {
            if (!fromTo.isEmpty()) {
                if (result == null) {
                    result = "";
                }
                result = result +
                        fromTo.getFrom().getHour() + ":" + fromTo.getFrom().getMinute() +
                        "-" +
                        fromTo.getTo().getHour() + ":" + fromTo.getFrom().getMinute();
            }
        }
        return result;
    }


    /**
     * @return true if
     * 1) empty model (no one day)
     * 2) model contains ALL days and every day has empty FromTo
     */
    private static boolean isDailyAroundTheClock(Collection<DayModel> days) {
        boolean result = false;

        //size of collection is always 7, (but always element may be null)
        //dont check just size

        int count = getCountNullDays(days);
        if (count == 7) {
            result = true;
        } else if (count == 0) {
            if (allDayHasEmptyFromTo(days)) {
                result = true;
            }
        }

        return result;
    }

    private static int getCountNullDays(Collection<DayModel> days) {
        int countNotNullDays = 0;
        for (DayModel day : days) {
            if (day != null) {
                countNotNullDays++;
            }
        }
        return countNotNullDays;
    }

    private static boolean allDayHasEmptyFromTo(Collection<DayModel> days) {
        if (days.size() != 7) {
            return false;
        }

        boolean allDayHasEmptyBody = true;
        metka: for (DayModel day : days) {
            if (day != null) {
                for (FromTo fromTo : day.getFromToList()) {
                    if (fromTo.isEmpty() == false) {
                        allDayHasEmptyBody = false;
                        break metka;
                    }
                }
            }
        }
        return allDayHasEmptyBody;
    }
}
