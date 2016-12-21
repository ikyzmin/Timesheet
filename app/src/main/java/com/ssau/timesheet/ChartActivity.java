package com.ssau.timesheet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ssau.timesheet.adapter.StatisticsPagerAdapter;
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


    private Toolbar toolbar;
    private ViewPager statisticsViewPager;


    public static void start(Context context) {
        Intent intent = new Intent(context, ChartActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_chart);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        statisticsViewPager = (ViewPager)findViewById(R.id.statistics_view_pager);
        statisticsViewPager.setAdapter(new StatisticsPagerAdapter(getSupportFragmentManager()));
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
    }

}
