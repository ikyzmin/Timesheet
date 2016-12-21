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


public class GetRecordsTask extends AsyncTask<Void, Void, ArrayList<Record>> {

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

    public GetRecordsTask(Context context) {
        this.context = context;
        mDbHelper = new RecordHelper(context);
    }

    @Override
    protected ArrayList<Record> doInBackground(Void... voids) {
        db = mDbHelper.getWritableDatabase();
        ArrayList<Record> records = new ArrayList<>();
        Cursor c = db.query(
                RecordContract.RecordEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        while (c.moveToNext()) {
            Record record = new Record();
            record.id = c.getInt(0);
            record.description = c.getString(2);
            record.categoryId = c.getInt(1);
            try {
                record.start = !c.isNull(3) ? Record.DATE_FORMATTER.parse(c.getString(3)) : null;
                record.end = !c.isNull(4) ? Record.DATE_FORMATTER.parse(c.getString(4)) : null;
                record.time = !c.isNull(5) ? c.getInt(5) : -1;
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                records.add(record);
               // c.close();
            }
        }
        return records;
    }
}
