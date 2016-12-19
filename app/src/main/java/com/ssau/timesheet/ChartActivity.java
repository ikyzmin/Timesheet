package com.ssau.timesheet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ssau.timesheet.database.MaxTimeCategorySelection;
import com.ssau.timesheet.database.MostOftenCategorySelection;
import com.ssau.timesheet.database.Record;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;

public class ChartActivity extends AppCompatActivity {

    private ListView mostOftenListView;
    private ListView maxTimeListView;
    private AppCompatTextView statisticsLabel;
    private Toolbar toolbar;
    private String startDate;
    private String endDate;

    public static void start(Context context) {
        Intent intent = new Intent(context, ChartActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_chart);
        PieView pieView = (PieView) findViewById(R.id.pie_view);
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        mostOftenListView = (ListView) findViewById(R.id.most_often_category);
        maxTimeListView = (ListView) findViewById(R.id.max_time_category_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        statisticsLabel = (AppCompatTextView) findViewById(R.id.statictics_text_view);
        statisticsLabel.setText(getString(R.string.statistics_type_format, getString(R.string.all_time_statistics)));
        pieView.setDate(pieHelperArrayList);
        pieView.selectedPie(0); //optional
        //  pieView.setOnPieClickListener(listener) //optional
        pieView.showPercentLabel(false); //optional
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_range:
                selectRange();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void selectRange() {
        RangeSelectionActivity.startMe(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStatistic();
    }

    private void loadStatistic() {
        GetRecordsByCategoryTask getRecordsByCategoryTask = new GetRecordsByCategoryTask(this);

        ArrayList<MostOftenCategorySelection> records;
        try {
            if (startDate != null && endDate != null) {
                Date start = Record.STATISTIC_FORMATTER.parse(startDate);
                Date end = Record.STATISTIC_FORMATTER.parse(endDate);
                records = getRecordsByCategoryTask.execute(start, end).get();
            } else {
                records = getRecordsByCategoryTask.execute().get();
            }
            mostOftenListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records));
        } catch (InterruptedException | ExecutionException | ParseException e) {
            e.printStackTrace();
        }
        GetCategoryByMaxTimeTask getRecordsByMaxTime = new GetCategoryByMaxTimeTask(this);

        ArrayList<MaxTimeCategorySelection> maxTimeRecords;
        try {
            if (startDate != null && endDate != null) {
                Date start = Record.STATISTIC_FORMATTER.parse(startDate);
                Date end = Record.STATISTIC_FORMATTER.parse(endDate);
                maxTimeRecords = getRecordsByMaxTime.execute(start, end).get();
            } else {
                maxTimeRecords = getRecordsByMaxTime.execute().get();
            }
            maxTimeListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, maxTimeRecords));
        } catch (InterruptedException | ExecutionException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RangeSelectionActivity.REQUEST_RANGE && resultCode == RESULT_OK) {
            startDate = data.getStringExtra(RangeSelectionActivity.START_DATE);
            endDate = data.getStringExtra(RangeSelectionActivity.END_DATE);
            statisticsLabel.setText(getString(R.string.statistics_type_format, getString(R.string.statistics_date_format, startDate, endDate)));
            loadStatistic();
        }
    }
}
