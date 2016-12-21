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
    private LocalBroadcastManager localBroadcastManager;

    private final BroadcastReceiver dateSetBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onDateSet(intent.getStringExtra(DATE_START_EXTRA), intent.getStringExtra(DATE_END_EXTRA));
        }
    };

    private static final ArrayList<BroadcastReceiver> registeredBroadcasts = new ArrayList<>();
    private static int registeredCount = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (registeredCount==0) {
            localBroadcastManager.registerReceiver(dateSetBroadcastReceiver, new IntentFilter(ACTION_DATE_SET));
        }
        registeredCount++;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (--registeredCount == 0) {
            localBroadcastManager.unregisterReceiver(dateSetBroadcastReceiver);
        }
    }

    public abstract void onDateSet(String start, String end);


}
