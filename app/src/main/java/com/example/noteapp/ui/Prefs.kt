package com.example.noteapp.ui

import android.content.Context

class Prefs(context: Context) {


    private val preferences = context
        .getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun saveState() {
        preferences.edit().putBoolean("isShown", true).apply()
    }

    fun isShown(): Boolean {
        return preferences.getBoolean("isShown",false)
    }

    fun saveName(name: String){
        preferences.edit().putString("name",name).apply()
    }

    fun getName(): String? {
        return preferences.getString("name", "")
    }
}