package com.example.graduationproject.charity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.charity.adapters.DonationAdapter
import com.example.graduationproject.charity.models.Donation
import com.example.graduationproject.charity.models.Donor
import kotlinx.android.synthetic.main.fragment_donation_with_campaign.view.*
import kotlinx.android.synthetic.main.fragment_donation_without_campaign.view.*


class DonationWithoutCampaignFragment : Fragment() {

    private lateinit var  donationList: MutableList<Donation>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_donation_without_campaign, container, false)

        var donor1 = Donor(R.drawable.campaign_image,"Mary Ann Vargas","غزة")
        var donor2 = Donor(R.drawable.campaign_image,"Harry Ann Vargas","غزة")
        var donor3 = Donor(R.drawable.campaign_image,"Mary Ann Vargas","غزة")

        donationList = mutableListOf()
        donationList.add(Donation(donor1,null,"025896542","غزة","غزة","غزة","200 شيكل"))
        donationList.add(Donation(donor2,null,"025896542","غزة","غزة","غزة","200 شيكل"))
        donationList.add(Donation(donor3,null,"025896542","غزة","غزة","غزة","200 شيكل"))


        root.rv_donation_without_campaign.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL, false
        )
        root.rv_donation_without_campaign.setHasFixedSize(true)
        val donationAdapter =
            DonationAdapter(this.activity, donationList)
        root.rv_donation_without_campaign.adapter = donationAdapter

        return root
    }

}