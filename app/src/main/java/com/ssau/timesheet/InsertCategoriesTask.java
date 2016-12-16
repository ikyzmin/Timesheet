package com.ssau.timesheet;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ssau.timesheet.database.RecordContract;
import com.ssau.timesheet.database.RecordHelper;

/**
 * Created by Илья on 16.12.2016.
 */

public class InsertCategoriesTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private RecordHelper mDbHelper;
    private SQLiteDatabase db;

    public InsertCategoriesTask(Context context) {
        this.context = context;
        mDbHelper = new RecordHelper(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] categories = context.getResources().getStringArray(R.array.categories);
        for (int i = 0; i < categories.length; i++) {
            values.put(RecordContract.CategoryEntry.NAME, categories[i]);
            values.put(RecordContract.CategoryEntry._ID, i);
            try {
                db.insertOrThrow(RecordContract.CategoryEntry.TABLE_NAME, null, values);
            } finally {
                continue;
            }
        }
        return null;
    }
}
