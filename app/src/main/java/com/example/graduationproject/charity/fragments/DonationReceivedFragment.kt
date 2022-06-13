package com.example.graduationproject.charity.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.api.charityApi.donation.DonationJson
import com.example.graduationproject.charity.adapters.DonationAdapter
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.ZoomOutPageTransformer
import com.example.graduationproject.network.RetrofitInstance
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.add_campaign_phase_four.*
import kotlinx.android.synthetic.main.add_campaign_phase_one.*
import kotlinx.android.synthetic.main.fragment_add_campaign.view.*
import kotlinx.android.synthetic.main.fragment_charity_settings.view.*
import kotlinx.android.synthetic.main.fragment_donation.view.*
import kotlinx.android.synthetic.main.fragment_donation.view.tab_layout
import kotlinx.android.synthetic.main.fragment_donation_not_received.*
import kotlinx.android.synthetic.main.fragment_donation_received.view.*
import kotlinx.android.synthetic.main.fragment_donation_received.view.back
import kotlinx.android.synthetic.main.fragment_donation_requests.*
import kotlinx.android.synthetic.main.fragment_donation_tab_received.*
import kotlinx.android.synthetic.main.fragment_donation_without_campaign.*
import kotlinx.android.synthetic.main.fragment_donation_without_campaign.rv_donation_without_campaign
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonationReceivedFragment : Fragment() {

    var token = ""
    var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_donation_received, container, false)
        requireActivity().charity_nav_bottom.visibility = View.GONE
        var sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("charity_token", "")!!

        progressDialog = ProgressDialog(activity)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(
            DonationNotReceivedFragment(),
            "التبرعات الغير مستلمة"
        )
        sectionsPagerAdapter.addFragmentsAndTitles(
            DonationTabReceivedFragment(),
            "التبرعات المستلمة"
        )
        root.donation_received_viewpager.adapter = sectionsPagerAdapter

        root.donation_received_tab_layout.setupWithViewPager(root.donation_received_viewpager)
        root.donation_received_viewpager.setPageTransformer(true, ZoomOutPageTransformer())

        var onPageChangeListener: ViewPager.OnPageChangeListener =
            object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {
                    when (state) {
                        0 -> {
                            getDonationsNotReceived()
                        }
                        1 -> {
                            getDonationsReceived()
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
                            getDonationsNotReceived()
                        }
                        1 -> {
                            getDonationsReceived()
                        }

                    }

                }

            }
        root.donation_received_viewpager.setOnPageChangeListener(onPageChangeListener)
        root.donation_received_viewpager.post(Runnable { onPageChangeListener.onPageSelected(root.donation_received_viewpager.currentItem) })

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return root
    }


    fun getDonationsNotReceived() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getDonationNotReceived("Bearer $token")

        response.enqueue(object : Callback<DonationJson> {
            override fun onResponse(call: Call<DonationJson>, response: Response<DonationJson>) {
                val data = response.body()
                if (response.isSuccessful) {

                    if (data!!.data.isEmpty()) {
                        no_notReceived_donations.visibility = View.VISIBLE
                        rv_donation_tab_notReceived.visibility = View.GONE
                    } else {
                        no_notReceived_donations.visibility = View.GONE
                        rv_donation_tab_notReceived.visibility = View.VISIBLE
                        rv_donation_tab_notReceived.layoutManager = LinearLayoutManager(
                            activity,
                            RecyclerView.VERTICAL, false
                        )
                        rv_donation_tab_notReceived.setHasFixedSize(true)
                        val donationAdapter =
                            DonationAdapter(
                                requireActivity(),
                                data!!.data,
                                "DonationNotReceivedFragment",
                                requireActivity().supportFragmentManager
                            )
                        rv_donation_tab_notReceived.adapter = donationAdapter
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

    fun getDonationsReceived() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getDonationReceived("Bearer $token")

        response.enqueue(object : Callback<DonationJson> {
            override fun onResponse(call: Call<DonationJson>, response: Response<DonationJson>) {
                val data = response.body()
                if (response.isSuccessful) {
                    Log.e("enter tab received", "yes")

                    if (data!!.data.isEmpty()) {
                        no_received_donations.visibility = View.VISIBLE
                        rv_donation_tab_received.visibility = View.GONE
                    } else {
                        no_received_donations.visibility = View.GONE
                        rv_donation_tab_received.visibility = View.VISIBLE
                        rv_donation_tab_received.layoutManager = LinearLayoutManager(
                            activity,
                            RecyclerView.VERTICAL, false
                        )
                        rv_donation_tab_received.setHasFixedSize(true)
                        val donationAdapter =
                            DonationAdapter(
                                requireActivity(),
                                data!!.data,
                                "DonationReceivedFragment",
                                requireActivity().supportFragmentManager
                            )
                        rv_donation_tab_received.adapter = donationAdapter
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