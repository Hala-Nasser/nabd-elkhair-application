package com.example.graduationproject.charity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import android.widget.LinearLayout

import android.view.Display
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.classes.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.fragment_charity_profile.*
import kotlinx.android.synthetic.main.fragment_charity_profile.view.*
import kotlinx.android.synthetic.main.fragment_donation.view.*
import net.cachapa.expandablelayout.ExpandableLayout


class ProfileFragment : Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_profile, container, false)

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(AboutCharityFragment(),"عن الجمعية")
        sectionsPagerAdapter.addFragmentsAndTitles(CharityComplaintsFragment(),"شكاوي")


        root.charity_profile_view_pager.adapter = sectionsPagerAdapter

        root.charity_profile_tab_layout.setupWithViewPager(root.charity_profile_view_pager)
        root.charity_profile_view_pager.setPageTransformer(true, ZoomOutPageTransformer())

        val tabs = root.charity_profile_tab_layout.getChildAt(0) as ViewGroup

        for (i in 0 until tabs.childCount ) {
            val tab = tabs.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 0f
            layoutParams.marginEnd = 12
            layoutParams.marginStart = 12
            layoutParams.width = 270
            tab.layoutParams = layoutParams
            root.charity_profile_tab_layout.requestLayout()
        }
        return root
    }




}