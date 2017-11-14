package com.koala.infinitum.android_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.koala.infinitum.android_project.mapSearch.MapSearchActivity;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


public class MainActivity extends AppCompatActivity {
  
    private static final int DRAWER_ITEM_SETTINGS = 0;
    private static final int DRAWER_ITEM_OUT = 1;

    private final static String LOGIN = "login";
    private final static String PASSWD = "password";

    private String currUserLogin;


    TabLayout tabLayout;

    Drawer navDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currUserLogin = getIntent().getExtras().getString(LOGIN);
        setContentView(R.layout.activity_main);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setElevation(0);
//        }
        /*component = */
      //  App.getComponent().inject(this);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        IProfile profile = new ProfileDrawerItem()
                .withName(currUserLogin)
                .withIcon(R.mipmap.ic_launcher_round);

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_g)
                .addProfiles(profile)
                .build();

        navDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(true)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)

                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(DRAWER_ITEM_SETTINGS),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_out).withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(DRAWER_ITEM_OUT)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener(){
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (((int) drawerItem.getIdentifier())){
                            case DRAWER_ITEM_SETTINGS:
                                //TODO: make settings activity
                                break;
                            case DRAWER_ITEM_OUT:
                                SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPref", 0);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.remove(LOGIN);
                                editor.remove(PASSWD);
                                editor.apply();
                                Intent intent = new Intent(getBaseContext(), SplashScreenActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                })
                .build();
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
                Intent intent = new Intent(this, MapSearchActivity.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (navDrawer != null && navDrawer.isDrawerOpen()) {
            navDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
