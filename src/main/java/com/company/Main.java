package com.company;

import com.company.model.DayModel;
import com.company.model.SchedulerDeserializer;
import com.company.model.SchedulerModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class Main {


    public static void main(String[] args) {
        System.out.println("hi");

        Gson gson = new GsonBuilder()
                        .registerTypeAdapter(SchedulerModel.class, new SchedulerDeserializer())
                        .create();
        SchedulerModel schedulerModel = gson.fromJson(JsonStrings.JSON4, SchedulerModel.class);

        System.out.println(schedulerModel);

        Map<SchedulerModel.DAYS_OF_WEEK, DayModel> scheduler = schedulerModel.getDays();


    }
}
