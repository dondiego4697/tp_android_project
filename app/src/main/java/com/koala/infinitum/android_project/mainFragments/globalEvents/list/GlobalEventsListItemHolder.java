package com.koala.infinitum.android_project.mainFragments.globalEvents.list;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.List.OnItemClickListener;

class GlobalEventsListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tvTitle, tvDescription, tvStartTime, tvCategory, tvCreated, tvPeopleCount;
    private OnItemClickListener onItemClickListener;
    private Context activityContext;

    GlobalEventsListItemHolder(View itemView, Context context) {
        super(itemView);
        activityContext = context;
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        tvStartTime = (TextView) itemView.findViewById(R.id.tvStartTime);
        tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
        tvCreated = (TextView) itemView.findViewById(R.id.tvCreated);
        tvPeopleCount = (TextView) itemView.findViewById(R.id.tvPeopleCount);
        itemView.setOnClickListener(this);
    }

    void update(Place place) {
        tvTitle.setText(place.getTitle());
        tvDescription.setText(place.getDescription());
        tvStartTime.setText(String.format(activityContext.getResources().getString(R.string.place_item__time_start), place.getTimeStart()));
        tvCategory.setText(place.getCategoryName());
        tvCreated.setText(String.format(activityContext.getResources().getString(R.string.place_item__time_created), place.getCreated()));
        tvPeopleCount.setText(String.valueOf(place.getSubscribers()));
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v, getAdapterPosition());
    }
}
