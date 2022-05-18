package com.example.graduationproject.charity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.complaint.Data

import com.example.graduationproject.charity.models.Complaint
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.complaint_item.view.*
import kotlinx.android.synthetic.main.donation_with_campaign.view.*
import kotlinx.android.synthetic.main.payment_item.view.*


class ComplaintAdapter(
    var activity: Context?, var data: List<Data>?,
) : RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>(){

    class ComplaintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image  =itemView.donor_image
        val name  =itemView.complainer_name
        val reasons  =itemView.rv_complaint_reasons

        fun initialize(data: Data,activity: Context?) {
            Picasso.get().load(RetrofitInstance.IMAGE_URL+data.donor!!.image).error(R.drawable.campaign_image).into(image)
            name.text = data.donor.name

            val reasonAdapter =
                ComplaintReasonAdapter(activity, data.complaint_reason!!)

            reasons.layoutManager = GridLayoutManager(activity,2)
            reasons.setHasFixedSize(true)
            reasons.adapter = reasonAdapter
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComplaintViewHolder{
            var view: View = LayoutInflater.from(activity).inflate(R.layout.complaint_item, parent, false)
            return ComplaintViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {

            holder.initialize(data!![position],activity)

    }


}