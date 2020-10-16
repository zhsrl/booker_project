package com.e.booker.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.prefs.Preferences

class SaveDataSharedPreference {

    companion object{
        var LOGGED_STATUS = "logged in"

        fun getSharedPreferences(context: Context) :SharedPreferences{
            return PreferenceManager.getDefaultSharedPreferences(context)
        }


        fun setLogged(context: Context,logged: Boolean){
            val editor = getSharedPreferences(context).edit()
            editor.putBoolean(LOGGED_STATUS,logged)
            editor.apply()
        }

        fun getLoggedStatus(context: Context): Boolean{
            return getSharedPreferences(context).getBoolean(LOGGED_STATUS,false)
        }
    }


}