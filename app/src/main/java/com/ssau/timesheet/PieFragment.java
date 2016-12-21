package com.ssau.timesheet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ssau.timesheet.adapter.HistoryAdapter;
import com.ssau.timesheet.adapter.HistoryPoint;
import com.ssau.timesheet.database.MaxTimeCategorySelection;
import com.ssau.timesheet.database.Record;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;

/**
 * Created by Илья on 21.12.2016.
 */

public class PieFragment extends BaseStatisticsFragment {

    private PieView pieView;
    private String startDate;
    private String endDate;
    private ListView historyListView;
    private final int[] DEFAULT_COLOR_LIST = {Color.parseColor("#33B5E5"),
            Color.parseColor("#AA66CC"),
            Color.parseColor("#99CC00"),
            Color.parseColor("#FFBB33"),
            Color.parseColor("#FF4444")};

    @Override
    public void onDateSet(String start, String end) {
        startDate = start;
        endDate = end;
        loadStatistic();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadStatistic();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_piechart, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pieView = (PieView) view.findViewById(R.id.pie_view);
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        pieView.setDate(pieHelperArrayList);
        pieView.showPercentLabel(true);
        historyListView = (ListView) view.findViewById(R.id.history_list);
    }

    private void loadStatistic() {
        GetCategoryByMaxTimeTask getRecordsByMaxTime = new GetCategoryByMaxTimeTask(getContext());
        ArrayList<MaxTimeCategorySelection> maxTimeRecords = null;
        try {
            if (startDate != null && endDate != null) {
                Date start = Record.STATISTIC_FORMATTER.parse(startDate);
                Date end = Record.STATISTIC_FORMATTER.parse(endDate);
                maxTimeRecords = getRecordsByMaxTime.execute(start, end).get();
            } else {
                maxTimeRecords = getRecordsByMaxTime.execute().get();
            }
        } catch (InterruptedException | ExecutionException | ParseException e) {
            e.printStackTrace();
        }
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        float allTime = 0f;
        for (MaxTimeCategorySelection maxTimeCategorySelection : maxTimeRecords) {
            allTime += maxTimeCategorySelection.time;
        }
        ArrayList<HistoryPoint> historyPoints = new ArrayList<>();
        int index = 0;
        for (MaxTimeCategorySelection maxTimeCategorySelection : maxTimeRecords) {
            PieHelper pieHelper = new PieHelper((maxTimeCategorySelection.time / allTime) * 100);
            pieHelperArrayList.add(pieHelper);
            HistoryPoint historyPoint = new HistoryPoint();
            historyPoint.name = maxTimeCategorySelection.category;
            historyPoint.color = DEFAULT_COLOR_LIST[index % 5];
            historyPoints.add(historyPoint);
            index++;
        }
        historyListView.setAdapter(new HistoryAdapter(getContext(), historyPoints));
        pieView.setDate(pieHelperArrayList);

    }
}
