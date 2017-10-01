package com.company.model;

public class FromTo {
    private From from;
    private To to;

    public From getFrom() {
        return from;
    }

    public To getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "FromTo{" + from + " - " + to + '}';
    }

    public boolean isEmpty() {
        return from == null && to == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FromTo)) return false;

        FromTo fromTo = (FromTo) o;

        if (from != null ? !from.equals(fromTo.from) : fromTo.from != null) return false;
        return to != null ? to.equals(fromTo.to) : fromTo.to == null;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}
