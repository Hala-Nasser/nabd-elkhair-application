package com.example.graduationproject.charity.fragments

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
import com.example.graduationproject.api.charityApi.donation.DonationJson
import com.example.graduationproject.charity.adapters.DonationAdapter
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.fragment_donation_not_received.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonationNotReceivedFragment : Fragment() {

    var token = ""
    var progressDialog: ProgressDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_donation_not_received, container, false)

        var sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("charity_token", "")!!

        progressDialog = ProgressDialog(activity)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")

        getDonations()
        return root
    }

    fun getDonations() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getDonationNotReceived("Bearer $token")

        response.enqueue(object : Callback<DonationJson> {
            override fun onResponse(call: Call<DonationJson>, response: Response<DonationJson>) {
                val data = response.body()
                if (response.isSuccessful) {

                    if(data!!.data.isEmpty()){
                        no_donations.visibility = View.VISIBLE
                        rv_donation_tab_notReceived.visibility = View.GONE
                    }else{
                        no_donations.visibility = View.GONE
                        rv_donation_tab_notReceived.visibility = View.VISIBLE
                        rv_donation_tab_notReceived.layoutManager = LinearLayoutManager(
                            activity,
                            RecyclerView.VERTICAL, false
                        )
                        rv_donation_tab_notReceived.setHasFixedSize(true)
                        val donationAdapter =
                            DonationAdapter(requireActivity(), data!!.data,"DonationNotReceivedFragment",requireActivity().supportFragmentManager)
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
}