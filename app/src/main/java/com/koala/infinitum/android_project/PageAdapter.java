package com.koala.infinitum.android_project;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.koala.infinitum.android_project.mainFragments.list.ListFragment;
import com.koala.infinitum.android_project.mainFragments.list.ListTypes;

import java.util.ArrayList;
import java.util.Arrays;

public class PageAdapter extends FragmentPagerAdapter {
    private ArrayList<String> TITLES = new ArrayList<>(Arrays.asList("MAIN1", "MAIN2"));

    PageAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: {
                return ListFragment.newInstance(ListTypes.Main1);
            }
            case 1: {
                return ListFragment.newInstance(ListTypes.Main2);
            }
            default: {
                return ListFragment.newInstance(ListTypes.Main1);
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
