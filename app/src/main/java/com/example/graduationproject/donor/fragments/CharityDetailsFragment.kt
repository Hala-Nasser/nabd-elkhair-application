package com.example.graduationproject.donor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.charity.fragments.AddComplaintFragment
import com.example.graduationproject.charity.models.Donation
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_charity_details.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class CharityDetailsFragment : Fragment() {

    lateinit var charity_name:String
    lateinit var charity_image:String
    lateinit var charity_address:String
    //lateinit var charity_donation_type:String
   // var campaignDonation : ArrayList<Donation> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_charity_details, container, false)

        val b = arguments
        if (b != null) {
            charity_name = b.getString("charity_name")!!
            charity_image = b.getString("charity_image")!!
            charity_address = b.getString("charity_address")!!

            Picasso.get().load(RetrofitInstance.IMAGE_URL+charity_image).into(root.charity_image)
            root.charity_name_2.text = charity_name
            root.charity_name.text = charity_name
            root.charity_location.text = charity_address


            val about_fragment = AboutCharityFragment()
            b.putString("charity_name", charity_name)
            b.putString("charity_image", charity_image)
            b.putString("charity_description", b.getString("charity_description"))
            b.putInt("charity_id", b.getInt("charity_id", 0))
            //b.putStringArrayList("charity_donation_type", b.getStringArrayList("charity_donation_type"))
            b.putString("charity_phone", b.getString("charity_phone"))

            about_fragment.arguments = b

            val charity_campaign_fragment = CharityCampaignsFragment()
            b.putInt("charity_id", b.getInt("charity_id", 0))

            charity_campaign_fragment.arguments = b

            val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
            sectionsPagerAdapter.addFragmentsAndTitles(about_fragment, "عن الجمعية")
            sectionsPagerAdapter.addFragmentsAndTitles(charity_campaign_fragment, "الحملات")
            root.charity_campaigns_viewpager.adapter = sectionsPagerAdapter
            root.charity_tab_layout.setupWithViewPager(root.charity_campaigns_viewpager)

            root.donor_add_complaint.setOnClickListener {
                val f = AddComplaintFragment()
                val bundle= Bundle()
                bundle.putString("from","Donor")
                bundle.putInt("charity_id", b.getInt("charity_id", 0))
                f.arguments=bundle
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,f).commit()
            }

        }

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }


        return root
    }


}