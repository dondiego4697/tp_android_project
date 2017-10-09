package com.koala.infinitum.android_project;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
            //actionBar.setTitle(getResources().getString(R.string.app_name));
        }

        init();
    }

    private void init() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
        PageAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
    }

    private void setTabLayoutIcons() {
        ArrayList<Integer> buff = new ArrayList<>();
        buff.add(R.drawable.ic_dashboard_black_24dp);
        buff.add(R.drawable.ic_home_black_24dp);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tl = tabLayout.getTabAt(i);
            if (tl != null) {
                tl.setIcon(buff.get(i));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search_map: {
                break;
            }
        }
        return true;
    }
}
