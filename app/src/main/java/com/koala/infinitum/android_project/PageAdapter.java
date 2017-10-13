package com.koala.infinitum.android_project;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.koala.infinitum.android_project.mainFragments.globalEvents.GlobalEvents;
import com.koala.infinitum.android_project.mainFragments.myEvents.MyEvents;
import com.koala.infinitum.android_project.mainFragments.mySubscriptions.MySubscriptions;

import java.util.ArrayList;

class PageAdapter extends FragmentPagerAdapter {
    private ArrayList<String> TITLES = new ArrayList<>();

    PageAdapter(android.support.v4.app.FragmentManager fm, Context context) {
        super(fm);
        TITLES.add(context.getResources().getString(R.string.page__my_events));
        TITLES.add(context.getResources().getString(R.string.page__my_subsc));
        TITLES.add(context.getResources().getString(R.string.page__global));
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new MyEvents();
            }
            case 1: {
                return new MySubscriptions();
            }
            case 2: {
                return new GlobalEvents();
            }
            default: {
                return new GlobalEvents();
            }
        }
    }

    @Override
    public int getCount() {
        return TITLES.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES.get(position);
    }
}
