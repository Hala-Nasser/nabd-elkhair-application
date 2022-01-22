package com.example.graduationproject.donor.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.category.Data
import com.example.graduationproject.donor.models.Campaigns
import com.example.graduationproject.donor.models.DonationType
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.current_campaigns_item.view.*

class CampaignsAdapter (var activity: Context?, var data :List<Campaigns>,var from:String) : RecyclerView.Adapter<CampaignsAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image  =itemView.campaign_image
        val name  =itemView.campaign_name
        val date  =itemView.campaign_date
        val charity  =itemView.campaign_charity
        val card  =itemView.campaign_card

        fun initialize(data: Campaigns) {
            image.setImageResource(data.campaignImg!!)
            name.text = data.campaignName
            date.text = data.campaignDate
            charity.text = data.campaignCharity
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignsAdapter.MyViewHolder {
        var View: View = LayoutInflater.from(activity).inflate(R.layout.current_campaigns_item ,parent ,false)
        val myHolder:MyViewHolder = MyViewHolder(View)
        return myHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CampaignsAdapter.MyViewHolder, position: Int) {
        // holder.photo.setImageResource(data[position].photo)
        holder.initialize(data[position])
        when(from){
            "CharityHome" -> {
                holder.charity.visibility = View.GONE
            }
            "DonorHome" -> {
                holder.charity.visibility = View.VISIBLE
            }
        }
    }

}