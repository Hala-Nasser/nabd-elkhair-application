package com.example.graduationproject.donor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.donor.models.Campaigns
import kotlinx.android.synthetic.main.current_campaigns_item.view.*

class CampaignsAdapter (var activity: Context?, var data :List<Campaigns>,var from:String,
                        var clickListener: onCampaignItemClickListener) : RecyclerView.Adapter<CampaignsAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image  =itemView.campaign_image
        val name  =itemView.campaign_name
        val date  =itemView.campaign_date
        val charity  =itemView.campaign_charity
        val card  =itemView.campaign_card


        fun initialize(data: Campaigns, action: onCampaignItemClickListener,from: String) {
            when(from){
                "DonorProfile" -> {
                    image.setImageResource(data.campaignImg!!)
                    name.text = data.campaignName
                    date.text = data.campaignDate
                }
                "CharityCampaigns" -> {
                    image.setImageResource(data.campaignImg!!)
                    name.text = data.campaignName
                }

                else -> {
                    image.setImageResource(data.campaignImg!!)
                    name.text = data.campaignName
                    date.text = data.campaignDate
                    charity.text = data.campaignCharity.charityName
                }

            }

            itemView.setOnClickListener {
                action.onItemClick(data, adapterPosition,from)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignsAdapter.MyViewHolder {
        when(from){
            "DonorProfile" -> {
                var view: View = LayoutInflater.from(activity).inflate(R.layout.profile_campaign_item ,parent ,false)
                val myHolder:MyViewHolder = MyViewHolder(view)
                return myHolder
            }
            "CharityCampaigns" -> {

                var view: View = LayoutInflater.from(activity).inflate(R.layout.campaign_item_in_donation_screen ,parent ,false)
                val myHolder:MyViewHolder = MyViewHolder(view)
                return myHolder
            }
        }

        var view: View = LayoutInflater.from(activity).inflate(R.layout.current_campaigns_item ,parent ,false)
        val myHolder:MyViewHolder = MyViewHolder(view)
        return myHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CampaignsAdapter.MyViewHolder, position: Int) {
        // holder.photo.setImageResource(data[position].photo)
        holder.initialize(data[position], clickListener,from)

        when(from){
            "CharityHome" -> {
                holder.charity.visibility = View.GONE
            }
            "DonorHome" -> {
                holder.charity.visibility = View.VISIBLE
            }
        }
    }

    interface onCampaignItemClickListener {
        fun onItemClick(data: Campaigns, position: Int,from: String)
    }

}