package com.example.graduationproject.adapters

import android.content.Context
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.ViewGroup


class MyPagerAdapter(): PagerAdapter() {

    lateinit var layouts: IntArray
    lateinit var layoutInflater: LayoutInflater

    constructor(context: Context, layouts: IntArray): this(){
        this.layouts = layouts
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return layouts.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(layouts[position], container, false)
        view.rotationY = 180F
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}