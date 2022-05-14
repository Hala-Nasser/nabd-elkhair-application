package com.example.graduationproject.donor.fragments

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
import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.DonationType
import com.example.graduationproject.api.donorApi.campaignsAccordingToCharity.CampaignsCharityJson
import com.example.graduationproject.charity.models.Donation
import com.example.graduationproject.donor.adapters.CampaignsAdapter
import com.example.graduationproject.models.Campaigns
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_charity_campaigns.*
import kotlinx.android.synthetic.main.fragment_charity_campaigns.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharityCampaignsFragment : Fragment(), CampaignsAdapter.onCampaignItemClickListener {

//    private lateinit var campaignsList: MutableList<Campaigns>
    private var donationList = ArrayList<Donation>()
    private var donationList1 = ArrayList<Donation>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_charity_campaigns, container, false)


        val b = arguments
        if (b != null) {
            var id = b.getInt("charity_id", 0)
            getCampaignsCharity(id)
        }

        return root
    }

    override fun onItemClick(data: Campaigns, position: Int, from: String) {
        val fragment = CampaignDetailsFragment()
        val b = Bundle()
        b.putInt("campaign_id", data.id)
        b.putString("campaign_name", data.name)
        b.putString("campaign_description", data.description)
        b.putString("campaign_image", data.image)
        b.putString("campaign_date", data.expiry_date)
        b.putParcelable("campaign_charity", data.charity)
        b.putParcelableArrayList("campaign_donation_type", data.donation_type as java.util.ArrayList<DonationType>)
        Log.e("DT put in bundle", data.donation_type.toString())


        fragment.arguments = b

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, fragment).addToBackStack(null).commit()
        requireActivity().nav_bottom.visibility=View.GONE
    }


    fun getCampaignsCharity(charity: Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getCampaignsAccordingToCharity(charity)

        response.enqueue(object : Callback<CampaignsCharityJson> {
            override fun onResponse(
                call: Call<CampaignsCharityJson>,
                response: Response<CampaignsCharityJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data
                    Log.e("response", response.body().toString())

                    if (data.isEmpty()) {
                        no_campaign.visibility = View.VISIBLE
                        rv_campaigns.visibility = View.GONE

                    } else {
                        no_campaign.visibility = View.GONE
                        rv_campaigns.visibility = View.VISIBLE

                        rv_campaigns.layoutManager = LinearLayoutManager(
                            activity,
                            RecyclerView.VERTICAL, false
                        )
                        rv_campaigns.setHasFixedSize(true)
                        val campaignsAdapter =
                            CampaignsAdapter(activity, data, "CharityCampaigns", this@CharityCampaignsFragment)
                        rv_campaigns.adapter = campaignsAdapter

                    }

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<CampaignsCharityJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

}