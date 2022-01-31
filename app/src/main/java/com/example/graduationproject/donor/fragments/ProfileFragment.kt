package com.example.graduationproject.donor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_donor_main.*
import com.example.graduationproject.charity.fragments.*
import com.example.graduationproject.classes.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        requireActivity().nav_bottom.visibility=View.VISIBLE

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(AllDonationFragment(), "الكل")
        sectionsPagerAdapter.addFragmentsAndTitles(MoneyDonationFragment(), "المال")
        sectionsPagerAdapter.addFragmentsAndTitles(FoodDonationFragment(), "الغذاء")
        sectionsPagerAdapter.addFragmentsAndTitles(ClothesDonationFragment(), "الملابس")
        root.campaigns_profile_viewpager.adapter = sectionsPagerAdapter
        root.profile_tab_layout.setupWithViewPager(root.campaigns_profile_viewpager)

        root.settings.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,SettingsFragment()).addToBackStack(null).commit()
        }

        return root
    }

}