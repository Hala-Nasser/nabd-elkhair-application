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
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.api.donorApi.profile.ProfileJson
import com.example.graduationproject.classes.GeneralChanges
import kotlinx.android.synthetic.main.activity_donor_main.*
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_notification.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.profile_image
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    var progressDialog: ProgressDialog? = null

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

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
//        sectionsPagerAdapter.addFragmentsAndTitles(AllDonationFragment(), "الكل")
//        sectionsPagerAdapter.addFragmentsAndTitles(MoneyDonationFragment(), "المال")
//        sectionsPagerAdapter.addFragmentsAndTitles(FoodDonationFragment(), "الغذاء")
//        sectionsPagerAdapter.addFragmentsAndTitles(ClothesDonationFragment(), "الملابس")
        root.campaigns_profile_viewpager.adapter = sectionsPagerAdapter
        root.profile_tab_layout.setupWithViewPager(root.campaigns_profile_viewpager)

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

}