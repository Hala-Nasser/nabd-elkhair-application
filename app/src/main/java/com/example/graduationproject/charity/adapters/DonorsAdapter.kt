package com.example.graduationproject.charity.adapters

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.charity.fragments.AddComplaintFragment
import com.example.graduationproject.charity.models.Donation
import com.example.graduationproject.donor.models.Donor
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_donation_with_campaign.view.*
import kotlinx.android.synthetic.main.bottom_sheet_manually.view.*
import kotlinx.android.synthetic.main.charity_item.view.*
import kotlinx.android.synthetic.main.donation_with_campaign.view.*
import kotlinx.android.synthetic.main.donors_item.view.*

class DonorsAdapter(var activity: Context?, var data :List<Donation>?=null,var from:String,var fragment : FragmentManager) : RecyclerView.Adapter<DonorsAdapter.MyViewHolder>(){

    var campaignName:String?=null
    var campaignImg:Int?=null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image  =itemView.donor_image
        val name  =itemView.donor_name
        val details_btn  =itemView.donation_details_btn
        val card  =itemView.donor_card_view

        fun initialize(data: Donor?) {
                     //image.setImageResource(data!!.image!!)
                     name.text = data!!.name
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorsAdapter.MyViewHolder {
        var View: View = LayoutInflater.from(activity).inflate(R.layout.donors_item ,parent ,false)
        val myHolder: DonorsAdapter.MyViewHolder = DonorsAdapter.MyViewHolder(View)
        return myHolder
    }

    override fun getItemCount(): Int {
        return data!!.size
    }


    override fun onBindViewHolder(holder: DonorsAdapter.MyViewHolder, position: Int) {
        holder.initialize(data!![position].donors)

        if (from=="CampaignDonationAdapter"){
            holder.card.apply {
                strokeColor = resources.getColor(R.color.app_color)
                setCardBackgroundColor(resources.getColor(R.color.white))
            }
        }else{
            holder.card.apply {
                strokeColor = resources.getColor(R.color.white)
                setCardBackgroundColor(resources.getColor(R.color.donor_card))
            }
        }
        holder.details_btn.setOnClickListener {
            bottomSheet(activity as Activity,position)
        }
    }


    fun bottomSheet(activity: Activity,position: Int){
        var view = activity.layoutInflater.inflate(R.layout.bottom_sheet_manually, null)
        var bottomSheetDialog = BottomSheetDialog(activity)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.setCanceledOnTouchOutside(false)
        if (from=="CampaignDetails"){
            view.bs_campaign_card.visibility = View.GONE
            view.add_complaint_img.visibility = View.VISIBLE
        }else {
            view.add_complaint_img.visibility = View.GONE
            view.bs_campaign_card.visibility = View.VISIBLE
        }

        view.add_complaint_img.setOnClickListener {
            val f = AddComplaintFragment()
            val b= Bundle()
            b.putString("from","Charity")
            f.arguments=b
            fragment.beginTransaction().replace(R.id.charityContainer,f).commit()
        }
        view.close.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
//        view.accept_donation.setOnClickListener {
//            bottomSheetDialog.dismiss()
//        }
        val donation  = data!![position]

        view.bs_campaign_image.setImageResource(campaignImg!!)
        view.bs_campaign_name.text = campaignName
        view.bs_donor_name.text = donation.donors!!.name
        view.bs_donation_amount.text = donation.donationAmount
        view.bs_donor_prefecture.text = donation.donorDistrict
        view.bs_donor_city.text =donation.donorCity
        view.bs_donor_address.text = donation.donorAddress
        view.bs_donor_phone.text = donation.donorPhoneNumber
        bottomSheetDialog.show()
    }
}