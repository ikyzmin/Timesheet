package com.ssau.timesheet.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ssau.timesheet.ListOfMaximumsFragment;
import com.ssau.timesheet.PieFragment;
import com.ssau.timesheet.TimeByCategoryFragment;

public class StatisticsPagerAdapter extends FragmentStatePagerAdapter {

    public StatisticsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ListOfMaximumsFragment();
                break;
            case 1:
                fragment = new PieFragment();
                break;
            case 2:
                fragment = new TimeByCategoryFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
