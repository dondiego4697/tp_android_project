package com.koala.infinitum.android_project.mainFragments.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.koala.infinitum.android_project.R

//Сохраняет ссылку на layout и в дальнейшем работает только по ней. Поиск layout происходит один рах
class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.tvItem) as TextView

    fun update(text: String) {
        name.text = text
    }
}