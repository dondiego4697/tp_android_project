package com.koala.infinitum.android_project.mainFragments.globalEvents;

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

import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.Response;
import com.koala.infinitum.android_project.httpApi.services.PlaceService;
import com.koala.infinitum.android_project.mainFragments.globalEvents.list.GlobalEventsListAdapter;

import java.util.ArrayList;

public class GlobalEvents extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    GlobalEventsListAdapter listAdapter;
    ArrayList<Place> listData = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_global_events, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvList);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.srl);
        swipeRefreshLayout.setOnRefreshListener(this);

        listAdapter = new GlobalEventsListAdapter(listData);

        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
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
                listData = (ArrayList<Place>) response.body().getData();
                listAdapter.updateData(listData);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String err) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), err, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
