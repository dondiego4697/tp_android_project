package com.koala.infinitum.android_project.mainFragments.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koala.infinitum.android_project.R;

import java.util.ArrayList;

class ListAdapter extends RecyclerView.Adapter<ListItemHolder> {
    private ArrayList<String> items;

    ListAdapter(ArrayList<String> items) {
        super();
        this.items = items;
    }

    @Override
    public ListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ListItemHolder holder, int position) {
        holder.update(items.get(position));
        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Log.d("custom-logs", pos + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
