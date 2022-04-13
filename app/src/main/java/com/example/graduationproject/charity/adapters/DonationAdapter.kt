package com.example.graduationproject.charity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.charity.models.Donation
import kotlinx.android.synthetic.main.current_campaigns_item.view.*
import kotlinx.android.synthetic.main.donation_with_campaign.view.*
import kotlinx.android.synthetic.main.donors_item.view.*
import kotlinx.android.synthetic.main.fragment_clothes_donation.view.*

import android.app.Activity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_manually.view.*
import kotlinx.android.synthetic.main.donation_without_campaign.view.*


class DonationAdapter(
    var activity: Context?, var data: List<Donation>, var from:String
) : RecyclerView.Adapter<DonationAdapter.WithoutCampaignViewHolder>(){

    class WithoutCampaignViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image  =itemView.donation_without_campaign_image
        val name  =itemView.donation_without_campaign_name
        val details_btn  =itemView.donation_without_details_btn
        val donation_received  =itemView.donation_received
        val card  =itemView.cv_donation

        fun initialize(data: Donation) {
//            image.setImageResource(data.donors!!.image!!)
            name.text = data.donors!!.name
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WithoutCampaignViewHolder{
            var view: View = LayoutInflater.from(activity).inflate(R.layout.donation_without_campaign, parent, false)
            return WithoutCampaignViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: WithoutCampaignViewHolder, position: Int) {

            holder.initialize(data[position])
            if (from=="DonationReceivedFragment"){
                holder.details_btn.visibility = View.GONE
                holder.donation_received.visibility = View.VISIBLE
                holder.card.cardElevation = 0f
            }else{
                holder.details_btn.visibility = View.VISIBLE
                holder.donation_received.visibility = View.GONE
                holder.card.cardElevation = 3f

                holder.details_btn.setOnClickListener {
                    bottomSheet(activity as Activity, position)
                }
            }

    }

    fun bottomSheet(activity: Activity, position: Int){
        var view = activity.layoutInflater.inflate(R.layout.bottom_sheet_manually, null)
        var bottomSheetDialog = BottomSheetDialog(activity)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.setCanceledOnTouchOutside(false)
        view.bs_campaign_card.visibility = View.GONE
        view.close.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
//        view.accept_donation.setOnClickListener {
//            bottomSheetDialog.dismiss()
//        }
        val donation  =data[position]
        view.bs_donor_name.text = donation.donors!!.name
        view.bs_donation_amount.text = donation.donationAmount
        view.bs_donor_prefecture.text = donation.donorDistrict
        view.bs_donor_city.text = donation.donorCity
        view.bs_donor_address.text = donation.donorAddress
        view.bs_donor_phone.text = donation.donorPhoneNumber
        bottomSheetDialog.show()
    }

}