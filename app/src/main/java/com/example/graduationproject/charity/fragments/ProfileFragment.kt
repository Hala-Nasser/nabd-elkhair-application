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
import com.example.graduationproject.R
import android.widget.LinearLayout

import android.view.Display
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.api.charityApi.fcm.FCMJson
import com.example.graduationproject.api.donorApi.complaint.ComplaintJson
import com.example.graduationproject.api.donorApi.profile.ProfileJson
import com.example.graduationproject.charity.adapters.ComplaintAdapter
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.TabLayoutSettings
import com.example.graduationproject.classes.ZoomOutPageTransformer
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.fragment_charity_complaints.*
import kotlinx.android.synthetic.main.fragment_charity_info.*
import kotlinx.android.synthetic.main.fragment_charity_info.view.*
import kotlinx.android.synthetic.main.fragment_charity_profile.*
import kotlinx.android.synthetic.main.fragment_charity_profile.view.*
import kotlinx.android.synthetic.main.fragment_donation.view.*
import kotlinx.android.synthetic.main.fragment_donation_received.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import net.cachapa.expandablelayout.ExpandableLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {
    var progressDialog: ProgressDialog? = null
    var token = ""
    lateinit var sharedPref: SharedPreferences
    var img = ""
    var name = ""
    var email = ""
    var about = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_profile, container, false)
        requireActivity().charity_nav_bottom.visibility = View.VISIBLE

        sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("charity_token", "")!!

        progressDialog = ProgressDialog(activity)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
        profile()

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(AboutCharityFragment(), "عن الجمعية")
        sectionsPagerAdapter.addFragmentsAndTitles(CharityComplaintsFragment(), "شكاوي")


        root.charity_profile_view_pager.adapter = sectionsPagerAdapter

        root.charity_profile_tab_layout.setupWithViewPager(root.charity_profile_view_pager)
        root.charity_profile_view_pager.setPageTransformer(true, ZoomOutPageTransformer())

        var tabLayout = TabLayoutSettings()
        tabLayout.setTabMargin(root.charity_profile_tab_layout, 12, 12, 270)

        var onPageChangeListener: ViewPager.OnPageChangeListener =
            object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {
                    when (state) {
                        0->{
                            profile()
                            profile_charity_about.text = about
                        }
                        1 -> {
                            getComplaints()
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
                        0->{
                            profile()
                            profile_charity_about.text = about
                        }
                        1 -> {
                            getComplaints()
                        }

                    }

                }

            }
        root.charity_profile_view_pager.setOnPageChangeListener(onPageChangeListener)
        root.charity_profile_view_pager.post(Runnable { onPageChangeListener.onPageSelected(root.charity_profile_view_pager.currentItem) })


        root.charity_settings_icon.setOnClickListener {
            val sharedPref = requireActivity().getSharedPreferences(
                "sharedPref", Context.MODE_PRIVATE
            )

            val editor = sharedPref.edit()
            editor.putString("image", img)
            editor.putString("name", name)
            editor.putString("email", email)
            editor.apply()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.charityContainer, CharitySettingsFragment()).addToBackStack(null)
                .commit()
        }

        root.charity_donation_received.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.charityContainer, DonationReceivedFragment()).addToBackStack(null)
                .commit()
        }

        return root
    }


    fun profile() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityProfile("Bearer $token")

        response.enqueue(object : Callback<FCMJson> {
            override fun onResponse(call: Call<FCMJson>, response: Response<FCMJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data
                    img = data.image
                    name = data.name
                    email = data.email
                    about = data.about
                    Picasso.get().load(RetrofitInstance.IMAGE_URL + img).into(charity_profile_image)
                    charity_profile_name.text = name
                    charity_profile_email.text = email
                    GeneralChanges().hideDialog(progressDialog!!)

                    val editor = sharedPref.edit()
                    editor.putString("about", data.about)
                    editor.putInt("charity_notification", data.notification_status)
                    editor.apply()

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<FCMJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }

    fun getComplaints() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getComplaints("Bearer $token")

        response.enqueue(object : Callback<ComplaintJson> {
            override fun onResponse(call: Call<ComplaintJson>, response: Response<ComplaintJson>) {
                val data = response.body()
                if (response.isSuccessful) {
                    Log.e("complaints", data!!.data.toString())

                    if (data.data!!.isEmpty()) {
                        no_complaints.visibility = View.VISIBLE
                        rv_charity_complaints.visibility = View.GONE
                    } else {
                        no_complaints.visibility = View.GONE
                        rv_charity_complaints.visibility = View.VISIBLE
                        rv_charity_complaints.layoutManager = LinearLayoutManager(
                            activity,
                            RecyclerView.VERTICAL, false
                        )
                        rv_charity_complaints.setHasFixedSize(true)
                        val donationAdapter =
                            ComplaintAdapter(requireActivity(), data.data)
                        rv_charity_complaints.adapter = donationAdapter
                    }

                    GeneralChanges().hideDialog(progressDialog!!)
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<ComplaintJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }

}