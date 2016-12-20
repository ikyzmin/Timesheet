package com.ssau.timesheet.database;


import android.provider.BaseColumns;

public class RecordContract {

    public static abstract class RecordEntry implements BaseColumns {
        public static final String TABLE_NAME = "record";
        public static final String DESCRIPTION = "description";
        public static final String START = "start";
        public static final String END = "end";
        public static final String TIME = "time";
        public static final String CATEGORY = "category_id";

        public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DESCRIPTION + " TEXT," + START + " TEXT," + END + " TEXT," + TIME + " INTEGER," + CATEGORY + " INTEGER " + ")";
        public static final String DELETE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String GET_BY_CATEGORY_ORDER = "SELECT "+ CategoryEntry.TABLE_NAME+"."+CategoryEntry.NAME+",count(" + CATEGORY + ") FROM " + TABLE_NAME +" INNER JOIN "+ CategoryEntry.TABLE_NAME +" ON "+CATEGORY+" = "+CategoryEntry.TABLE_NAME+"."+CategoryEntry._ID+" GROUP BY " + CATEGORY + " ORDER BY count(" + CATEGORY + ") DESC";
        public static final String GET_BY_CATEGORY_ORDER_BETWEEN = "SELECT "+ CategoryEntry.TABLE_NAME+"."+CategoryEntry.NAME+",count(" + CATEGORY + ") FROM " + TABLE_NAME +" INNER JOIN "+ CategoryEntry.TABLE_NAME +" ON "+CATEGORY+" = "+CategoryEntry.TABLE_NAME+"."+CategoryEntry._ID+" WHERE "+START+" >= \"%s\" "+" AND "+END+" <= \"%s\""+" GROUP BY " + CATEGORY + " ORDER BY count(" + CATEGORY + ") DESC";

        public static final String GET_BY_CATEGORY_ORDER_TIME = "SELECT "+ CategoryEntry.TABLE_NAME+"."+CategoryEntry.NAME+",sum(" + TIME + ") FROM " + TABLE_NAME +" INNER JOIN "+ CategoryEntry.TABLE_NAME +" ON "+CATEGORY+" = "+CategoryEntry.TABLE_NAME+"."+CategoryEntry._ID+" GROUP BY " + CATEGORY + " ORDER BY sum(" + TIME + ") DESC";
        public static final String GET_BY_CATEGORY_ORDER_TIME_BETWEEN = "SELECT "+ CategoryEntry.TABLE_NAME+"."+CategoryEntry.NAME+",sum(" + TIME + ") FROM " + TABLE_NAME +" INNER JOIN "+ CategoryEntry.TABLE_NAME +" ON "+CATEGORY+" = "+CategoryEntry.TABLE_NAME+"."+CategoryEntry._ID+" WHERE "+START+" >= \"%s\" "+" AND "+END+" <= \"%s\""+" GROUP BY " + CATEGORY + " ORDER BY sum(" + TIME + ") DESC";

    }

    public static abstract class PhotoEntry implements BaseColumns {
        public static final String TABLE_NAME = "photo";
        public static final String PHOTO = "_photo";
        public static final String RECORD_ID = "record_id";
        public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PHOTO + " BLOB,"+RECORD_ID+" INTEGER,"+" FOREIGN KEY (" + RECORD_ID + ") REFERENCES " + RecordEntry.TABLE_NAME + "(" + RecordEntry._ID + ") ON DELETE CASCADE" + " ) ";
        public static final String DELETE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static abstract class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String NAME = "name";
        public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT " + ")";
        public static final String DELETE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
