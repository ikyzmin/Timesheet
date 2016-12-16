package com.ssau.timesheet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;

import com.ssau.timesheet.adapter.CategoryAdapter;
import com.ssau.timesheet.adapter.RecordAdapter;
import com.ssau.timesheet.database.Category;
import com.ssau.timesheet.database.Photo;
import com.ssau.timesheet.database.Record;

import java.util.concurrent.ExecutionException;


public class RecordActivity extends AppCompatActivity {

    AppCompatButton addPhoto;
    AppCompatButton addRecord;
    AppCompatSpinner categoriesSpinner;
    AppCompatEditText descriptionEditText;
    Uri path;

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
        categoriesSpinner = (AppCompatSpinner) findViewById(R.id.categories_spinner);
        try {
            CategoryAdapter categoryAdapter = new CategoryAdapter(this, new GetCategoriesTask(this).execute().get());
            categoriesSpinner.setAdapter(categoryAdapter);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
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
                Photo photo = new Photo();
                photo.uri = path;
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
            path = data.getData();
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


}
