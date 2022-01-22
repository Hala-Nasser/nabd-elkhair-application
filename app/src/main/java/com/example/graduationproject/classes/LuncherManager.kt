package com.example.graduationproject.classes

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences




class LuncherManager(activity: Activity) {

    private val PREF_NAME = "LunchManger"
    private val IS_FIRST_TIME = "isFirst"

    var sharedPreferences = activity.getSharedPreferences(PREF_NAME, 0)
    var editor = sharedPreferences!!.edit()


    // this function will use to change the value of IS_FIRST_TIME that store in sharedPref
    // if the user is enter for the first time this function will make the value false
    //for not showing onBoarding screens
    fun setFirstLunch(isFirst: Boolean) {
        editor!!.putBoolean(IS_FIRST_TIME, isFirst)
        editor!!.commit()
    }

    // this function return the value of IS_FIRST_TIME that store in sharedPref
    fun isFirstTime(): Boolean {
        return sharedPreferences!!.getBoolean(IS_FIRST_TIME, true)
    }
}