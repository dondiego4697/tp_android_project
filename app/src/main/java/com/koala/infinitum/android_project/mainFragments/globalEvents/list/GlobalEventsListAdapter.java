package com.koala.infinitum.android_project.mainFragments.globalEvents.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koala.infinitum.android_project.List.Adapter;
import com.koala.infinitum.android_project.Place.PlaceInfoActivity;
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
        this.items = items;
        this.notifyDataSetChanged();
    }

    public void addData(ArrayList<Place> items) {
        this.items.addAll(items);
        this.notifyDataSetChanged();
    }

    public void updateItem(Place place, Integer index) {
        items.set(index, place);
        this.notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        this.notifyDataSetChanged();
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
                Intent intent = new Intent(activityContext, PlaceInfoActivity.class);
                intent.putExtra("place", items.get(pos));
                activityContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}