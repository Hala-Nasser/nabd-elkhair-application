package com.example.graduationproject.classes

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import com.example.graduationproject.R
import com.google.android.material.tabs.TabLayout

class TabLayoutSettings {

     fun setupTabIcons(tabLayout: TabLayout,TabIcons:Array<Int>) {
         for (i in TabIcons.indices){
             tabLayout.getTabAt(i)!!.setIcon(TabIcons[i])
         }
    }

     fun setTabMargin(tabLayout: TabLayout,marginEnd:Int,marginStart:Int,width:Int) {
        val tabs = tabLayout.getChildAt(0) as ViewGroup

        for (i in 0 until tabs.childCount) {
            val tab = tabs.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.marginEnd = marginEnd
            layoutParams.marginStart = marginStart
            layoutParams.width = width
            tab.layoutParams = layoutParams
            tabLayout.requestLayout()
        }
    }
}