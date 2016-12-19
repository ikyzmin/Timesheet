package com.ssau.timesheet;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;

import com.ssau.timesheet.database.Record;
import com.ssau.timesheet.database.RecordContract;
import com.ssau.timesheet.database.RecordHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class AddRecordTask extends AsyncTask<Record, Void, Long> {

    private Context context;
    private RecordHelper mDbHelper;
    private SQLiteDatabase db;

    public AddRecordTask(Context context) {
        this.context = context;
        mDbHelper = new RecordHelper(context);
    }

    @Override
    protected Long doInBackground(Record... records) {
        db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        ContentValues photoValues = new ContentValues();
        long newPhotoId = Integer.MIN_VALUE;
        try {

                values.put(RecordContract.RecordEntry.DESCRIPTION, records[0].description);
                values.put(RecordContract.RecordEntry.CATEGORY, records[0].categoryId);
                values.put(RecordContract.RecordEntry.TIME,records[0].time);
                values.put(RecordContract.RecordEntry.START, Record.DATE_FORMATTER.format(records[0].start));
                values.put(RecordContract.RecordEntry.END, Record.DATE_FORMATTER.format(records[0].end));
                long newRowId = db.insert(RecordContract.RecordEntry.TABLE_NAME, null, values);
            if (records[0].photo.uri != null) {
                for (Uri uri : records[0].photo.uri) {
                    photoValues.put(RecordContract.PhotoEntry.PHOTO, getBitmapAsByteArray(uri));
                    photoValues.put(RecordContract.PhotoEntry.RECORD_ID,newRowId);
                    newPhotoId = db.insert(RecordContract.PhotoEntry.TABLE_NAME, null, photoValues);
                }
            }
            return newRowId;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Long.MIN_VALUE;
    }

    public byte[] getBitmapAsByteArray(Uri uri) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream iStream = context.getContentResolver().openInputStream(uri);
        byte[] inputData = getBytes(iStream);
        return inputData;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
