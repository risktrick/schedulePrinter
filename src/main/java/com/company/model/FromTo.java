package com.company.model;

public class FromTo {
    From from;
    To to;

    @Override
    public String toString() {
        return "FromTo{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    public boolean isEmpty() {
        return from == null && to == null;
    }
}
