package com.koala.infinitum.android_project.mainFragments.globalEvents.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koala.infinitum.android_project.List.Adapter;
import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.List.OnItemClickListener;

import java.util.ArrayList;

public class GlobalEventsListAdapter extends RecyclerView.Adapter<GlobalEventsListItemHolder> implements Adapter<Place>{
    private ArrayList<Place> items;
    private Context activityContext;

    public GlobalEventsListAdapter(Context context) {
        super();
        items = new ArrayList<>();
        activityContext = context;
    }

    public void updateData(ArrayList<Place> items) {
        this.notifyDataSetChanged();
        this.items = items;
    }

    public void updateItem(Place place, Integer index) {
        this.notifyDataSetChanged();
        items.set(index, place);
    }

    public void clear() {
        this.notifyDataSetChanged();
        items.clear();
    }

    public ArrayList<Place> getData() {
        return items;
    }

    @Override
    public GlobalEventsListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        return new GlobalEventsListItemHolder(view, activityContext);
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