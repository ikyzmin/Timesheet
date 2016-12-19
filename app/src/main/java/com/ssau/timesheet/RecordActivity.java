package com.ssau.timesheet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.ssau.timesheet.adapter.CategoryAdapter;
import com.ssau.timesheet.database.Category;
import com.ssau.timesheet.database.Photo;
import com.ssau.timesheet.database.Record;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class RecordActivity extends AppCompatActivity {

    AppCompatButton addPhoto;
    AppCompatButton addRecord;
    AppCompatSpinner categoriesSpinner;
    AppCompatEditText descriptionEditText;
    AppCompatTextView startTimeTextView;
    AppCompatTextView endTimeTextView;
    private Date startDate;
    private final ArrayList<Uri> paths = new ArrayList<>();
    private Date endDate;
    private Calendar dateAndTime;

    TimePickerDialog.OnTimeSetListener startTimeCallback = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.setTime(startDate);
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            startDate = dateAndTime.getTime();
            startTimeTextView.setText(Record.DATE_FORMATTER.format(dateAndTime.getTime()));
        }
    };

    TimePickerDialog.OnTimeSetListener endTimeCallback = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.setTime(endDate);
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            endDate = dateAndTime.getTime();
            endTimeTextView.setText(Record.DATE_FORMATTER.format(endDate.getTime()));
        }
    };

    private final DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateAndTime.set(year, month, dayOfMonth);
            startDate = dateAndTime.getTime();
            dateAndTime = Calendar.getInstance();
            TimePickerDialog tpd = new TimePickerDialog(RecordActivity.this, startTimeCallback, dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE), true);
            tpd.show();
        }
    };
    private final DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateAndTime.set(year, month, dayOfMonth);
            endTimeTextView.setText(Record.DATE_FORMATTER.format(dateAndTime.getTime()));
            endDate = dateAndTime.getTime();
            dateAndTime = Calendar.getInstance();
            TimePickerDialog tpd = new TimePickerDialog(RecordActivity.this, endTimeCallback, dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE), true);
            tpd.show();
        }
    };

    public static void startMe(Context context) {
        Intent intent = new Intent(context, RecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_record);
        addPhoto = (AppCompatButton) findViewById(R.id.add_photo);
        addRecord = (AppCompatButton) findViewById(R.id.add_record);
        descriptionEditText = (AppCompatEditText) findViewById(R.id.description);
        startTimeTextView = (AppCompatTextView) findViewById(R.id.start_date);
        endTimeTextView = (AppCompatTextView) findViewById(R.id.end_date);
        categoriesSpinner = (AppCompatSpinner) findViewById(R.id.categories_spinner);
        dateAndTime = Calendar.getInstance();
        try {
            CategoryAdapter categoryAdapter = new CategoryAdapter(this, new GetCategoriesTask(this).execute().get());
            categoriesSpinner.setAdapter(categoryAdapter);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        startTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RecordActivity.this, startDateSetListener,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        endTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RecordActivity.this, endDateSetListener,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Record record = new Record();
                record.description = descriptionEditText.getText().toString();
                record.categoryId = ((Category) (categoriesSpinner.getSelectedItem())).id;
                record.start = startDate;
                record.end = endDate;
                record.time = (int)(endDate.getTime() - startDate.getTime());
                Photo photo = new Photo();
                photo.uri = paths;
                record.photo = photo;
                AddRecordTask addRecordTask = new AddRecordTask(RecordActivity.this);
                try {
                    addRecordTask.execute(record).get();
                    finish();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            paths.add(data.getData());
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


}
