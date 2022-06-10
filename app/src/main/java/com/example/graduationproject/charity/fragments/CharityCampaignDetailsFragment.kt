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
import com.example.graduationproject.donor.fragments.CampaignsAccordingToDonationTypeFragment
import com.example.graduationproject.donor.fragments.ProfileFragment
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.charity_item.view.*
import kotlinx.android.synthetic.main.fragment_charity_campaign_details.*
import kotlinx.android.synthetic.main.fragment_charity_campaign_details.view.*
import kotlin.math.log
import kotlin.system.exitProcess


class CharityCampaignDetailsFragment : Fragment() {
     var campaignId =0
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
//            if (b.getString("from", "")=="CharityHome"){
//                campaignDate =  b.getString("date")!!
//                campaignTime =  b.getString("time")!!
//             }else {
            campaignDate = b.getString("campaign_date")!!
            campaignTime =  b.getString("campaign_time")!!
            campaignId =  b.getInt("campaign_id")
            campaignName =  b.getString("campaign_name")!!
            campaignDesc =  b.getString("campaign_description")!!
            campaignImage =  b.getString("campaign_image")!!
            getBundleData(b, root)
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
            b.putInt("campaignId", campaignId)
            fragment.arguments = b

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.charityContainer, fragment).commit()
        }


        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return root
    }

    private fun getBundleData(b: Bundle, root: View) {

        Picasso.get().load(RetrofitInstance.IMAGE_URL+campaignImage).into(root.campaign_img_details)
        root.campaign_name_details.text = campaignName
        root.campaign_desc_details.text = campaignDesc
        root.campaign_date_details.text = campaignDate
        root.campaign_time_details.text = campaignTime




        if (b.getParcelableArrayList<Data>("campaign_donation") == null) {
            root.rv_donors.visibility = View.GONE
            root.nodonors_txt.visibility = View.VISIBLE
        } else {
            root.rv_donors.visibility = View.VISIBLE
            root.nodonors_txt.visibility = View.GONE

            campaignDonation = b.getParcelableArrayList<Data>("campaign_donation")!!

//            var linkedHashSet: LinkedHashSet<Data> = LinkedHashSet()
//            linkedHashSet.addAll(campaignDonation)
//            campaignDonation.clear()
//            campaignDonation.addAll(linkedHashSet)

            Log.e("campaignDonation",campaignDonation.toString())
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