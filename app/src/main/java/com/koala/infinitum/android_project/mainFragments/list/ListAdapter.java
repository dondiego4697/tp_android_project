package com.koala.infinitum.android_project.mainFragments.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koala.infinitum.android_project.R;

import java.util.ArrayList;

interface OnItemClickListener {
    void onItemClick(String item);
}

public class ListAdapter extends RecyclerView.Adapter<ListItemHolder> {
    private ArrayList<String> items;
    private OnItemClickListener onItemClickListener;
    public ListAdapter(ArrayList<String> items, OnItemClickListener onItemClickListener) {
        super();
        this.items = items;
        this.onItemClickListener = onItemClickListener;
        Log.d("custom-logs", "init");
    }

    @Override
    public ListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ListItemHolder listItemHolder = new ListItemHolder(view);
        listItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick("123");
            }
        });
        return listItemHolder;
    }

    @Override
    public void onBindViewHolder(ListItemHolder holder, int position) {
        holder.update(items.get(position));
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
