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

        ArrayList<WeekDay> days = getCorrectDaysInTrueOrder(locale);
        for (WeekDay day : days) {
            System.out.println("dayOfWeek = " + day);
        }

        SchedulerModel schedulerModel = new SchedulerModel(days);


//        getScheduleString(JsonStrings.JSON1);

    }

    static ArrayList<WeekDay> getCorrectDaysInTrueOrder(Locale locale) {
        ArrayList<WeekDay> daysOnWeek = new ArrayList<>(7);

        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        String[] weekdays = dfs.getShortWeekdays(); //short names. first element (index 0) is empty str. second always sunday.

        for (int i = 1, j = 0; i < weekdays.length && j < WeekDay.JSON_CODES.length; i++, j++) {
            WeekDay weekDay = new WeekDay(WeekDay.JSON_CODES[j], weekdays[i]);
            daysOnWeek.add(weekDay);
        }


        Calendar calendar = Calendar.getInstance(locale);
        int firstDayOfWeek = calendar.getFirstDayOfWeek();  //count from 1 -> monday is 2
        int firstDayIndex = firstDayOfWeek - 1;

        int counter = 1;
        for (int dayIndex = firstDayIndex; dayIndex < daysOnWeek.size(); dayIndex++) {
            WeekDay dayByIndex = daysOnWeek.get(dayIndex);
            dayByIndex.setValue(counter);
            counter++;
        }
        for (int dayIndex = 0; dayIndex < firstDayIndex; dayIndex++) {
            WeekDay dayByIndex = daysOnWeek.get(dayIndex);
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

    private static SchedulerModel parseJson(SchedulerModel schedulerModel, String jsonStr) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SchedulerModel.class, new SchedulerDeserializer(schedulerModel))
                .create();
        return gson.fromJson(jsonStr, SchedulerModel.class);
    }


}
