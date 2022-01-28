package com.example.graduationproject.charity.adapters

import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.charity.models.Donation
import com.example.graduationproject.charity.models.Donor
import com.example.graduationproject.donor.adapters.CampaignsAdapter
import com.example.graduationproject.donor.models.Campaigns
import kotlinx.android.synthetic.main.current_campaigns_item.view.*
import kotlinx.android.synthetic.main.donation_with_campaign.view.*
import kotlinx.android.synthetic.main.donors_item.view.*
import kotlinx.android.synthetic.main.fragment_clothes_donation.view.*
import android.view.animation.LinearInterpolator

import android.animation.ObjectAnimator
import android.app.Activity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_manually.view.*
import kotlinx.android.synthetic.main.donation_with_campaign.view.donation_card_view
import kotlinx.android.synthetic.main.donation_without_campaign.view.*


class CampaignDonationAdapter(
    var activity: Context?, var data: List<Campaigns>,
) : RecyclerView.Adapter<CampaignDonationAdapter.CampaignViewHolder>(){

    class CampaignViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image  =itemView.donation_campaign_image
        val name  =itemView.donation_campaign_name
        val arrow  =itemView.arrow_down
        val donors  =itemView.rv_campaign_donors
        val card  =itemView.donation_card_view

        fun initialize(campaigns: Campaigns) {
                image.setImageResource(campaigns.campaignImg!!)
                name.text = campaigns.campaignName

        }

    }




    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CampaignViewHolder{
            var view: View =   LayoutInflater.from(activity).inflate(R.layout.donation_with_campaign, parent, false)
            return CampaignViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }



    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {

               var campaignHolder =  holder

                campaignHolder.initialize(data[position])

                campaignHolder.arrow.setOnClickListener {
                    if (campaignHolder.donors.visibility == View.GONE) {
                        campaignHolder.arrow.setImageResource(R.drawable.ic_arrow_up)
                        TransitionManager.beginDelayedTransition(
                            campaignHolder.card,
                            AutoTransition()
                        )
                        campaignHolder.donors.visibility = View.VISIBLE

                    } else {
                        campaignHolder.arrow.setImageResource(R.drawable.ic_arrow_down)
                        //TransitionManager.beginDelayedTransition(campaignHolder.card,AutoTransition())
                        campaignHolder.donors.visibility = View.GONE
                    }

                    if (data[position].donation == null) {
                        campaignHolder.donors.visibility = View.GONE
                    }else{
                        val donation = data[position].donation!!
                        for (element in donation) {
                            if (data[position].campaignId == element.campaignId) {
                                val donorAdapter =
                                    DonorsAdapter(activity, donation)

                                donorAdapter.campaignName = data[position].campaignName
                                donorAdapter.campaignImg = data[position].campaignImg
                                campaignHolder.donors.layoutManager = LinearLayoutManager(
                                    activity,
                                    RecyclerView.VERTICAL, false
                                )
                                campaignHolder.donors.setHasFixedSize(true)
                                campaignHolder.donors.adapter = donorAdapter
                            }
                        }
                    }

                }
                    }



}