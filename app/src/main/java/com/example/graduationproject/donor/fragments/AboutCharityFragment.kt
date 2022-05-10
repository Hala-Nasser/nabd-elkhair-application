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
import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.DonationType
import com.example.graduationproject.api.donorApi.profile.ProfileJson
import com.example.graduationproject.api.donorApi.specificDonationType.SpecificDonationTypeJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*
import kotlinx.android.synthetic.main.activity_charity_complete_signup.rv_complete_signup_donation_type
import kotlinx.android.synthetic.main.fragment_about_charity.*
import kotlinx.android.synthetic.main.fragment_about_charity.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutCharityFragment : Fragment() {

    var donation_type : List<DonationType> = listOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_about_charity, container, false)

        val b = arguments
        if (b != null) {
            var description = b.getString("charity_description")
            var donation_type_id = b.getStringArrayList("charity_donation_type")
            var phone = b.getInt("charity_phone")
            //var facebook = b.getString("charity_facebook")

//            for (id in donation_type_id)
//            rv_donation_type.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false)
//            rv_donation_type.setHasFixedSize(true)
//            var donationTypeAdapter =
//                DonationTypeAdapter(activity, donation_type,"about charity")
//            rv_donation_type.adapter = donationTypeAdapter

            root.charity_description.text = description
            root.charity_phone.text = phone.toString()
            //root.charity_facebook.text = facebook
        }

        return root
    }


    fun donationType(id: Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.specificDonationType(id)

        response.enqueue(object : Callback<SpecificDonationTypeJson> {
            override fun onResponse(call: Call<SpecificDonationTypeJson>, response: Response<SpecificDonationTypeJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    //GeneralChanges().hideDialog(progressDialog!!)

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    //GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<SpecificDonationTypeJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                //GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }



}