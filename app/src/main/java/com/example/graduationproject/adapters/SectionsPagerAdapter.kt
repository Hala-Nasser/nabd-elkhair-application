package com.example.graduationproject.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.graduationproject.R



private val TAB_TITLES = arrayOf(
    "تبرع بحملة",
    "تبرع بدون حملة"
)

class SectionsPagerAdapter(fm: FragmentManager,var fragment : ArrayList<Fragment>) :
    FragmentPagerAdapter(fm) {



    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getCount(): Int {
        return fragment.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }



}