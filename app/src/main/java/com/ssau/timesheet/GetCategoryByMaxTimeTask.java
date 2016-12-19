package com.ssau.timesheet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ssau.timesheet.database.MaxTimeCategorySelection;
import com.ssau.timesheet.database.MostOftenCategorySelection;
import com.ssau.timesheet.database.Record;
import com.ssau.timesheet.database.RecordContract;
import com.ssau.timesheet.database.RecordHelper;

import java.util.ArrayList;
import java.util.Date;

public class GetCategoryByMaxTimeTask extends AsyncTask<Date,Void,ArrayList<MaxTimeCategorySelection>> {
    private Context context;
    private RecordHelper mDbHelper;
    private SQLiteDatabase db;
    private String[] projection = {
            RecordContract.RecordEntry.CATEGORY,
            RecordContract.RecordEntry.DESCRIPTION,
            RecordContract.RecordEntry.START,
            RecordContract.RecordEntry.END,
            RecordContract.RecordEntry.TIME
    };

    public GetCategoryByMaxTimeTask(Context context) {
        this.context = context;
        mDbHelper = new RecordHelper(context);
    }

    @Override
    protected ArrayList<MaxTimeCategorySelection> doInBackground(Date... dates) {
        db = mDbHelper.getWritableDatabase();
        ArrayList<MaxTimeCategorySelection> records = new ArrayList<>();
        Cursor c = null;
        if (dates != null && dates.length != 0 && dates[0] != null && dates[1] != null) {
            c = db.rawQuery(String.format(RecordContract.RecordEntry.GET_BY_CATEGORY_ORDER_TIME_BETWEEN, Record.DATE_FORMATTER.format(dates[0]), Record.DATE_FORMATTER.format(dates[1])), null);
        } else {
            c = db.rawQuery(RecordContract.RecordEntry.GET_BY_CATEGORY_ORDER_TIME, null);
        }
        while (c.moveToNext()) {
            MaxTimeCategorySelection record = new MaxTimeCategorySelection();
            record.category = c.getString(0);
            record.time = c.getInt(1);
            records.add(record);
        }
        c.close();
        return records;
    }
}
