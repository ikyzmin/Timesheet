package com.ssau.timesheet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;


public abstract class BaseStatisticsFragment extends Fragment {

    public static final String ACTION_DATE_SET = "dateSet";
    public static final String DATE_START_EXTRA = "dateStart";
    public static final String DATE_END_EXTRA = "dateExtra";
    private static LocalBroadcastManager localBroadcastManager;

    private  BroadcastReceiver dateSetBroadcastReceiver;

    private static int registeredCount = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (dateSetBroadcastReceiver == null) {
            dateSetBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    onDateSet(intent.getStringExtra(DATE_START_EXTRA), intent.getStringExtra(DATE_END_EXTRA));
                }
            };
        }
        if (localBroadcastManager == null) {
            localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
                dateSetBroadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        onDateSet(intent.getStringExtra(DATE_START_EXTRA), intent.getStringExtra(DATE_END_EXTRA));
                    }
                };
            localBroadcastManager.registerReceiver(dateSetBroadcastReceiver, new IntentFilter(ACTION_DATE_SET));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
            localBroadcastManager.unregisterReceiver(dateSetBroadcastReceiver);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public abstract void onDateSet(String start, String end);


}
