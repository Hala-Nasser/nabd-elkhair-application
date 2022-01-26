package com.example.graduationproject.adapters

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import android.view.MotionEvent
import android.R

import android.content.res.TypedArray
import androidx.viewpager.widget.PagerAdapter


public class LockableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context) {

     var swipeLocked = false


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return !swipeLocked && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return !swipeLocked && super.onInterceptTouchEvent(event)
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return !swipeLocked && super.canScrollHorizontally(direction)
    }
}