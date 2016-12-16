package com.ssau.timesheet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ssau.timesheet.database.Record;
import com.ssau.timesheet.database.RecordContract;
import com.ssau.timesheet.database.RecordHelper;

import java.util.ArrayList;


public class GetRecordsTask extends AsyncTask<Void, Void, ArrayList<Record>> {

    private Context context;
    private RecordHelper mDbHelper;
    private SQLiteDatabase db;
    private String[] projection = {
            RecordContract.RecordEntry.CATEGORY,
            RecordContract.RecordEntry.PHOTOS_ID,
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
            record.description = c.getString(2);
            record.photoId = c.getInt(1);
            records.add(record);
        }
        return records;
    }
}
