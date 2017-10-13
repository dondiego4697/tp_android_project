package com.koala.infinitum.android_project.mainFragments.myEvents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koala.infinitum.android_project.R;

public class MyEvents extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_events, container, false);
        return rootView;
    }

    @Override
    public void onRefresh() {

    }
}