package com.ssau.timesheet.database;

public class MostOftenCategorySelection {
    public String category;
    public int count;

    @Override
    public String toString() {
        return category+"\n"+count+" times";
    }
}
