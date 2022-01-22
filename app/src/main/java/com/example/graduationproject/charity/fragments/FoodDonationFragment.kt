package com.example.graduationproject.charity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.graduationproject.R
import com.example.graduationproject.donor.adapters.CampaignsAdapter
import com.example.graduationproject.donor.models.Campaigns
import kotlinx.android.synthetic.main.fragment_food_donation.view.*
import kotlinx.android.synthetic.main.fragment_money_donation.view.*
import kotlinx.android.synthetic.main.fragment_money_donation.view.rv_money_donation


class FoodDonationFragment : Fragment() {
    private lateinit var  campaignsList: MutableList<Campaigns>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_food_donation, container, false)

        campaignsList = mutableListOf()
        campaignsList.add(Campaigns(R.drawable.campaign_image,"تقديم الأكل للعائلات المحتاجة","22/2/2028","جمعية الاحسان الخيرية"))
        campaignsList.add(Campaigns(R.drawable.campaign_image,"تقديم الأكل للعائلات المحتاجة","22/2/2028","جمعية الاحسان الخيرية"))
        campaignsList.add(Campaigns(R.drawable.campaign_image,"تقديم الأكل للعائلات المحتاجة","22/2/2028","جمعية الاحسان الخيرية"))



        root.rv_food_donation.layoutManager = GridLayoutManager(this.context,2)
        root.rv_food_donation.setHasFixedSize(true)
        val campaignsAdapter =
            CampaignsAdapter(this.activity, campaignsList,"CharityHome")
        root.rv_food_donation.adapter = campaignsAdapter

        return root
    }


}