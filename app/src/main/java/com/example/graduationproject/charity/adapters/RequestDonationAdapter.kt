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
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.graduationproject.api.charityApi.donation.Data
import com.example.graduationproject.api.charityApi.donation.Donor
import com.example.graduationproject.api.donorApi.forgotPassword.ForgotPasswordJson
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bottom_donation_with_campaign.view.*
import kotlinx.android.synthetic.main.bottom_sheet_manually.view.*
import kotlinx.android.synthetic.main.donation_requests_item.view.*
import kotlinx.android.synthetic.main.donation_without_campaign.view.*
import kotlinx.android.synthetic.main.donors_item.view.donor_image
import kotlinx.android.synthetic.main.donors_item.view.donor_name
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RequestDonationAdapter(
    var activity: Context?, var data: List<Data>
) : RecyclerView.Adapter<RequestDonationAdapter.DonationRequestsViewHolder>(){


    class DonationRequestsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image  =itemView.donor_image
        val name  =itemView.donor_name
        val donation_accept  =itemView.donation_accept
        val donation_declined  =itemView.donation_declined

        fun initialize(data: Donor) {
            Picasso.get().load(RetrofitInstance.IMAGE_URL+data.image).into(image)
            name.text = data.name
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DonationRequestsViewHolder{
            var view: View = LayoutInflater.from(activity).inflate(R.layout.donation_requests_item, parent, false)
          return DonationRequestsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DonationRequestsViewHolder, position: Int) {

        holder.initialize(data[position].donor!!)
        holder.itemView.setOnClickListener {
            bottomSheet(activity as Activity, position)
        }

        holder.donation_accept.setOnClickListener {
            setDonationAcceptance(data[position].id,1)
        }

        holder.donation_declined.setOnClickListener {
            setDonationAcceptance(data[position].id,0)
          }



    }

    fun bottomSheet(activity: Activity, position: Int){
        var view = activity.layoutInflater.inflate(R.layout.bottom_sheet_manually, null)
        var bottomSheetDialog = BottomSheetDialog(activity)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.setCanceledOnTouchOutside(false)
            if (data[position].campaign != null){
                view.bs_campaign_card.visibility = View.VISIBLE
                Picasso.get().load(RetrofitInstance.IMAGE_URL+data[position].campaign!!.image).into(view.bs_campaign_image)
                view.bs_campaign_name.text = data[position].campaign!!.name
            }else{
                view.bs_campaign_card.visibility = View.GONE
            }

        view.close.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
//        view.accept_donation.setOnClickListener {
//            bottomSheetDialog.dismiss()
//        }
        val donation  =data[position]
        view.bs_donor_name.text = donation.donor!!.name
        view.bs_donation_amount.text = donation.donation_amount
        view.bs_donor_prefecture.text = donation.donor_district
        view.bs_donor_city.text = donation.donor_city
        view.bs_donor_address.text = donation.donor_address
        view.bs_donor_phone.text = donation.donor_phone
        bottomSheetDialog.show()
    }

    fun setDonationAcceptance(donation_id: Int,acceptance: Int) {

        var sharedPref = activity!!.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        var token = sharedPref.getString("charity_token", "")!!

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.setDonationAcceptance("Bearer $token",donation_id,acceptance)

        response.enqueue(object : Callback<ForgotPasswordJson> {
            override fun onResponse(call: Call<ForgotPasswordJson>, response: Response<ForgotPasswordJson>) {
                val data = response.body()
                if (response.isSuccessful) {
                    Toast.makeText(activity, data!!.message, Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<ForgotPasswordJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }
}