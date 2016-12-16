package com.ssau.timesheet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ssau.timesheet.database.Category;
import com.ssau.timesheet.database.RecordContract;
import com.ssau.timesheet.database.RecordHelper;

import java.util.ArrayList;

public class GetCategoriesTask extends AsyncTask<Void, Void, ArrayList<Category>> {

    private Context context;
    private RecordHelper mDbHelper;
    private SQLiteDatabase db;
    private String[] projection = {
            RecordContract.CategoryEntry._ID,
            RecordContract.CategoryEntry.NAME,
    };

    public GetCategoriesTask(Context context) {
        this.context = context;
        mDbHelper = new RecordHelper(context);
    }

    @Override
    protected ArrayList<Category> doInBackground(Void... voids) {
        db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.query(RecordContract.CategoryEntry.TABLE_NAME, projection, null, null, null, null, null);
        ArrayList<Category> categories = new ArrayList<>();
        while (cursor.moveToNext()) {
            Category category = new Category();
            category.id = cursor.getInt(0);
            category.name = cursor.getString(1);
            categories.add(category);
        }
        return categories;
    }
}
