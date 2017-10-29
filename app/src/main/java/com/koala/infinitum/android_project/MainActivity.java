package com.koala.infinitum.android_project;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.interfaces.place.IGetAll;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.Response;
import com.koala.infinitum.android_project.httpApi.services.PlaceService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout; //available only from API 22

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar(); //method getSupportActionBar from API 24
        if (actionBar != null) {
            actionBar.setElevation(0);
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

    /*private void setTabLayoutIcons() {
        ArrayList<Integer> buff = new ArrayList<>();
        buff.add(R.drawable.ic_dashboard_black_24dp);
        buff.add(R.drawable.ic_home_black_24dp);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tl = tabLayout.getTabAt(i);
            if (tl != null) {
                tl.setIcon(buff.get(i));
            }
        }
    }*/

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
