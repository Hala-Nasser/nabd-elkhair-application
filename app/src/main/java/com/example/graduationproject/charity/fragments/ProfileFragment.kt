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
import com.example.graduationproject.classes.TabLayoutSettings
import com.example.graduationproject.classes.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.activity_charity_main.*
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

        requireActivity().charity_nav_bottom.visibility=View.VISIBLE

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(AboutCharityFragment(),"عن الجمعية")
        sectionsPagerAdapter.addFragmentsAndTitles(CharityComplaintsFragment(),"شكاوي")


        root.charity_profile_view_pager.adapter = sectionsPagerAdapter

        root.charity_profile_tab_layout.setupWithViewPager(root.charity_profile_view_pager)
        root.charity_profile_view_pager.setPageTransformer(true, ZoomOutPageTransformer())

        var tabLayout = TabLayoutSettings()
        tabLayout.setTabMargin(root.charity_profile_tab_layout,12,12,270)


        root.charity_settings_icon.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.charityContainer, CharitySettingsFragment()).addToBackStack(null).commit()
        }

        return root
    }




}