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

        String result = null;

        if (isDailyAroundTheClock(scheduler.values())) {
            result = Constants.ежедневно;
        }

        String time = isOneTimeForAllDays(scheduler);
        if (time != null) {
            result = Constants.ежедневно + " " + time;
        }


        // здесь -> модель не пустая,
        // в модели либо все дни и разное время,
        // либо не все дни (и возможно так же разное время)
        if (result == null) {
//            result = walkByDays(scheduler);
            result = constructDaysStr(scheduler);
        }

        return result;
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

//        System.out.println("status=" + status + " tmpFromToList=" + tmpFromToList);
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

    private static String walkByDays(Map<SchedulerModel.DAYS_OF_WEEK, DayModel> scheduler) {
        System.out.println("\n\n");
        System.out.println("walkByDays");

        LinkedList<Map.Entry<SchedulerModel.DAYS_OF_WEEK, DayModel>> linkedList = new LinkedList<>(scheduler.entrySet());
        ListIterator<Map.Entry<SchedulerModel.DAYS_OF_WEEK, DayModel>> listIterator = linkedList.listIterator();

        String result = "";
        boolean flagInRange = true;
        boolean flagBreakRange = false;
        Map.Entry<SchedulerModel.DAYS_OF_WEEK, DayModel> current;


        //надо сохранять в список пока следующий день не равен null
        //если следующий после Null (или череды null) не null -> для них новый список
        //потом выводить из этого списка первый - последний
        //для времени соответственно прежде чем выводить аналогично пробежаться
        while (listIterator.hasNext()) {
            current = listIterator.next();
            DayModel currentDayModel = current.getValue();
            String currentDayName = current.getKey().name;

            if (currentDayModel != null && currentDayModel.getFromToList() != null) {
                if (flagInRange) {
                    flagInRange = false;
                    result = result + currentDayName;
                } else {
                    if (listIterator.hasNext()) {
                        Map.Entry<SchedulerModel.DAYS_OF_WEEK, DayModel> nextItem = listIterator.next();
                        DayModel nextDayModel = nextItem.getValue();

                        if (nextDayModel == null) {
                            result = result + "-" + currentDayName;
                        }
                    }
                }
            }
        }

//        for (Map.Entry<SchedulerModel.DAYS_OF_WEEK, DayModel> entry : entries) {
//            if (dayModel != null && dayModel.getFromToList() != null) {
//                if (flagInRange) {
//                    flagInRange = false;
//                    result = result + key.name;
//                }
//
//                if (next == null) {
//                    result = result + "-" + current;
//                }
//            }
//        }

        return result;
    }

    private static String constructDaysStr(Map<SchedulerModel.DAYS_OF_WEEK, DayModel> scheduler) {
        System.out.println("\n");
        System.out.println("constructDaysStr");
        LinkedList<Map.Entry<SchedulerModel.DAYS_OF_WEEK, DayModel>> listAllDays = new LinkedList<>(scheduler.entrySet());

        Iterator<Map.Entry<SchedulerModel.DAYS_OF_WEEK, DayModel>> iterator = listAllDays.iterator();

        DayModel tmpDayModel;
        while (iterator.hasNext()) {
            Map.Entry<SchedulerModel.DAYS_OF_WEEK, DayModel> dayEntry = iterator.next();

        }

        List<>


//        Map.Entry<SchedulerModel.DAYS_OF_WEEK, DayModel> day

        return null;
    }
}
