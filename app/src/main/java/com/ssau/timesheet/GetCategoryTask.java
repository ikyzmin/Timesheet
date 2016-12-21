package com.ssau.timesheet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ssau.timesheet.database.Category;
import com.ssau.timesheet.database.RecordContract;
import com.ssau.timesheet.database.RecordHelper;

import java.util.ArrayList;

public class GetCategoryTask extends AsyncTask<Long, Void, Category> {

    private Context context;
    private RecordHelper mDbHelper;
    private SQLiteDatabase db;
    private String[] projection = {
            RecordContract.CategoryEntry._ID,
            RecordContract.CategoryEntry.NAME,
    };

    public GetCategoryTask(Context context) {
        this.context = context;
        mDbHelper = new RecordHelper(context);
    }

    @Override
    protected Category doInBackground(Long... longs) {
        db = mDbHelper.getWritableDatabase();
        String selection = RecordContract.CategoryEntry._ID + " = ?";
        String[] selectionArg = new String[]{longs[0].toString()};
        Cursor cursor = db.query(RecordContract.CategoryEntry.TABLE_NAME, projection, selection, selectionArg, null, null, null);
        Category category = new Category();
        while (cursor.moveToNext()) {
            category.id = cursor.getInt(0);
            category.name = cursor.getString(1);
        }
        cursor.close();
        return category;
    }
}
