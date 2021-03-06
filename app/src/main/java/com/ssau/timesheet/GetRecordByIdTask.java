package com.ssau.timesheet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ssau.timesheet.database.Record;
import com.ssau.timesheet.database.RecordContract;
import com.ssau.timesheet.database.RecordHelper;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Илья on 19.12.2016.
 */

public class GetRecordByIdTask extends AsyncTask<Long,Void,Record> {

    private Context context;
    private RecordHelper mDbHelper;
    private SQLiteDatabase db;
    private String[] projection = {
            RecordContract.RecordEntry._ID,
            RecordContract.RecordEntry.CATEGORY,
            RecordContract.RecordEntry.DESCRIPTION,
            RecordContract.RecordEntry.START,
            RecordContract.RecordEntry.END,
            RecordContract.RecordEntry.TIME
    };

    public GetRecordByIdTask(Context context) {
        this.context = context;
        mDbHelper = new RecordHelper(context);
    }

    @Override
    protected Record doInBackground(Long[] objects) {
        db = mDbHelper.getWritableDatabase();
        String selection=  RecordContract.RecordEntry._ID+"="+objects[0].toString();
        String [] selectionArg=  {objects[0].toString()};
        com.ssau.timesheet.database.Record record = null;
        Cursor c = db.query(
                RecordContract.RecordEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null
        );
        while (c.moveToNext()) {
            record = new com.ssau.timesheet.database.Record();
            record.id = c.getInt(0);
            record.description = c.getString(2);
            record.categoryId = c.getInt(1);
            try {
                record.start = !c.isNull(3) ? com.ssau.timesheet.database.Record.DATE_FORMATTER.parse(c.getString(3)) : null;
                record.end = !c.isNull(4) ? com.ssau.timesheet.database.Record.DATE_FORMATTER.parse(c.getString(4)) : null;
                record.time = !c.isNull(5) ? c.getInt(5) : -1;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            finally {
                c.close();
            }
        }
        return record;
    }
}
