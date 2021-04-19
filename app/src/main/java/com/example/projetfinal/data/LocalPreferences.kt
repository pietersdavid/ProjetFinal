package com.example.projetfinal.data

import android.content.Context
import android.content.SharedPreferences

// LocalPreferences allow to save data in the smartphone with persistence
class LocalPreferences private constructor(context: Context) {

    // Instantiate a sharedpreferences object
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPref2", Context.MODE_PRIVATE)

    // Function to add an item to history
    // One same position can be only once in the history due to "MutableSet" (It is managable but i do the choice to leave it like that, it is not critical)
    fun addToHistory(newEntry: String){
        // Get old history
        var history = this.getHistory()

        // Enter new position
        if (history != null) {
            history?.add(newEntry)
        }

        // Save ,ew history in shared preferences
        sharedPreferences.edit().putStringSet("mhistory", history).apply()
    }

    // Get the history in the shared preferences
    fun getHistory(): MutableSet<String>? {
        return sharedPreferences.getStringSet("mhistory", mutableSetOf<String>().toMutableSet())
    }

    // Empty the history
    fun reset() {
        sharedPreferences.edit().clear().apply()
    }

    // Check if history is empty
    fun isEmpty(): Boolean {
        return sharedPreferences.all.isEmpty()
    }

    // Give access to shared preferences across application
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



