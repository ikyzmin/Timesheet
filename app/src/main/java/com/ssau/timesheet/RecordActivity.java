package com.ssau.timesheet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.ssau.timesheet.database.Photo;
import com.ssau.timesheet.database.Record;

import java.util.concurrent.ExecutionException;


public class RecordActivity extends AppCompatActivity {

    AppCompatButton addPhoto;
    AppCompatButton addRecord;
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
                record.description = "test";
                Photo photo = new Photo();
                photo.uri = path;
                record.photo = photo;
                AddRecordTask addRecordTask = new AddRecordTask(RecordActivity.this);
                try {
                    addRecordTask.execute(record).get();
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
