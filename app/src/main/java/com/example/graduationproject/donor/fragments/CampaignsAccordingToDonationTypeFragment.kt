package com.example.graduationproject.donor.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.CampaignsDonationTypeJson
import com.example.graduationproject.api.donorApi.donationType.DonationTypeJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.TabLayoutSettings
import com.example.graduationproject.donor.adapters.CampaignsAdapter
import com.example.graduationproject.donor.models.Campaigns
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.fragment_capmaigns_according_to_donation_type.*
import kotlinx.android.synthetic.main.fragment_capmaigns_according_to_donation_type.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CampaignsAccordingToDonationTypeFragment : Fragment(), CampaignsAdapter.onCampaignItemClickListener {

    var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(
            R.layout.fragment_capmaigns_according_to_donation_type,
            container,
            false
        )

        Log.e("campaign fragment", "enter")
        val sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val donation_type = sharedPref.getInt("selected home donation type", 0)

        Log.e("donation in campaign", donation_type.toString())

        progressDialog = ProgressDialog(activity)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
        getCampaignsDonationType(donation_type)


        return root
    }

    fun getCampaignsDonationType(donation_type:Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getCampaignsAccordingToDonationType(donation_type)

        response.enqueue(object : Callback<CampaignsDonationTypeJson> {
            override fun onResponse(call: Call<CampaignsDonationTypeJson>, response: Response<CampaignsDonationTypeJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data
                    Log.e("response", response.body().toString())
                    if (data.isEmpty()){
                        all_no_campaign.visibility = View.VISIBLE
                        rv_campaigns.visibility = View.GONE
                        GeneralChanges().hideDialog(progressDialog!!)

                    }else {
                        all_no_campaign.visibility = View.GONE
                        rv_campaigns.visibility = View.VISIBLE

                        if (requireParentFragment()::class.java.name == ProfileFragment()::class.java.name){
                            rv_campaigns.layoutManager = LinearLayoutManager(activity,
                                RecyclerView.VERTICAL,false)
                            rv_campaigns.setHasFixedSize(true)
                            val campaignsAdapter =
                                CampaignsAdapter(activity, data,"DonorProfile", this@CampaignsAccordingToDonationTypeFragment)
                            rv_campaigns.adapter = campaignsAdapter
                            campaignsAdapter.notifyDataSetChanged()
                            GeneralChanges().hideDialog(progressDialog!!)
                        }else{
                            rv_campaigns.layoutManager = LinearLayoutManager(activity,
                                RecyclerView.HORIZONTAL,false)
                            rv_campaigns.setHasFixedSize(true)
                            val campaignsAdapter =
                                CampaignsAdapter(activity, data,"DonorHome",this@CampaignsAccordingToDonationTypeFragment)
                            rv_campaigns.adapter = campaignsAdapter
                            campaignsAdapter.notifyDataSetChanged()
                            GeneralChanges().hideDialog(progressDialog!!)
                        }
                    }

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<CampaignsDonationTypeJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }

    override fun onItemClick(data: Campaigns, position: Int, from: String) {
        TODO("Not yet implemented")
    }

}