package com.company;

import com.company.model.*;

import java.util.*;

public class SchedulerParser {

    public String parse(SchedulerModel schedulerModel){
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
            result = constructDaysStr(scheduler);
        }

        return result;
    }



    private String isOneTimeForAllDays(Map<SchedulerModel.DAYS_OF_WEEK, DayModel> scheduler) {
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



    /**
     * @return true if
     * 1) empty model (no one day)
     * 2) model contains ALL days and every day has empty FromTo
     */
    private boolean isDailyAroundTheClock(Collection<DayModel> days) {
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

    private int getCountNullDays(Collection<DayModel> days) {
        int countNotNullDays = 0;
        for (DayModel day : days) {
            if (day != null) {
                countNotNullDays++;
            }
        }
        return countNotNullDays;
    }

    private boolean allDayHasEmptyFromTo(Collection<DayModel> days) {
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





    private String constructDaysStr(Map<SchedulerModel.DAYS_OF_WEEK, DayModel> scheduler) {
        System.out.println("\n");
        System.out.println("constructDaysStr");

        List<WeekDay> list = convert(scheduler);
        System.out.println("list weekDays");
        list.forEach(System.out::println);

        List<List<WeekDay>> byBuckets = groupByBuckets(list);
        System.out.println();
        System.out.println("byBuckets");
        byBuckets.forEach(System.out::println);

        System.out.println("building str...");
        String result = "";

        for (int i = 0; i < byBuckets.size(); i++) {
            List<WeekDay> bucket = byBuckets.get(i);
            result +=  buildStr(bucket);    //In one bucket all days has equal listFromTo.
            if (i != byBuckets.size() - 1) {
                result += ", ";
            }
        }

        System.out.println("result = " + result);
        return result;
    }

    private LinkedList<WeekDay> convert(Map<SchedulerModel.DAYS_OF_WEEK, DayModel> scheduler) {
        LinkedList<WeekDay> list = new LinkedList<>();
        for (Map.Entry<SchedulerModel.DAYS_OF_WEEK, DayModel> entry : scheduler.entrySet()) {
            SchedulerModel.DAYS_OF_WEEK dayOfWeek = entry.getKey();
            List<FromTo> fromToList = null;
            if (entry.getValue() != null) {
                fromToList = entry.getValue().getFromToList();
            }

            WeekDay weekDay = new WeekDay(dayOfWeek, fromToList);
            list.add(weekDay);
        }
        return list;
    }

    private List<List<WeekDay>> groupByBuckets(List<WeekDay> list) {
        List<List<WeekDay>> byBuckets = new ArrayList<>();
        List<FromTo> tmpFromToList;
        for (int i = 0; i < list.size(); i++) {
            WeekDay weekDay = list.get(i);

            if (weekDay != null && weekDay.getFromToList() != null) {
                tmpFromToList = new LinkedList<>(weekDay.getFromToList());  //save element fromToList for searching copies

                List<WeekDay> bucket = new LinkedList<>();

                bucket.add(weekDay);    //insert to bucket element
                list.set(i, null);//instead of list.remove(i);         //rm element from src list

                //search weekDays with equals fromToList. If found rm from src list and put to bucket.
                for (int j = 0; j < list.size(); j++) {
                    WeekDay day = list.get(j);
                    if (day != null && day.getFromToList() != null && day.getFromToList().equals(tmpFromToList)) {
                        bucket.add(day);
                        list.set(j, null);  //instead of list.remove(j) cause we cant change size of list in loop by list
                    }
                }

                byBuckets.add(bucket);
            }
        }
        return byBuckets;
    }

    //In one bucket all days has equal listFromTo.
    //Here we just need add separator and build correct string. For time just use ListFromTo of any element
    private String buildStr(List<WeekDay> bucket) {
        StringBuilder resultStr = new StringBuilder();

        boolean flagInRange = false;
        WeekDay currentDay;
        WeekDay nextDay;

        for (int i = 0; i < bucket.size(); i++) {
            currentDay = bucket.get(i);

            if (flagInRange == false) {
                resultStr.append(currentDay.getDayOfWeek().name);
            }

            //next element does not exist -> break
            if (i + 1 > bucket.size() - 1) {
                if (flagInRange) {
                    resultStr.append("-");
                    resultStr.append(currentDay.getDayOfWeek().name);
                }
                break;
            }

            nextDay = bucket.get(i + 1);

            //looking for the next element:difference is one -> set flagInRange
            if (nextDay.getDayOfWeek().ordinal() - currentDay.getDayOfWeek().ordinal() == 1) {
                flagInRange = true;
            }

            // difference more then one ->
            // if in range print current
            // else print ","
            // (else if last element nothing to print) - not need code
            else if (nextDay.getDayOfWeek().ordinal() - currentDay.getDayOfWeek().ordinal() > 1) {
                if (flagInRange) {
                    resultStr.append("-");
                    resultStr.append(currentDay.getDayOfWeek().name);
                    resultStr.append(", "); //because has next element
                    flagInRange = false;
                } else {
                    resultStr.append(", ");
                }
            }

            // if last element and in range -> print last
        }

        String time = getPrintableTime(bucket.get(0).getFromToList());
        resultStr.append(time);
        return resultStr.toString();
    }




    private String getPrintableTime(List<FromTo> fromToList) {
        boolean hasNotNull = false;
        for (FromTo fromTo : fromToList) {
            if (!fromTo.isEmpty()) {
                hasNotNull = true;
            }
        }

        return hasNotNull ? nicePrint(fromToList) : "";
    }

    private String nicePrint(List<FromTo> fromToList) {
        StringBuilder result = new StringBuilder(" ");
        for (int i = 0; i < fromToList.size(); i++) {
            FromTo fromTo = fromToList.get(i);
            From from = fromTo.getFrom();
            To to = fromTo.getTo();

            result.append(from.getHour() + ":" + from.getMinute());
            result.append("-");
            result.append(to.getHour() + ":" + to.getMinute());
            if (i != fromToList.size() - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    private String toReadableString(List<FromTo> tmpFromToList) {
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
}
