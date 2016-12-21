package com.ssau.timesheet.database;

import android.graphics.Bitmap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Record {

    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public static final DateFormat ITEM_FORMATTER = new SimpleDateFormat("dd/MMM/yy HH:mm");
    public static final DateFormat STATISTIC_FORMATTER = new SimpleDateFormat("dd/MMM/yy");
    public  static  final DateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");


    public String description;
    public Date start;
    public Date end;
    public String categoryName;
    public int categoryId;
    public Photo photo;
    public int time;
    public int id;
    public ArrayList<Bitmap> bitmaps;

}
