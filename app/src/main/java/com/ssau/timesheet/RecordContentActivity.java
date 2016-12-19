package com.ssau.timesheet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.widget.GridView;

import com.ssau.timesheet.adapter.ImageAdapter;
import com.ssau.timesheet.adapter.RecordAdapter;
import com.ssau.timesheet.database.Record;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;


public class RecordContentActivity extends AppCompatActivity {

    public final static String RECORD_ID_EXTRA = "recordId";

    private long id;

    AppCompatTextView categoryTextView;
    AppCompatTextView descriptionTextView;
    AppCompatTextView dateTextView;
    AppCompatTextView timeTextView;
    GridView photoGridView;

    public static void startMe(Context context, long id) {
        Intent intent = new Intent(context, RecordContentActivity.class);
        intent.putExtra(RECORD_ID_EXTRA, id);
        context.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        id = getIntent().getLongExtra(RECORD_ID_EXTRA, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_record_content);
        id = getIntent().getLongExtra(RECORD_ID_EXTRA, 0);
        categoryTextView = (AppCompatTextView) findViewById(R.id.category_text_view);
        descriptionTextView = (AppCompatTextView) findViewById(R.id.description_text_view);
        dateTextView = (AppCompatTextView) findViewById(R.id.date_text_view);
        timeTextView = (AppCompatTextView) findViewById(R.id.time_text_view);
        photoGridView = (GridView) findViewById(R.id.photos_grid_view);

    }

    @Override
    protected void onResume() {
        loadRecord();
        super.onResume();
    }

    private void loadRecord() {
        GetRecordByIdTask getRecordsTask = new GetRecordByIdTask(this);
        Record record = null;
        try {
            record = getRecordsTask.execute(id).get();
            record.categoryName = new GetCategoryTask(this).execute((long) record.categoryId).get().name;
            Calendar calendar = Calendar.getInstance();
            if (record.start != null && record.end != null) {
                calendar.setTime(record.start);
                long time = record.time;
                String hour = String.valueOf((((time / 1000) / 60) / 60) % 24);
                String minute = String.valueOf((((time / 1000) / 60) % 60));
                timeTextView.setText(getString(R.string.date_format, hour, minute));
                categoryTextView.setText(record.categoryName);
                descriptionTextView.setText(record.description);

            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        GetImageAsyncTask getImageAsyncTask = new GetImageAsyncTask(this);
        try {
            ArrayList<Bitmap> bitmaps = getImageAsyncTask.execute(id).get();
            photoGridView.setAdapter(new ImageAdapter(this, bitmaps));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}
