package com.example.graduationproject.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.graduationproject.R



private val TAB_TITLES = arrayOf(
    R.id.donation_all,
    R.id.donation_food,
    R.id.donation_money,
    R.id.donation_clothes
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager,var fragment : ArrayList<Fragment>) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }



    override fun getCount(): Int {
        return fragment.size
    }
}