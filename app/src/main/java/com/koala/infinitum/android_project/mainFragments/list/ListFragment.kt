package com.koala.infinitum.android_project.mainFragments.list

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.koala.infinitum.android_project.R

class ListFragment(context: Context) : Fragment() {
    private val ARG_LIST_TYPE = "LIST_TYPE"
    private val mainFactory: Factory = Factory(context)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.list_fragment, container, false)
        val recyclerView = rootView.findViewById(R.id.rvList) as RecyclerView
        val listTypes = this.arguments.getSerializable(ARG_LIST_TYPE) as ListTypes
        var items = ArrayList<String>()
        when (listTypes) {
            ListTypes.Main1 -> items = mainFactory.Main1
            ListTypes.Main2 -> mainFactory.Main2
        }
        recyclerView.adapter = ListAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return rootView
    }

    companion object {
        private val ARG_LIST_TYPE = "LIST_TYPE"

        fun newInstance(listType: ListTypes, context: Context): ListFragment {
            val fragment = ListFragment(context)
            val args = Bundle()
            args.putSerializable(ARG_LIST_TYPE, listType)
            fragment.arguments = args
            return fragment
        }
    }
}