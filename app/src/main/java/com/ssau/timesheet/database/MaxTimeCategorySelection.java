package com.ssau.timesheet.database;
public class MaxTimeCategorySelection {
    public String category;
    public int time;

    @Override
    public String toString() {
        return category + "\n" + time / 1000 / 60 / 60 % 24 + "h. " + time / 1000 / 60 % 60 + "m.";
    }
}
