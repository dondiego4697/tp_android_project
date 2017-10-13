package com.koala.infinitum.android_project.mainFragments.globalEvents.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.mainFragments.OnItemClickListener;

import java.util.ArrayList;

public class GlobalEventsListAdapter extends RecyclerView.Adapter<GlobalEventsListItemHolder> {
    private ArrayList<Place> items;

    public GlobalEventsListAdapter(ArrayList<Place> items) {
        super();
        this.items = items;
    }

    public void updateData(ArrayList<Place> items) {
        this.items = items;
    }

    @Override
    public GlobalEventsListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new GlobalEventsListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(GlobalEventsListItemHolder holder, int position) {
        holder.update(items.get(position));
        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Log.d("myLogs", String.valueOf(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}