package com.example.graduationproject.charity.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import com.example.graduationproject.api.charityApi.fcm.FCMJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_charity_info.*
import kotlinx.android.synthetic.main.fragment_charity_info.view.*
import kotlinx.android.synthetic.main.fragment_charity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AboutCharityFragment : Fragment() {
    var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_charity_info, container, false)
        val sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("charity_token", "")!!

        profile()
        //root.profile_charity_about.text = about

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
                    profile_charity_about.text = data.about

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<FCMJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }


}