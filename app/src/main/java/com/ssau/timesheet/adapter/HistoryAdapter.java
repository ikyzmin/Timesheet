package com.ssau.timesheet.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssau.timesheet.R;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {

    private ArrayList<HistoryPoint> historyPoints;
    private Context context;
    LayoutInflater lInflater;

    public HistoryAdapter(Context context, ArrayList<HistoryPoint> historyPoints) {
        this.context = context;
        this.historyPoints = historyPoints;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setHistoryPoints(ArrayList<HistoryPoint> historyPoints) {
        this.historyPoints = historyPoints;
    }

    @Override
    public int getCount() {
        return historyPoints.size();
    }

    @Override
    public HistoryPoint getItem(int i) {
        return historyPoints.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = lInflater.inflate(R.layout.i_history_point, viewGroup, false);
        }

        HistoryPoint historyPoint = getItem(i);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((AppCompatTextView) view.findViewById(R.id.point_name)).setText(historyPoint.name);
        Drawable pointDrawable = ContextCompat.getDrawable(context, R.drawable.square);
        pointDrawable.setColorFilter(new PorterDuffColorFilter(historyPoint.color, PorterDuff.Mode.SRC_IN));
        ((ImageView) view.findViewById(R.id.point_color)).setImageDrawable(pointDrawable);

        return view;
    }
}
