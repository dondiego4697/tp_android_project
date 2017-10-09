package com.koala.infinitum.android_project.mainFragments.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.koala.infinitum.android_project.MainActivity;
import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.Response;
import com.koala.infinitum.android_project.httpApi.services.PlaceService;

import java.util.ArrayList;

public class ListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Factory mainFactory;
    private ListTypes listType;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

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
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.srl);
        swipeRefreshLayout.setOnRefreshListener(this);

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

    @Override
    public void onRefresh() {
        testReq();
    }

    private void testReq() {
        new PlaceService().getAll(10, 0, true, new ClientCallback<Response<Place>>() {
            @Override
            public void onSuccess(retrofit2.Response<Response<Place>> response) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), response.body().getData().get(0).getPoint(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String err) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), err, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
