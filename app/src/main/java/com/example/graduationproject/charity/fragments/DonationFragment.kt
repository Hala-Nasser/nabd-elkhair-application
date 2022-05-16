package com.example.graduationproject.charity.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.classes.ZoomOutPageTransformer
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import kotlinx.android.synthetic.main.fragment_charity_notification.view.*
import kotlinx.android.synthetic.main.fragment_donation.view.*


class DonationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_donation, container, false)

        val sharedPref= requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val user_image = sharedPref.getString("charity_image", "")
        val campaignDonationCount = sharedPref.getInt("campaignDonationCount", 0)
        val withoutCampaignDonationCount = sharedPref.getInt("withoutCampaignDonationCount", 0)
        Picasso.get().load(RetrofitInstance.IMAGE_URL+user_image).into(root.donation_profile_image)
        root.campaign_donation_count.text = campaignDonationCount.toString()
        root.without_campaign_donation_count.text = withoutCampaignDonationCount.toString()

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(DonationWithCampaignFragment(),"تبرع بحملة")
        sectionsPagerAdapter.addFragmentsAndTitles(DonationWithoutCampaignFragment(),"تبرع بدون حملة")
        root.donation_viewpager.adapter = sectionsPagerAdapter

        root.tab_layout.setupWithViewPager(root.donation_viewpager)
        root.donation_viewpager.setPageTransformer(true, ZoomOutPageTransformer())
        return root
    }

}