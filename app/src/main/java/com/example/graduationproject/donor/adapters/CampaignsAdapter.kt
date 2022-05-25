package com.example.graduationproject.donor.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.models.Campaigns
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.current_campaigns_item.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class CampaignsAdapter (var activity: Context?, var data :ArrayList<Campaigns>, var from:String,
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
                    Picasso.get().load(RetrofitInstance.IMAGE_URL+data.image).into(image)
                    name.text = data.name
                    date.text = data.expiry_date
                }
                "CharityCampaigns" -> {
                    Picasso.get().load(RetrofitInstance.IMAGE_URL+data.image).into(image)
                    name.text = data.name
                }

                else -> {
                    Picasso.get().load(RetrofitInstance.IMAGE_URL+data.image).into(image)
                    name.text = data.name
                    date.text = data.expiry_date
                    if (from=="DonorHome")
                    charity.text = data.charity.name
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
        Log.e("data in adapter", data.toString())
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