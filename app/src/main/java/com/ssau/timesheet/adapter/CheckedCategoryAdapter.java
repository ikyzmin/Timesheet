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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.ssau.timesheet.R;
import com.ssau.timesheet.database.Category;

import java.util.ArrayList;

/**
 * Created by Илья on 21.12.2016.
 */

public class CheckedCategoryAdapter extends BaseAdapter {

    public interface OnItemCheckedListner {
        void onItemChecked(Category category, boolean checked);
    }

    ArrayList<Category> categories;
    Context context;
    LayoutInflater lInflater;
    OnItemCheckedListner listener;


    public CheckedCategoryAdapter(Context context, ArrayList<Category> categories, OnItemCheckedListner listener) {
        this.categories = categories;
        this.context = context;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listener = listener;

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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = lInflater.inflate(R.layout.i_checked_category, viewGroup, false);
        }

        Category category = getItem(i);
        final int position = i;

        ((AppCompatTextView) view.findViewById(R.id.category_name)).setText(category.name);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.category_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                listener.onItemChecked(getItem(position), b);
            }
        });

        return view;
    }
}
