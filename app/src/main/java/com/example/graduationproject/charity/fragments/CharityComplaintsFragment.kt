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
import com.example.graduationproject.api.donorApi.complaint.ComplaintJson
import com.example.graduationproject.charity.adapters.ComplaintAdapter
import com.example.graduationproject.charity.adapters.DonationAdapter
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.fragment_charity_complaints.*
import kotlinx.android.synthetic.main.fragment_donation_tab_received.*
import kotlinx.android.synthetic.main.fragment_donation_tab_received.rv_donation_tab_received
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CharityComplaintsFragment : Fragment() {
  var token = ""
  var progressDialog: ProgressDialog? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    var root = inflater.inflate(R.layout.fragment_charity_complaints, container, false)

    var sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    token = sharedPref.getString("charity_token", "")!!

    progressDialog = ProgressDialog(activity)
    GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
    getComplaints()
    return root
  }

      fun getComplaints() {

        val retrofitInstance =
          RetrofitInstance.create()
        val response = retrofitInstance.getComplaints("Bearer $token")

        response.enqueue(object : Callback<ComplaintJson> {
          override fun onResponse(call: Call<ComplaintJson>, response: Response<ComplaintJson>) {
            val data = response.body()
            if (response.isSuccessful) {
              Log.e("complaints",data!!.data.toString())

              if(data.data!!.isEmpty()){
                no_complaints.visibility = View.VISIBLE
                rv_charity_complaints.visibility = View.GONE
              }else{
                no_complaints.visibility = View.GONE
                rv_charity_complaints.visibility = View.VISIBLE
                rv_charity_complaints.layoutManager = LinearLayoutManager(
                  activity,
                  RecyclerView.VERTICAL, false
                )
                rv_charity_complaints.setHasFixedSize(true)
                val donationAdapter =
                  ComplaintAdapter(requireActivity(),data.data)
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