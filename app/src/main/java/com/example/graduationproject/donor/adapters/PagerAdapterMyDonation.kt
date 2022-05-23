package com.example.graduationproject.donor.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.graduationproject.donor.fragments.MyDonationFragment

class PagerAdapterMyDonation(fm: FragmentManager, var numOfTabs: Int, var data: ArrayList<Int>) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return numOfTabs
    }

    override fun getItem(position: Int): Fragment {
        return MyDonationFragment.newInstance(data[position])
    }
}