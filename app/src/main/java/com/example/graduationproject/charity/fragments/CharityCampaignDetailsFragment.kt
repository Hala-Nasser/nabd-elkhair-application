package com.example.graduationproject.charity.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.charity.adapters.DonorsAdapter
import com.example.graduationproject.charity.models.Donation
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.fragment_charity_campaign_details.*
import kotlinx.android.synthetic.main.fragment_charity_campaign_details.view.*
import kotlin.math.log


class CharityCampaignDetailsFragment : Fragment() {

    lateinit var campaignId:String
    lateinit var campaignName:String
    lateinit var campaignDesc:String
     var campaignImage = 0
     var position = 0
    lateinit var campaignDate:String
    lateinit var campaignTime:String
    var campaignDonation : ArrayList<Donation> = ArrayList()
    lateinit var donorAdapter:DonorsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_campaign_details, container, false)

        donorAdapter =
            DonorsAdapter(activity, campaignDonation, "CampaignDetails")

        val b = arguments
        if (b != null) {
           var from  = b.getString("from")
            when(from){
                "AllDonation" -> {
                    getBundleData(b, root,"all")
                }
                "ClothesDonation" -> {
                    getBundleData(b, root,"clothes")
                }
                "FoodDonation" -> {
                    getBundleData(b, root,"food")
                }
                "MoneyDonation" -> {
                    getBundleData(b, root,"money")
                }
            }


        }




        return root
    }

    private fun getBundleData(b: Bundle, root: View,donationType:String) {
        campaignId =  b.getString("${donationType}_campaign_id")!!
        campaignName =  b.getString("${donationType}_campaign_name")!!
        campaignDesc =  b.getString("${donationType}_campaign_description")!!
        campaignImage =  b.getInt("${donationType}_campaign_image")
        campaignDate =  b.getString("${donationType}_campaign_date")!!
        campaignTime =  b.getString("${donationType}_campaign_time")!!
        position =  b.getInt("${donationType}_position")!!

        root.campaign_img_details.setBackgroundResource(campaignImage)
        root.campaign_name_details.text = campaignName
        root.campaign_desc_details.text = campaignDesc
        root.campaign_date_details.text = campaignDate
        root.campaign_time_details.text = campaignTime




        if (b.getParcelableArrayList<Donation>("${donationType}_campaign_donation") == null) {
            root.rv_donors.visibility = View.GONE
        } else {

            campaignDonation = b.getParcelableArrayList<Donation>("${donationType}_campaign_donation")!!

            var linkedHashSet: LinkedHashSet<Donation> = LinkedHashSet()
            linkedHashSet.addAll(campaignDonation)
            campaignDonation.clear()
            campaignDonation.addAll(linkedHashSet)

            donorAdapter =
                DonorsAdapter(activity, campaignDonation, "CampaignDetails")
            donorAdapter.campaignName = campaignName
            donorAdapter.campaignImg = campaignImage
            root.rv_donors.layoutManager = LinearLayoutManager(
                activity,
                RecyclerView.VERTICAL, false
            )
            root.rv_donors.setHasFixedSize(true)
            root.rv_donors.adapter = donorAdapter
        }

    }


    override fun onResume() {
        super.onResume()
        requireActivity().charity_nav_bottom.visibility=View.GONE
    }
}