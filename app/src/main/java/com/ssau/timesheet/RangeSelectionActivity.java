package com.ssau.timesheet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.DatePicker;

import com.ssau.timesheet.database.Record;

import java.util.Calendar;
import java.util.Date;

public class RangeSelectionActivity extends AppCompatActivity {

    public static final int REQUEST_RANGE = 120;
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    private Calendar dateAndTime = Calendar.getInstance();
    private Date startDate;
    private Date endDate;
    private AppCompatTextView startTextView;
    private AppCompatTextView endTextView;
    private AppCompatButton okButton;

    private final DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateAndTime.set(year, month, dayOfMonth);
            startDate = dateAndTime.getTime();
            dateAndTime = Calendar.getInstance();
            startTextView.setText(Record.DATE_FORMATTER.format(dateAndTime.getTime()));
        }
    };
    private final DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateAndTime.set(year, month, dayOfMonth);
            endTextView.setText(Record.DATE_FORMATTER.format(dateAndTime.getTime()));
            endDate = dateAndTime.getTime();
            dateAndTime = Calendar.getInstance();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_range);
        startTextView = (AppCompatTextView) findViewById(R.id.from_date);
        endTextView = (AppCompatTextView) findViewById(R.id.to_date);
        okButton = (AppCompatButton) findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(START_DATE, Record.STATISTIC_FORMATTER.format(startDate));
                intent.putExtra(END_DATE, Record.STATISTIC_FORMATTER.format(endDate));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        startTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RangeSelectionActivity.this, startDateSetListener,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        endTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RangeSelectionActivity.this, endDateSetListener,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public static final void startMe(AppCompatActivity activity) {
        Intent intent = new Intent(activity, RangeSelectionActivity.class);
        activity.startActivityForResult(intent, REQUEST_RANGE);
    }
}
