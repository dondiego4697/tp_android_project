package com.koala.infinitum.android_project.mainFragments.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.koala.infinitum.android_project.R

//Адаптер для списка, отслеживает измененые viewHolder и меняет элементы в их layout
class ListAdapter(private val items: ArrayList<String>) : RecyclerView.Adapter<ListItemHolder>() {
    override fun getItemCount(): Int = items.toArray().count()

    override fun onBindViewHolder(holder: ListItemHolder?, position: Int) {
        holder?.update(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListItemHolder {
        val itemHolder = LayoutInflater.from(parent!!.context).inflate(R.layout.list_item, parent, false) as LinearLayout
        return ListItemHolder(itemHolder)
    }
}
