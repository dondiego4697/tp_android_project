package com.koala.infinitum.android_project.mainFragments.globalEvents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.interfaces.SimpleCallback;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.services.PlaceService;
import com.koala.infinitum.android_project.mainFragments.globalEvents.list.GlobalEventsListAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class GlobalEvents extends Fragment implements SwipeRefreshLayout.OnRefreshListener { //from API 22

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar pbList, pbBottom;
    GlobalEventsListAdapter listAdapter;

    int LIMIT = 5, OFFSET = 0;
    Boolean REFRESH = false, isREADY = true;

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
        pbList = (ProgressBar) rootView.findViewById(R.id.pbList);
        pbBottom = (ProgressBar) rootView.findViewById(R.id.pbBottom);
        swipeRefreshLayout.setOnRefreshListener(this);

        listAdapter = new GlobalEventsListAdapter(getActivity());

        recyclerView.setAdapter(listAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (REFRESH) return;
                int total = linearLayoutManager.getItemCount();
                int last = linearLayoutManager.findLastVisibleItemPosition();
                if (last + 1 >= total && isREADY) {
                    isREADY = false;
                    setProgressBarBottomVisibility(true);
                    uploadEvents(new SimpleCallback() {
                        @Override
                        public void onResult(String res) {
                            isREADY = !Objects.equals(res, "stop");
                            setProgressBarBottomVisibility(false);
                        }
                    });
                }
            }
        });

        setProgressBarVisibility(true);
        uploadEvents(new SimpleCallback() {
            @Override
            public void onResult(String res) {
                setProgressBarVisibility(false);
            }
        });
        return rootView;
    }

    @Override
    public void onRefresh() {
        REFRESH = true;
        OFFSET = 0;
        isREADY = true;
        listAdapter.clear();
        setProgressBarVisibility(true);
        uploadEvents(new SimpleCallback() {
            @Override
            public void onResult(String res) {
                REFRESH = false;
                setProgressBarVisibility(false);
            }
        });
    }

    private void uploadEvents(final SimpleCallback simpleCallback) {
        swipeRefreshLayout.setRefreshing(false);
        new PlaceService().getAll(LIMIT, OFFSET, true, null, new ClientCallback<Responses<Place>>() {
            @Override
            public void onSuccess(retrofit2.Response<Responses<Place>> response) {
                ArrayList<Place> list = (ArrayList<Place>) response.body().getData();
                listAdapter.addData(list);
                OFFSET += list.size();
                simpleCallback.onResult(list.size() == 0 ? "stop" : "ok");
            }

            @Override
            public void onError(String err) {
                Toast.makeText(getActivity(), err, Toast.LENGTH_SHORT).show(); //TODO сделать friendly error
                simpleCallback.onResult("");
            }
        });
    }

    private void setProgressBarBottomVisibility(Boolean state) {
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) swipeRefreshLayout.getLayoutParams();
        p.setMargins(0, 0, 0, state ? 50 : 0);
        swipeRefreshLayout.requestLayout();

        pbBottom.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    private void setProgressBarVisibility(Boolean state) {
        pbList.setVisibility(state ? View.VISIBLE : View.GONE);
    }
}
