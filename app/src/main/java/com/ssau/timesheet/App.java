package com.ssau.timesheet;

import android.app.Application;

import com.ssau.timesheet.database.Record;
import com.ssau.timesheet.database.RecordHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new InsertCategoriesTask(this).execute();
    }
}
