package com.example.graduationproject.donor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.charity.fragments.AddComplaintFragment
import kotlinx.android.synthetic.main.fragment_charity_details.view.*


class CharityDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_charity_details, container, false)

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(AboutCharityFragment(), "عن الجمعية")
        sectionsPagerAdapter.addFragmentsAndTitles(CharityCampaignsFragment(), "الحملات")
        root.charity_campaigns_viewpager.adapter = sectionsPagerAdapter
        root.charity_tab_layout.setupWithViewPager(root.charity_campaigns_viewpager)

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        root.donor_add_complaint.setOnClickListener {
            val f = AddComplaintFragment()
            val b= Bundle()
            b.putString("from","Donor")
            f.arguments=b
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,f).commit()
        }
        return root
    }


}