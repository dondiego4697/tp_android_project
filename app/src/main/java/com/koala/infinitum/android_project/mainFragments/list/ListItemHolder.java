package com.koala.infinitum.android_project.mainFragments.list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.koala.infinitum.android_project.R;

interface OnItemClickListener {
    void onItemClick(View v, int pos);
}

public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView name;
    private OnItemClickListener onItemClickListener;

    public ListItemHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.tvItem);
        CardView card = (CardView) itemView.findViewById(R.id.cardView);
        itemView.setOnClickListener(this);
    }

    public void update(String text) {
        name.setText(text);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v, getAdapterPosition());
    }
}
