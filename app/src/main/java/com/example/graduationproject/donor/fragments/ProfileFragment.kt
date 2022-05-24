package com.example.graduationproject.donor.fragments

import android.app.ProgressDialog
import android.content.Context
import  android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.donationType.DonationTypeJson
import com.example.graduationproject.api.donorApi.profile.ProfileJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.TabLayoutSettings
import com.example.graduationproject.donor.adapters.PageAdapterDonationType
import com.example.graduationproject.donor.adapters.PagerAdapterMyDonation
import kotlinx.android.synthetic.main.activity_donor_main.*
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.profile_image
import kotlinx.android.synthetic.main.fragment_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    var progressDialog: ProgressDialog? = null
    var donation_type_ids = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        requireActivity().nav_bottom.visibility=View.VISIBLE

        val sharedPref= requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val user_id = sharedPref.getInt("user_id", 0)

        progressDialog = ProgressDialog(activity)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
        profile(user_id)
        getDonationType()

        root.profile_tab_layout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                root.campaigns_profile_viewpager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        root.settings.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,SettingsFragment()).addToBackStack(null).commit()
            requireActivity().nav_bottom.visibility=View.GONE
        }



        return root
    }

    fun profile(id: Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.profile(id)

        response.enqueue(object : Callback<ProfileJson> {
            override fun onResponse(call: Call<ProfileJson>, response: Response<ProfileJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data
                    Picasso.get().load(RetrofitInstance.IMAGE_URL+data.image).into(profile_image)
                    username.text = data.name
                    email.text = data.email
                    campaign_count.text = data.capmaign_donations_count.toString()
                    charity_count.text = data.charity_donations_count.toString()
                    GeneralChanges().hideDialog(progressDialog!!)

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<ProfileJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }


    fun getDonationType() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getDonationType()

        response.enqueue(object : Callback<DonationTypeJson> {
            override fun onResponse(call: Call<DonationTypeJson>, response: Response<DonationTypeJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    donation_type_ids.add(0)
                    profile_tab_layout.addTab(profile_tab_layout.newTab().setText("الكل"), true)

                    for (donation_type in data){
                        donation_type_ids.add(donation_type.id)
                        profile_tab_layout.addTab(profile_tab_layout.newTab().setText(donation_type.name), false)
                    }

                    TabLayoutSettings().setTabMargin(profile_tab_layout, 10, 10, 100)

                    val adapter = PagerAdapterMyDonation(childFragmentManager, profile_tab_layout.tabCount, donation_type_ids)
                    campaigns_profile_viewpager.adapter = adapter
                    campaigns_profile_viewpager.offscreenPageLimit = 1
                    campaigns_profile_viewpager.addOnPageChangeListener(
                        TabLayout.TabLayoutOnPageChangeListener(
                            profile_tab_layout
                        )
                    )

                    profile_tab_layout.tabMode = TabLayout.MODE_SCROLLABLE

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<DonationTypeJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

}