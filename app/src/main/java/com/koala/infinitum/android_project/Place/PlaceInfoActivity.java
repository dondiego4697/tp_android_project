package com.koala.infinitum.android_project.Place;

import android.content.Intent;
import android.graphics.Point;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.models.Place;

public class PlaceInfoActivity extends AppCompatActivity implements View.OnClickListener{

    AppBarLayout appBar;
    TextView tvTitle, tvDescription, tvStartTime, tvCategory, tvCreated, tvPeopleCount;
    FloatingActionButton fabSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        init();
    }

    private void init() {
        fabSubscribe = (FloatingActionButton) findViewById(R.id.fabSubscribe);
        fabSubscribe.setOnClickListener(this);

        appBar = (AppBarLayout) findViewById(R.id.appBar);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvCreated = (TextView) findViewById(R.id.tvCreated);
        tvPeopleCount = (TextView) findViewById(R.id.tvPeopleCount);
        fillData();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        appBar.getLayoutParams().height = height / 3;
    }

    private void fillData() {
        Intent intent = getIntent();
        Place place = (Place) intent.getSerializableExtra("place");

        tvTitle.setText(place.getTitle());
        tvDescription.setText(place.getDescription());
        tvStartTime.setText(place.getTimeStart());
        tvCategory.setText(place.getCategoryName());
        tvCreated.setText(place.getCreated());
        tvPeopleCount.setText(String.valueOf(place.getSubscribers()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabSubscribe: {

                break;
            }
        }
    }
}
