package com.ssau.timesheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Илья on 21.12.2016.
 */

public class ListOfMaximumsFragment extends BaseStatisticsFragment {


    private ListView mostOftenListView;
    private ListView maxTimeListView;
    private AppCompatTextView statisticsLabel;
    private String startDate;
    private String endDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_list_of_maximum,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mostOftenListView = (ListView) view.findViewById(R.id.most_often_category);
        maxTimeListView = (ListView) view.findViewById(R.id.max_time_category_list);
        statisticsLabel = (AppCompatTextView) view.findViewById(R.id.statictics_text_view);
        statisticsLabel.setText(getString(R.string.statistics_type_format, getString(R.string.all_time_statistics)));

    }

    @Override
    public void onDateSet(String start, String end) {
        startDate = start;
        endDate = end;
        statisticsLabel.setText(getString(R.string.statistics_type_format, getString(R.string.statistics_date_format, startDate, endDate)));
        loadStatistic();
    }

    private void loadStatistic() {
        GetRecordsByCategoryTask getRecordsByCategoryTask = new GetRecordsByCategoryTask(getContext());

        ArrayList<MostOftenCategorySelection> records;
        try {
            if (startDate != null && endDate != null) {
                Date start = Record.STATISTIC_FORMATTER.parse(startDate);
                Date end = Record.STATISTIC_FORMATTER.parse(endDate);
                records = getRecordsByCategoryTask.execute(start, end).get();
            } else {
                records = getRecordsByCategoryTask.execute().get();
            }
            mostOftenListView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, records));
        } catch (InterruptedException | ExecutionException | ParseException e) {
            e.printStackTrace();
        }
        GetCategoryByMaxTimeTask getRecordsByMaxTime = new GetCategoryByMaxTimeTask(getContext());

        ArrayList<MaxTimeCategorySelection> maxTimeRecords;
        try {
            if (startDate != null && endDate != null) {
                Date start = Record.STATISTIC_FORMATTER.parse(startDate);
                Date end = Record.STATISTIC_FORMATTER.parse(endDate);
                maxTimeRecords = getRecordsByMaxTime.execute(start, end).get();
            } else {
                maxTimeRecords = getRecordsByMaxTime.execute().get();
            }
            maxTimeListView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, maxTimeRecords));
        } catch (InterruptedException | ExecutionException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadStatistic();
    }
}
