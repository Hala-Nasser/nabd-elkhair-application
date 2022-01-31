package com.example.graduationproject.charity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.classes.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import kotlinx.android.synthetic.main.fragment_donation.view.*


class DonationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_donation, container, false)


        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(DonationWithCampaignFragment(),"تبرع بحملة")
        sectionsPagerAdapter.addFragmentsAndTitles(DonationWithoutCampaignFragment(),"تبرع بدون حملة")
        root.donation_viewpager.adapter = sectionsPagerAdapter
        root.tab_layout.setupWithViewPager(root.donation_viewpager)
        root.donation_viewpager.setPageTransformer(true, ZoomOutPageTransformer())
        return root
    }

}