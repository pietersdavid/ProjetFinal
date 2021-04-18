package com.example.projetfinal.data

import android.content.Context
import android.content.SharedPreferences

class LocalPreferences private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPref2", Context.MODE_PRIVATE)

    fun addToHistory(newEntry: String){
        var history = this.getHistory()

        if (history != null) {
            history?.add(newEntry)
        }

        sharedPreferences.edit().putStringSet("mhistory", history).apply()
    }

    fun getHistory(): MutableSet<String>? {
        return sharedPreferences.getStringSet("mhistory", mutableSetOf<String>().toMutableSet())
    }

    fun reset() {
        sharedPreferences.edit().clear().apply()
    }

    fun isEmpty(): Boolean {
        if (sharedPreferences.all.size == 0)
            return true
        else
            return false
    }

    companion object {
        private var INSTANCE: LocalPreferences? = null

        fun getInstance(context: Context): LocalPreferences {
            return INSTANCE?.let {
                INSTANCE
            } ?: run {
                INSTANCE = LocalPreferences(context)
                return INSTANCE!!
            }
        }
    }

}



