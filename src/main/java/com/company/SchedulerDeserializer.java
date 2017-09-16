package com.company;

import com.company.model.DayModel;
import com.company.model.FromTo;
import com.company.model.SchedulerModel;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

public class SchedulerDeserializer implements JsonDeserializer<SchedulerModel> {

    public SchedulerModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Set<Map.Entry<String, JsonElement>> entriesDays = getDays(json);

        SchedulerModel schedulerModel = walkByDays(entriesDays);

        return schedulerModel;
    }

    private Set<Map.Entry<String, JsonElement>> getDays(JsonElement json) {
        if (json.isJsonObject()) {
            Set<Map.Entry<String, JsonElement>> scheduleEntry = json.getAsJsonObject().entrySet();

//            System.out.println("scheduleEntry size: " + scheduleEntry.size());
            for (Map.Entry<String, JsonElement> entry : scheduleEntry) {

//                System.out.println("key=" + entry.getKey() + "   value=" + entry.getValue());

                JsonObject daysJson = entry.getValue().getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entriesDays = daysJson.entrySet();

                return entriesDays;
            }
        }
        return null;
    }

    private SchedulerModel walkByDays(Iterable<? extends Map.Entry<String, JsonElement>> entriesDays) {
        SchedulerModel schedulerModel = new SchedulerModel();
        Gson gson = new Gson();

        for (Map.Entry<String, JsonElement> entriesDay : entriesDays) {
            String key = entriesDay.getKey();
            JsonArray jsonArray = entriesDay.getValue().getAsJsonArray();
            if (key.contentEquals(SchedulerModel.MON_STR)) {
                schedulerModel.mon = jsonArrayToDayModel(gson, jsonArray);
            } else if (key.contentEquals(SchedulerModel.TUE_STR)) {
                schedulerModel.tue = jsonArrayToDayModel(gson, jsonArray);
            } else if (key.contentEquals(SchedulerModel.WED_STR)) {
                schedulerModel.wed = jsonArrayToDayModel(gson, jsonArray);
            } else if (key.contentEquals(SchedulerModel.THU_STR)) {
                schedulerModel.thu = jsonArrayToDayModel(gson, jsonArray);
            } else if (key.contentEquals(SchedulerModel.FRI_STR)) {
                schedulerModel.fri = jsonArrayToDayModel(gson, jsonArray);
            } else if (key.contentEquals(SchedulerModel.SAT_STR)) {
                schedulerModel.sat = jsonArrayToDayModel(gson, jsonArray);
            } else if (key.contentEquals(SchedulerModel.SUN_STR)) {
                schedulerModel.sun = jsonArrayToDayModel(gson, jsonArray);
            }
        }
        return schedulerModel;
    }

    private DayModel jsonArrayToDayModel(Gson gson, JsonArray jsonArray) {
        FromTo[] fromTos = gson.fromJson(jsonArray, FromTo[].class);
        List<FromTo> fromToList = Arrays.asList(fromTos);
        DayModel day = new DayModel(fromToList);
        return day;
    }

}