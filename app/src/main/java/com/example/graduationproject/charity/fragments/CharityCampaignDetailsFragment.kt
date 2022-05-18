package com.example.graduationproject.charity.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.charityApi.donation.Data
import com.example.graduationproject.charity.adapters.DonorsAdapter
import com.example.graduationproject.charity.models.Donation
import com.example.graduationproject.donor.fragments.ProfileFragment
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.charity_item.view.*
import kotlinx.android.synthetic.main.fragment_campaign_details.view.*
import kotlinx.android.synthetic.main.fragment_charity_campaign_details.*
import kotlinx.android.synthetic.main.fragment_charity_campaign_details.view.*
import kotlin.math.log
import kotlin.system.exitProcess


class CharityCampaignDetailsFragment : Fragment() {
    lateinit var campaignId:String
    lateinit var campaignName:String
    lateinit var campaignDesc:String
    lateinit var campaignImage:String
    lateinit var campaignDate:String
    lateinit var campaignTime:String
    var campaignDonation : ArrayList<Data> = ArrayList()
    lateinit var donorAdapter:DonorsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_campaign_details, container, false)

        requireActivity().charity_nav_bottom.visibility=View.GONE

        donorAdapter =
            DonorsAdapter(activity, campaignDonation, "CampaignDetails",requireActivity().supportFragmentManager)

        val b = arguments
        if (b != null) {
            if (requireParentFragment()::class.java.name == EditCampaignFragment()::class.java.name){
                var date = b.getString("date")
                var time =  b.getString("time")
                root.campaign_date_details.text = date
                root.campaign_time_details.text = time
             }else
                getBundleData(b,root)
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                var bundle = Bundle()
                bundle.putBoolean("addCampaign",false)
                var fragment = HomeFragment()
                fragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.charityContainer,fragment).commit()
            }
        })

        root.edit_campaign_btn.setOnClickListener {
            val fragment = EditCampaignFragment()
            val b = Bundle()
            b.putString("campaignDate", campaignDate)
            b.putString("campaignTime", campaignTime)
            fragment.arguments = b

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.charityContainer, fragment).addToBackStack(null).commit()
        }



        return root
    }

    private fun getBundleData(b: Bundle, root: View) {
        campaignId =  b.getString("campaign_id")!!
        campaignName =  b.getString("campaign_name")!!
        campaignDesc =  b.getString("campaign_description")!!
        campaignImage =  b.getString("campaign_image")!!
        campaignDate =  b.getString("campaign_date")!!
        campaignTime =  b.getString("campaign_time")!!

        Picasso.get().load(RetrofitInstance.IMAGE_URL+campaignImage).into(root.campaign_img_details)
        root.campaign_name_details.text = campaignName
        root.campaign_desc_details.text = campaignDesc
        root.campaign_date_details.text = campaignDate
        root.campaign_time_details.text = campaignTime




        if (b.getParcelableArrayList<Data>("campaign_donation") == null) {
            root.rv_donors.visibility = View.GONE
        } else {

            campaignDonation = b.getParcelableArrayList<Data>("campaign_donation")!!

            var linkedHashSet: LinkedHashSet<Data> = LinkedHashSet()
            linkedHashSet.addAll(campaignDonation)
            campaignDonation.clear()
            campaignDonation.addAll(linkedHashSet)

            donorAdapter =
                DonorsAdapter(activity, campaignDonation, "CampaignDetails",requireActivity().supportFragmentManager)
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