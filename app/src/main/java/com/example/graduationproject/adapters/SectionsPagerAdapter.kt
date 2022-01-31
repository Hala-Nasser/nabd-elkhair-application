package com.example.graduationproject.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.graduationproject.R


class SectionsPagerAdapter(fm: FragmentManager,) :
    FragmentPagerAdapter(fm) {
    var fragments = ArrayList<Fragment>()
    var titles = ArrayList<String>()

    fun addFragments(fragment: Fragment){
        fragments.add(fragment)
    }

    fun addFragmentsAndTitles(fragment: Fragment,title:String){
        fragments.add(fragment)
        titles.add(title)
    }


    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }



}