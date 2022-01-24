package com.example.graduationproject.donor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.graduationproject.R
import com.example.graduationproject.donor.models.Charity
import com.google.android.material.snackbar.Snackbar

class CampaignDetailsFragment : Fragment() {

    var campaign_name = ""
    var campaign_image = 0
    var campaign_date = ""
    lateinit var campaign_charity: Charity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_campaign_details, container, false)

        val b = arguments
        if (b != null) {
            campaign_name = b.getString("campaign_name")!!
            campaign_image = b.getInt("campaign_image")
            campaign_date = b.getString("campaign_date")!!
            campaign_charity = b.getParcelable("campaign_charity")!!

        }

        return root
    }

}