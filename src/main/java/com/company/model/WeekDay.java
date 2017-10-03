package com.company.model;

public class WeekDay implements Comparable<WeekDay> {

    public static final String[] JSON_CODES = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

    private String jsonCode;
    private String name;
    private int value;

    public WeekDay(String jsonCode, String name) {
        this.jsonCode = jsonCode;
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getJsonCode() {
        return jsonCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "WeekDay{" +
                "jsonCode='" + jsonCode + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeekDay)) return false;

        WeekDay weekDay = (WeekDay) o;

        if (value != weekDay.value) return false;
        if (jsonCode != null ? !jsonCode.equals(weekDay.jsonCode) : weekDay.jsonCode != null) return false;
        return name != null ? name.equals(weekDay.name) : weekDay.name == null;
    }

    @Override
    public int hashCode() {
        int result = jsonCode != null ? jsonCode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + value;
        return result;
    }

    @Override
    public int compareTo(WeekDay o) {
        if (o.getValue() == this.getValue()) {
            return 0;
        } else if (o.getValue() > this.getValue()) {
            return 1;
        } else {
            return -1;
        }
    }
}
