package com.company;

public class DayOnWeek implements Comparable<DayOnWeek>{
    private String jsonCode;
    private String name;
    private int value;

    public DayOnWeek(String jsonCode, String name) {
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

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DayOnWeek{" +
                "jsonCode='" + jsonCode + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayOnWeek)) return false;

        DayOnWeek dayOnWeek = (DayOnWeek) o;

        if (value != dayOnWeek.value) return false;
        if (jsonCode != null ? !jsonCode.equals(dayOnWeek.jsonCode) : dayOnWeek.jsonCode != null) return false;
        return name != null ? name.equals(dayOnWeek.name) : dayOnWeek.name == null;
    }

    @Override
    public int hashCode() {
        int result = jsonCode != null ? jsonCode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + value;
        return result;
    }

    @Override
    public int compareTo(DayOnWeek o) {
        if (o.getValue() == this.getValue()) {
            return 0;
        } else if (o.getValue() > this.getValue()) {
            return 1;
        } else {
            return -1;
        }
    }
}
