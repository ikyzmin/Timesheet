package com.ssau.timesheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ssau.timesheet.adapter.CheckedCategoryAdapter;
import com.ssau.timesheet.database.Category;
import com.ssau.timesheet.database.MostOftenCategorySelection;
import com.ssau.timesheet.database.Record;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class TimeByCategoryFragment extends BaseStatisticsFragment {


    private String startDate;
    private String endDate;
    private AppCompatTextView resultTextView;
    private ListView categoriesListView;
    private final ArrayList<String> ids = new ArrayList<>();

    @Override
    public void onDateSet(String start, String end) {
        startDate = start;
        endDate = end;
        if (!isDetached()) {
            loadStatistic();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            GetCategoriesTask getCategoriesTask = new GetCategoriesTask(getContext());
            ArrayList<Category> categories = getCategoriesTask.execute().get();
            categoriesListView.setAdapter(new CheckedCategoryAdapter(getContext(), categories, new CheckedCategoryAdapter.OnItemCheckedListner() {
                @Override
                public void onItemChecked(Category category, boolean checked) {
                    if (checked) {
                        ids.add(category.id + "");
                    } else {
                        ids.remove(category.id + "");
                    }
                    loadStatistic();
                }
            }));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        loadStatistic();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_timebycategory, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultTextView = (AppCompatTextView) view.findViewById(R.id.result_text_view);
        categoriesListView = (ListView) view.findViewById(R.id.checked_categories);
    }

    private void loadStatistic() {
        GetTimeFromCategoriesTask getTimeFromCategoriesTask = new GetTimeFromCategoriesTask(getContext());
        int result = 0;
        try {
            String[] idsArray = new String[ids.size()];
            ids.toArray(idsArray);
            if (startDate != null && endDate != null) {
                Date start = Record.STATISTIC_FORMATTER.parse(startDate);
                Date end = Record.STATISTIC_FORMATTER.parse(endDate);
                result = getTimeFromCategoriesTask.execute(idsArray, new Date[]{start, end}).get();
            } else {
                result = getTimeFromCategoriesTask.execute(idsArray, new Date[]{null, null}).get();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(result);
            resultTextView.setText(getString(R.string.summary_time_by_categories, Record.TIME_FORMATTER.format(calendar.getTime())));
        } catch (InterruptedException | ExecutionException | ParseException e) {
            e.printStackTrace();
        }
    }
}
