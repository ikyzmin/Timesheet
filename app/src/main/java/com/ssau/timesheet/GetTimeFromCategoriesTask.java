package com.ssau.timesheet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ssau.timesheet.database.MaxTimeCategorySelection;
import com.ssau.timesheet.database.Record;
import com.ssau.timesheet.database.RecordContract;
import com.ssau.timesheet.database.RecordHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Илья on 21.12.2016.
 */

public class GetTimeFromCategoriesTask extends AsyncTask<Object, Void, Integer> {

    private Context context;
    private RecordHelper mDbHelper;
    private SQLiteDatabase db;

    public GetTimeFromCategoriesTask(Context context) {
        this.context = context;
        mDbHelper = new RecordHelper(context);
    }

    @Override
    protected Integer doInBackground(Object... objects) {
        int result = 0;
        if (objects[0] != null && ((String[]) objects[0]).length != 0) {
            String inClause = createINClause((String[]) objects[0]);
            Date[] dates = null;
            if (objects.length == 2) {
                dates = (Date[]) objects[1];
            }
            if (mDbHelper==null){
                mDbHelper = new RecordHelper(context);
            }
            db = mDbHelper.getWritableDatabase();
            ArrayList<MaxTimeCategorySelection> records = new ArrayList<>();
            Cursor c = null;
            if (dates != null && dates.length != 0 && dates[0] != null && dates[1] != null) {
                c = db.rawQuery(String.format(RecordContract.CategoryEntry.GET_TIME_BY_CATEGORIES_BETWEEN, Record.DATE_FORMATTER.format(dates[0]), Record.DATE_FORMATTER.format(dates[1]), inClause), null);
            } else {
                c = db.rawQuery(String.format(RecordContract.CategoryEntry.GET_TIME_BY_CATEGORIES, inClause), null);
            }
            while (c.moveToNext()) {
                result = c.getInt(0);
            }
            c.close();
        }
        return result;

    }

    private String createINClause(String... ids) {
        StringBuilder stringBuilder = new StringBuilder("(");
        for (int i = 0; i < ids.length; i++) {
            if (i != ids.length - 1) {
                stringBuilder.append(ids[i] + ",");
            } else {
                stringBuilder.append(ids[i] + ")");
            }
        }
        return stringBuilder.toString();
    }
}
