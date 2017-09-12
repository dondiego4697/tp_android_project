package com.koala.infinitum.android_project.mainFragments.list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.koala.infinitum.android_project.R;


public class ListItemHolder extends RecyclerView.ViewHolder {

    private TextView name;

    public ListItemHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.tvItem);
        CardView card = (CardView) itemView.findViewById(R.id.cardView);
    }

    public void update(String text) {
        name.setText(text);
    }
}
