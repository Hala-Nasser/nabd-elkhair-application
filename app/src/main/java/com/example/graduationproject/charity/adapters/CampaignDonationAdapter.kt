package com.example.graduationproject.charity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.donor.models.Campaigns
import kotlinx.android.synthetic.main.donation_with_campaign.view.*

import kotlinx.android.synthetic.main.donation_with_campaign.view.donation_card_view
import net.cachapa.expandablelayout.ExpandableLayout


class CampaignDonationAdapter(
    var activity: Context?, var data: List<Campaigns>, var fragment: FragmentManager
) : RecyclerView.Adapter<CampaignDonationAdapter.CampaignViewHolder>(){


    class CampaignViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image  =itemView.donation_campaign_image
        val name  =itemView.donation_campaign_name
        val arrow  =itemView.arrow_down
        val donors  =itemView.rv_campaign_donors
        val expandable_layout  =itemView.donors_expandable_layout
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

                holder.initialize(data[position])

                  holder.itemView.setOnClickListener {

                    if (holder.expandable_layout.state == ExpandableLayout.State.COLLAPSED) {
                        holder.arrow.setImageResource(R.drawable.ic_arrow_up)
                        holder.expandable_layout.isExpanded = true
                    } else if (holder.expandable_layout.state == ExpandableLayout.State.EXPANDED) {
                        holder.arrow.setImageResource(R.drawable.ic_arrow_down)
                        holder.expandable_layout.isExpanded = false
                    }


                      if (data[position].donation == null) {
                          holder.donors.visibility = View.GONE
                      } else {
                          holder.donors.visibility = View.VISIBLE

                          val donation = data[position].donation!!
                          for (element in donation) {
                              if (data[position].campaignId == element.campaignId) {
                                  val donorAdapter =
                                      DonorsAdapter(activity, donation, "CampaignDonationAdapter",fragment)

                                  donorAdapter.campaignName = data[position].campaignName
                                  donorAdapter.campaignImg = data[position].campaignImg
                                  holder.donors.layoutManager = LinearLayoutManager(
                                      activity,
                                      RecyclerView.VERTICAL, false
                                  )
                                  holder.donors.setHasFixedSize(true)
                                  holder.donors.adapter = donorAdapter
                              }
                          }
                      }

                  }


    }



}