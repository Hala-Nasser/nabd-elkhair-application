package com.example.graduationproject.charity.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.api.charityApi.donation.DonationJson
import com.example.graduationproject.api.charityApi.donationCount.DonationCountJson
import com.example.graduationproject.charity.adapters.CampaignDonationAdapter
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.ZoomOutPageTransformer
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import kotlinx.android.synthetic.main.fragment_charity_notification.view.*
import kotlinx.android.synthetic.main.fragment_donation.*
import kotlinx.android.synthetic.main.fragment_donation.view.*
import kotlinx.android.synthetic.main.fragment_donation_with_campaign.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DonationFragment : Fragment() {
    var token = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_donation, container, false)



        val sharedPref= requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val user_image = sharedPref.getString("charity_image", "")
        token = sharedPref.getString("charity_token", "")!!
        getDonationsCount()
        Picasso.get().load(RetrofitInstance.IMAGE_URL+user_image).into(root.donation_profile_image)

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(DonationWithCampaignFragment(),"تبرع بحملة")
        sectionsPagerAdapter.addFragmentsAndTitles(DonationWithoutCampaignFragment(),"تبرع بدون حملة")
        root.donation_viewpager.adapter = sectionsPagerAdapter

        root.tab_layout.setupWithViewPager(root.donation_viewpager)
        root.donation_viewpager.setPageTransformer(true, ZoomOutPageTransformer())
        return root
    }

    fun getDonationsCount() {
        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getDonationsCount("Bearer $token")

        response.enqueue(object : Callback<DonationCountJson> {
            override fun onResponse(call: Call<DonationCountJson>, response: Response<DonationCountJson>) {
                val data = response.body()
                if (response.isSuccessful) {
                    campaign_donation_count.text = data!!.data.donationWithCampainCount.toString()
                    without_campaign_donation_count.text = data.data.donationWithoutCampainCount.toString()
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<DonationCountJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }
}