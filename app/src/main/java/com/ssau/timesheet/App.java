package com.ssau.timesheet;

import android.app.Application;

import com.ssau.timesheet.database.RecordHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RecordHelper mDbHelper = new RecordHelper(this);
    }
}
