package com.bangkit.capstone.insightapp.sharedpref

import android.content.Context
import android.content.SharedPreferences

class NightModeSharedPref(context: Context) {

    private var nightModeSharedPref: SharedPreferences =
        context.getSharedPreferences("night_mode", Context.MODE_PRIVATE)

    fun setNightModeState(state: Boolean) {
        val editor = nightModeSharedPref.edit()
        editor.putBoolean("NightMode", state)
        editor.apply()
    }

    fun loadNightModeState(): Boolean {
        return nightModeSharedPref.getBoolean("NightMode", false)
    }

}