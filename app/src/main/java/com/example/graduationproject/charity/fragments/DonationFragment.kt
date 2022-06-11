package com.example.graduationproject.charity.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.api.charityApi.CampaignDonation.CampaignDonationJson
import com.example.graduationproject.api.charityApi.donation.DonationJson
import com.example.graduationproject.api.charityApi.donationCount.DonationCountJson
import com.example.graduationproject.charity.adapters.CampaignDonationAdapter
import com.example.graduationproject.charity.adapters.DonationAdapter
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.ZoomOutPageTransformer
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import kotlinx.android.synthetic.main.fragment_charity_info.*
import kotlinx.android.synthetic.main.fragment_charity_notification.view.*
import kotlinx.android.synthetic.main.fragment_charity_profile.view.*
import kotlinx.android.synthetic.main.fragment_donation.*
import kotlinx.android.synthetic.main.fragment_donation.view.*
import kotlinx.android.synthetic.main.fragment_donation_with_campaign.*
import kotlinx.android.synthetic.main.fragment_donation_without_campaign.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DonationFragment : Fragment() {
    var token = ""
    var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_donation, container, false)

        val sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val user_image = sharedPref.getString("charity_image", "")
        token = sharedPref.getString("charity_token", "")!!
        progressDialog = ProgressDialog(activity)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
        getDonationsCount()
        Picasso.get().load(RetrofitInstance.IMAGE_URL + user_image)
            .into(root.donation_profile_image)

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(DonationWithCampaignFragment(), "تبرع بحملة")
        sectionsPagerAdapter.addFragmentsAndTitles(
            DonationWithoutCampaignFragment(),
            "تبرع بدون حملة"
        )
        root.donation_viewpager.adapter = sectionsPagerAdapter

        root.tab_layout.setupWithViewPager(root.donation_viewpager)
        root.donation_viewpager.setPageTransformer(true, ZoomOutPageTransformer())

        var onPageChangeListener: ViewPager.OnPageChangeListener =
            object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {
                    when (state) {
                        0 -> {
                            getCampaignDonations()
                        }
                        1 -> {
                            getDonations()
                        }

                    }
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            getCampaignDonations()
                        }
                        1 -> {
                            getDonations()
                        }

                    }

                }

            }
        root.donation_viewpager.setOnPageChangeListener(onPageChangeListener)
        root.donation_viewpager.post(Runnable { onPageChangeListener.onPageSelected(root.donation_viewpager.currentItem) })

        return root
    }

    fun getDonationsCount() {
        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getDonationsCount("Bearer $token")

        response.enqueue(object : Callback<DonationCountJson> {
            override fun onResponse(
                call: Call<DonationCountJson>,
                response: Response<DonationCountJson>
            ) {
                val data = response.body()
                if (response.isSuccessful) {
                    campaign_donation_count.text = data!!.data.donationWithCampainCount.toString()
                    without_campaign_donation_count.text =
                        data.data.donationWithoutCampainCount.toString()
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<DonationCountJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

    fun getCampaignDonations() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getCampaignDonations("Bearer $token")

        response.enqueue(object : Callback<CampaignDonationJson> {
            override fun onResponse(
                call: Call<CampaignDonationJson>,
                response: Response<CampaignDonationJson>
            ) {
                val data = response.body()
                if (response.isSuccessful) {

                    if (data!!.data.isEmpty()) {
                        no_donations.visibility = View.VISIBLE
                        rv_donation_with_campaign.visibility = View.GONE
                    } else {
                        no_donations.visibility = View.GONE
                        rv_donation_with_campaign.visibility = View.VISIBLE

                        rv_donation_with_campaign.layoutManager = LinearLayoutManager(
                            activity,
                            RecyclerView.VERTICAL, false
                        )
                        rv_donation_with_campaign.setHasFixedSize(true)
                        var donationAdapter =
                            CampaignDonationAdapter(
                                requireActivity(),
                                data!!.data,
                                requireActivity().supportFragmentManager
                            )
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

    fun getDonations() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getWithoutCampaignDonations("Bearer $token")

        response.enqueue(object : Callback<DonationJson> {
            override fun onResponse(call: Call<DonationJson>, response: Response<DonationJson>) {
                val data = response.body()
                if (response.isSuccessful) {

                    if (data!!.data.isEmpty()) {
                        no_without_donations.visibility = View.VISIBLE
                        rv_donation_without_campaign.visibility = View.GONE
                    } else {
                        no_without_donations.visibility = View.GONE
                        rv_donation_without_campaign.visibility = View.VISIBLE
                        rv_donation_without_campaign.layoutManager = LinearLayoutManager(
                            activity,
                            RecyclerView.VERTICAL, false
                        )
                        rv_donation_without_campaign.setHasFixedSize(true)
                        val donationAdapter =
                            DonationAdapter(
                                requireActivity(),
                                data!!.data,
                                "DonationFragment",
                                requireActivity().supportFragmentManager
                            )
                        rv_donation_without_campaign.adapter = donationAdapter
                    }



                    GeneralChanges().hideDialog(progressDialog!!)
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<DonationJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }
}