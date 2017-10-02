package com.company;

import com.company.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormatSymbols;
import java.util.*;

public class Main {


    public static void main(String[] args) {
        System.out.println("hi");

        Locale locale = Locale.getDefault();

        ArrayList<DayOnWeek> days = getCorrectDaysInTrueOrder(locale);
        for (DayOnWeek day : days) {
            System.out.println("dayOfWeek = " + day);
        }


//        getScheduleString(JsonStrings.JSON1);

    }

    static ArrayList<DayOnWeek> getCorrectDaysInTrueOrder(Locale locale) {
        ArrayList<DayOnWeek> daysOnWeek = new ArrayList<>(7);

        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        String[] weekdays = dfs.getShortWeekdays(); //short names. first element (index 0) is empty str. second always sunday.
        String[] jsonCodes = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

        for (int i = 1, j = 0; i < weekdays.length && j < jsonCodes.length; i++, j++) {
            DayOnWeek dayOnWeek = new DayOnWeek(jsonCodes[j], weekdays[i]);
            daysOnWeek.add(dayOnWeek);
        }


        Calendar calendar = Calendar.getInstance(locale);
        int firstDayOfWeek = calendar.getFirstDayOfWeek();  //count from 1 -> monday is 2
        int firstDayIndex = firstDayOfWeek - 1;

        int counter = 1;
        for (int dayIndex = firstDayIndex; dayIndex < daysOnWeek.size(); dayIndex++) {
            DayOnWeek dayByIndex = daysOnWeek.get(dayIndex);
            dayByIndex.setValue(counter);
            counter++;
        }
        for (int dayIndex = 0; dayIndex < firstDayIndex; dayIndex++) {
            DayOnWeek dayByIndex = daysOnWeek.get(dayIndex);
            dayByIndex.setValue(counter);
            counter++;
        }

        return daysOnWeek;
    }


    public static String getScheduleString(String jsonStr) {

        SchedulerModel schedulerModel = parseJson(jsonStr);
        System.out.println(schedulerModel);

        SchedulerParser parser = new SchedulerParser();
        return parser.parse(schedulerModel);
    }

    private static SchedulerModel parseJson(String jsonStr) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SchedulerModel.class, new SchedulerDeserializer())
                .create();
        SchedulerModel schedulerModel = gson.fromJson(jsonStr, SchedulerModel.class);
        return schedulerModel;
    }


}
