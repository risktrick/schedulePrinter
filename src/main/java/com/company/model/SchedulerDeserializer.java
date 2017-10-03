package com.company.model;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SchedulerDeserializer implements JsonDeserializer<SchedulerModel> {

    private SchedulerModel schedulerModel;

    public SchedulerDeserializer(SchedulerModel schedulerModel) {
        this.schedulerModel = schedulerModel;
    }

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
        Gson gson = new Gson();

        for (Map.Entry<String, JsonElement> entriesDay : entriesDays) {
            String key = entriesDay.getKey();
            JsonArray jsonArray = entriesDay.getValue().getAsJsonArray();

            for (String jsonCode : WeekDay.JSON_CODES) {
                if (key.contentEquals(jsonCode)) {
                    schedulerModel.put(key, jsonArrayToDayModel(gson, jsonArray));
                }
            }
        }
        return schedulerModel;
    }

    private List<FromTo> jsonArrayToDayModel(Gson gson, JsonArray jsonArray) {
        FromTo[] fromTos = gson.fromJson(jsonArray, FromTo[].class);
        List<FromTo> fromToList = Arrays.asList(fromTos);
        return fromToList;
    }

}