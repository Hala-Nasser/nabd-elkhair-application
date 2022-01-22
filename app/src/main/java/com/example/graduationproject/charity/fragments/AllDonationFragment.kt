package com.example.graduationproject.charity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.donor.adapters.CampaignsAdapter
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.donor.models.Campaigns
import kotlinx.android.synthetic.main.fragment_all_donation.view.*


class AllDonationFragment : Fragment() {
    private lateinit var  campaignsList: MutableList<Campaigns>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_all_donation, container, false)
        campaignsList = mutableListOf()
        campaignsList.add(Campaigns(R.drawable.campaign_image,"تقديم الأكل للعائلات المحتاجة","22/2/2022","جمعية الاحسان الخيرية"))
        campaignsList.add(Campaigns(R.drawable.campaign_image,"تقديم الأكل للعائلات المحتاجة","22/2/2022","جمعية الاحسان الخيرية"))
        campaignsList.add(Campaigns(R.drawable.campaign_image,"تقديم الأكل للعائلات المحتاجة","22/2/2022","جمعية الاحسان الخيرية"))



        root.rv_all_donation.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
        root.rv_all_donation.setHasFixedSize(true)
        val campaignsAdapter =
            CampaignsAdapter(this.activity, campaignsList,"DonorHome")
        root.rv_all_donation.adapter = campaignsAdapter
        return root
    }

}