package com.example.graduationproject.charity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.classes.TabLayoutSettings
import com.example.graduationproject.classes.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.fragment_charity_settings.view.*

class CharitySettingsFragment : Fragment() {

    private val TAB_ICONS = arrayOf(
        R.drawable.ic_settings,
        R.drawable.ic_payment,
        R.drawable.ic_notifications
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_settings, container, false)

        requireActivity().charity_nav_bottom.visibility=View.GONE

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(GeneralSettingsFragment(),"الاعدادات")
        sectionsPagerAdapter.addFragmentsAndTitles(PaymentSettingsFragment(),"الدفع")
        sectionsPagerAdapter.addFragmentsAndTitles(NotificationsSettingsFragment(),"الاشعارات")


        root.charity_settings_view_pager.adapter = sectionsPagerAdapter

        root.charity_settings_tab_layout.setupWithViewPager(root.charity_settings_view_pager)
        root.charity_settings_view_pager.setPageTransformer(true, ZoomOutPageTransformer())

        var tabLayout = TabLayoutSettings()
        tabLayout.setupTabIcons(root.charity_settings_tab_layout,TAB_ICONS)




        return root
    }

}