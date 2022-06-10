package com.example.graduationproject.charity.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.charityApi.CampaignDonation.CampaignDonationJson
import com.example.graduationproject.api.charityApi.donation.DonationJson
import com.example.graduationproject.api.donorApi.logout.LogoutJson
import com.example.graduationproject.charity.adapters.CampaignDonationAdapter
import com.example.graduationproject.charity.models.Donation
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.donor.SignInActivity
import com.example.graduationproject.donor.models.Charity
import com.example.graduationproject.donor.models.DonationType
import com.example.graduationproject.donor.models.Donor
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.fragment_donation_with_campaign.*
import kotlinx.android.synthetic.main.fragment_donation_with_campaign.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DonationWithCampaignFragment : Fragment(){

    var token = ""
    lateinit var donationAdapter: CampaignDonationAdapter
    var progressDialog: ProgressDialog? = null
    lateinit var sharedPref:SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_donation_with_campaign, container, false)

//        sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
//        token = sharedPref.getString("charity_token", "")!!
//
//        progressDialog = ProgressDialog(activity)
//        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
//
//        getDonations()
        return root

    }

    fun getDonations() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getCampaignDonations("Bearer $token")

        response.enqueue(object : Callback<CampaignDonationJson> {
            override fun onResponse(call: Call<CampaignDonationJson>, response: Response<CampaignDonationJson>) {
                val data = response.body()
                if (response.isSuccessful) {
                    val editor = sharedPref.edit()
                    editor.putInt("campaignDonationCount", data!!.data.size)
                    editor.apply()

                    if(data.data.isEmpty()){
                        no_donations.visibility = View.VISIBLE
                        rv_donation_with_campaign.visibility = View.GONE
                    }else{
                        no_donations.visibility = View.GONE
                        rv_donation_with_campaign.visibility = View.VISIBLE

                        rv_donation_with_campaign.layoutManager = LinearLayoutManager(
                            activity,
                            RecyclerView.VERTICAL, false
                        )
                        rv_donation_with_campaign.setHasFixedSize(true)
                        var donationAdapter =
                            CampaignDonationAdapter(requireActivity(), data.data,requireActivity().supportFragmentManager)
                        rv_donation_with_campaign.adapter = donationAdapter
                    }

                    GeneralChanges().hideDialog(progressDialog!!)
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<CampaignDonationJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }


}