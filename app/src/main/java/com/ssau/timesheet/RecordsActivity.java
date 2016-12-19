package com.ssau.timesheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ssau.timesheet.adapter.RecordAdapter;
import com.ssau.timesheet.database.Record;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RecordsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBarDrawerToggle toggleButton;
    private RecyclerView recordsRecyclerView;
    private RecordAdapter recordAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_records);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recordsRecyclerView = (RecyclerView) findViewById(R.id.record_recycler_view);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }

    private void loadRecords() {
        GetRecordsTask getRecordsTask = new GetRecordsTask(this);
        ArrayList<Record> records;
        try {
            records = getRecordsTask.execute().get();
            recordAdapter = new RecordAdapter(this, records);
            for (int i = 0; i < records.size(); i++) {
                records.get(i).categoryName = new GetCategoryTask(this).execute((long) records.get(i).categoryId).get().name;
            }
            recordsRecyclerView.setAdapter(recordAdapter);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_record:
                addRecord();
                return true;
            case R.id.show_chart:
                addCharts();
                return true;
        }
        if (toggleButton.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addRecord() {
        RecordActivity.startMe(this);
    }

    private void addCharts() {
        ChartActivity.start(this);
    }


}
