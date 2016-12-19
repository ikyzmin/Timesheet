package com.ssau.timesheet.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssau.timesheet.R;
import com.ssau.timesheet.RecordContentActivity;
import com.ssau.timesheet.database.Record;

import java.util.ArrayList;
import java.util.Calendar;

public class RecordAdapter extends RecyclerView.Adapter {

    ArrayList<Record> records;
    Context context;

    public RecordAdapter(Context context, ArrayList<Record> records) {
        this.records = records;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.i_record, parent, false);
        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecordHolder recordHolder = (RecordHolder) holder;
        recordHolder.title.setText(records.get(position).categoryName);
        recordHolder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecordContentActivity.startMe(context,records.get(position).id);
            }
        });
       // recordHolder.image.setImageBitmap(records.get(position).bitmap);
        recordHolder.description.setText(records.get(position).description);
        Calendar calendar = Calendar.getInstance();
        if (records.get(position).start != null && records.get(position).end != null) {
            calendar.setTime(records.get(position).start);
            long time = records.get(position).time;
            String hour = String.valueOf((((time / 1000) / 60) / 60) % 24);
            String minute = String.valueOf((((time / 1000) / 60) % 60));
            recordHolder.date.setText(context.getString(R.string.date_format, hour, minute));
        }
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
        public View content;

        public RecordHolder(View itemView) {
            super(itemView);
            content = itemView;
            title = (AppCompatTextView) itemView.findViewById(R.id.title);
            date = (AppCompatTextView) itemView.findViewById(R.id.date);
            description = (AppCompatTextView) itemView.findViewById(R.id.description);
            image = (AppCompatImageView) itemView.findViewById(R.id.image);
        }

    }
}
