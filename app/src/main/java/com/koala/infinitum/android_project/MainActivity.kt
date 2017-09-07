package com.koala.infinitum.android_project

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var pagerAdapter: PageAdapter? = null
    private var viewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.elevation = 0F

        init()
    }

    private fun init() {
        viewPager = findViewById(R.id.container) as ViewPager?
        tabLayout = findViewById(R.id.tabs) as TabLayout

        tabLayout!!.setupWithViewPager(viewPager)
        pagerAdapter = PageAdapter(supportFragmentManager, this)
        viewPager!!.adapter = pagerAdapter
        //tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_home_white_24dp)
    }

}
