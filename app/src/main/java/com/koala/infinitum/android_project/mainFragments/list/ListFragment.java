package com.koala.infinitum.android_project.mainFragments.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koala.infinitum.android_project.R;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private Factory mainFactory;
    private ListTypes listType;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listType = (ListTypes) getArguments().getSerializable("LIST_TYPE");
        mainFactory = new Factory();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvList);

        ArrayList<String> items = null;
        switch (listType) {
            case Main1: {
                items = mainFactory.getMain1();
                break;
            }
            case Main2: {
                items = mainFactory.getMain2();
                break;
            }
        }
        ListAdapter listAdapter = new ListAdapter(items);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    public static ListFragment newInstance(ListTypes listType) {
        Bundle args = new Bundle();
        args.putSerializable("LIST_TYPE", listType);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
