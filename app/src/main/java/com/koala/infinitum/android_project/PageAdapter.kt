package com.koala.infinitum.android_project

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.koala.infinitum.android_project.mainFragments.list.ListFragment
import com.koala.infinitum.android_project.mainFragments.list.ListTypes

class PageAdapter(fm: android.support.v4.app.FragmentManager, private val context: Context): FragmentPagerAdapter(fm) {
    private val COUNT = 2
    private val TITLES: Array<String> = arrayOf("MAIN1", "MAIN2")

    override fun getCount(): Int = COUNT

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ListFragment.newInstance(ListTypes.Main1, context)
            1 -> return ListFragment.newInstance(ListTypes.Main2, context)
        }
        return ListFragment.newInstance(ListTypes.Main1, context)
    }

    override fun getPageTitle(position: Int): CharSequence? = TITLES[position]
}