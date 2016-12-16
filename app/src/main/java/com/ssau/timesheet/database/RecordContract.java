package com.ssau.timesheet.database;


import android.provider.BaseColumns;

public class RecordContract {

    public static abstract class RecordEntry implements BaseColumns {
        public static final String TABLE_NAME = "record";
        public static final String DESCRIPTION = "description";
        public static final String START = "start";
        public static final String END = "end";
        public static final String TIME = "time";
        public static final String PHOTOS_ID = "photos_id";
        public static final String CATEGORY = "category_id";

        public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DESCRIPTION + " TEXT," + START + " TEXT," + END + " TEXT," + TIME + " INTEGER," + PHOTOS_ID + " INTEGER REFERENCES " + PhotoEntry.TABLE_NAME + " ON DELETE CASCADE," + CATEGORY + " INTEGER " + ")";
        public static final String DELETE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class PhotoEntry implements BaseColumns {
        public static final String TABLE_NAME = "photo";
        public static final String PHOTO = "_photo";
        public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+PHOTO+" BLOB "+")";
        public static final String DELETE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static abstract class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String NAME = "name";
        public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, "+NAME+" TEXT "+")";
        public static final String DELETE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
