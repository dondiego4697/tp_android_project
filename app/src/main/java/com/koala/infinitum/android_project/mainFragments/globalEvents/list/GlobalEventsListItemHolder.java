package com.koala.infinitum.android_project.mainFragments.globalEvents.list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.mainFragments.OnItemClickListener;

class GlobalEventsListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView name;
    private OnItemClickListener onItemClickListener;

    GlobalEventsListItemHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.tvItem);
        CardView card = (CardView) itemView.findViewById(R.id.cardView);
        itemView.setOnClickListener(this);
    }

    void update(Place place) {
        name.setText(place.getCreated() + place.getDescription() + place.getPoint() + place.getTitle());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v, getAdapterPosition());
    }
}
