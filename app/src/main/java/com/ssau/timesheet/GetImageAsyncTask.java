package com.ssau.timesheet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.ssau.timesheet.database.RecordContract;
import com.ssau.timesheet.database.RecordHelper;

import java.util.ArrayList;


public class GetImageAsyncTask extends AsyncTask<Long, Void, ArrayList<Bitmap>> {

    private Context context;
    private RecordHelper mDbHelper;
    private SQLiteDatabase db;
    private String[] projection = {
            RecordContract.PhotoEntry.PHOTO,
    };

    public GetImageAsyncTask(Context context) {
        this.context = context;
        mDbHelper = new RecordHelper(context);
    }

    @Override
    protected ArrayList<Bitmap> doInBackground(Long... longs) {
        db = mDbHelper.getWritableDatabase();
        String selection = RecordContract.PhotoEntry.RECORD_ID + " = "+longs[0];
        Cursor cursor = db.query(RecordContract.PhotoEntry.TABLE_NAME, projection, selection, null, null, null, null);
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        while (cursor.moveToNext()) {
            byte[] imgByte = cursor.getBlob(0);
            bitmaps.add(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return bitmaps;
    }
}
