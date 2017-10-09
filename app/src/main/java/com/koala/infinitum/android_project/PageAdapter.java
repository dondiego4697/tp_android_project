package com.koala.infinitum.android_project;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.koala.infinitum.android_project.mainFragments.list.ListFragment;
import com.koala.infinitum.android_project.mainFragments.list.ListTypes;

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
                return ListFragment.newInstance(ListTypes.Main1);
            }
            case 1: {
                return ListFragment.newInstance(ListTypes.Main2);
            }
            case 2: {
                return ListFragment.newInstance(ListTypes.Main3);
            }
            default: {
                return ListFragment.newInstance(ListTypes.Main3);
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
