package com.ssau.timesheet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ssau.timesheet.R;
import com.ssau.timesheet.database.Category;

import java.util.ArrayList;

/**
 * Created by Илья on 16.12.2016.
 */

public class CategoryAdapter extends BaseAdapter {

    private ArrayList<Category> categories;
    private Context context;


    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.categories = categories;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categories.get(i).id;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context
                    .getApplicationContext().getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.i_category,
                    parent, false);
        }

        AppCompatTextView title = (AppCompatTextView) convertView
                .findViewById(R.id.category_name);
        title.setText(getItem(position).name);


        return convertView;
    }
}
