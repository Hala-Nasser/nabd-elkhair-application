package com.example.graduationproject.classes

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.graduationproject.R


class NavigationManager(activity: Activity) {

    private val PREF_NAME = "NavigationManager"
    private val PAGE_NAME = "PAGE_NAME"

    var sharedPreferences = activity.getSharedPreferences(PREF_NAME, 0)
    var editor = sharedPreferences!!.edit()


    // this function will use to set the value of PAGE_NAME and store in sharedPref
    //for navigating between fragments and activities
    fun setPageName(pageName: Int) {
        editor!!.putInt(PAGE_NAME , pageName)
        editor!!.commit()
    }

    // this function will use to get the value of PAGE_NAME that store in sharedPref
    fun  getPageName():Int {
        return sharedPreferences.getInt(PAGE_NAME, R.id.home)
    }

}