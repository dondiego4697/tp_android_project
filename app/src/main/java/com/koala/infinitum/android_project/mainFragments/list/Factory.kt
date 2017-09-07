package com.koala.infinitum.android_project.mainFragments.list

import android.content.Context

class Factory(private val context: Context) {
    val Main1: ArrayList<String>
        get() {
            val result = ArrayList<String>()
            result.add("asasd1")
            result.add("asasd2")
            result.add("asasd3")
            result.add("asasd4")
            return result
        }

    val Main2: Any? = null
}