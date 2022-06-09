package com.example.graduationproject.charity.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.api.charityApi.donation.DonationJson
import com.example.graduationproject.charity.adapters.DonationAdapter
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.ZoomOutPageTransformer
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.fragment_charity_settings.view.*
import kotlinx.android.synthetic.main.fragment_donation.view.*
import kotlinx.android.synthetic.main.fragment_donation.view.tab_layout
import kotlinx.android.synthetic.main.fragment_donation_received.view.*
import kotlinx.android.synthetic.main.fragment_donation_received.view.back
import kotlinx.android.synthetic.main.fragment_donation_requests.*
import kotlinx.android.synthetic.main.fragment_donation_without_campaign.*
import kotlinx.android.synthetic.main.fragment_donation_without_campaign.rv_donation_without_campaign
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonationReceivedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_donation_received, container, false)

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(DonationNotReceivedFragment(),"التبرعات الغير مستلمة")
        sectionsPagerAdapter.addFragmentsAndTitles(DonationTabReceivedFragment(),"التبرعات المستلمة")
        root.donation_received_viewpager.adapter = sectionsPagerAdapter

        root.donation_received_tab_layout.setupWithViewPager(root.donation_received_viewpager)
        root.donation_received_viewpager.setPageTransformer(true, ZoomOutPageTransformer())

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return root
    }


}