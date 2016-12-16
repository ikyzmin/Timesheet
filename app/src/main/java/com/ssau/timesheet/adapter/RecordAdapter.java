package com.ssau.timesheet.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssau.timesheet.R;
import com.ssau.timesheet.database.Record;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter {

    ArrayList<Record> records;

    public RecordAdapter(ArrayList<Record> records) {
        this.records = records;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.i_record, parent, false);
        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecordHolder recordHolder = (RecordHolder) holder;
        recordHolder.title.setText(records.get(position).categoryName);
        recordHolder.image.setImageBitmap(records.get(position).bitmap);
        recordHolder.description.setText(records.get(position).description);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    private class RecordHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView title;
        public AppCompatTextView date;
        public AppCompatTextView description;
        public AppCompatImageView image;

        public RecordHolder(View itemView) {
            super(itemView);
            title = (AppCompatTextView) itemView.findViewById(R.id.title);
            date = (AppCompatTextView) itemView.findViewById(R.id.date);
            description = (AppCompatTextView) itemView.findViewById(R.id.description);
            image = (AppCompatImageView) itemView.findViewById(R.id.image);
        }

    }
}
