package com.example.graduationproject.charity.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
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
import com.example.graduationproject.charity.adapters.RequestDonationAdapter
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.fragment_donation_requests.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.content.Intent
import android.os.CountDownTimer
import com.example.graduationproject.api.charityApi.donation.DonationJson
import com.example.graduationproject.classes.PrefUtil
import com.example.graduationproject.classes.TimerExpiredReceiver
import kotlinx.android.synthetic.main.fragment_charity_settings.view.*
import kotlinx.android.synthetic.main.fragment_charity_settings.view.back
import kotlinx.android.synthetic.main.fragment_donation_requests.view.*
import java.util.*


class DonationRequestsFragment : Fragment() {
    var token = ""
    var progressDialog: ProgressDialog? = null
    lateinit var donationAdapter: RequestDonationAdapter
    var onActivityStateChanged: RequestDonationAdapter.OnActivityStateChanged? = null

    companion object {
        @SuppressLint("ServiceCast")
        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long {
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }
    }

    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_donation_requests, container, false)

        donationAdapter = RequestDonationAdapter(requireActivity(), null, this)
        requireActivity().charity_nav_bottom.visibility = View.GONE
        var sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("charity_token", "")!!

        getDonations()
        progressDialog = ProgressDialog(activity)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return root
    }

    fun getDonations() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getDonationRequests("Bearer $token")

        response.enqueue(object : Callback<DonationJson> {
            override fun onResponse(call: Call<DonationJson>, response: Response<DonationJson>) {
                val data = response.body()
                if (response.isSuccessful) {

                    rv_donation_requests.layoutManager = LinearLayoutManager(
                        activity,
                        RecyclerView.VERTICAL, false
                    )
                    rv_donation_requests.setHasFixedSize(true)
                    donationAdapter =
                        RequestDonationAdapter(
                            requireActivity(),
                            data!!.data,
                            this@DonationRequestsFragment
                        )
                    rv_donation_requests.adapter = donationAdapter
                    onActivityStateChanged = donationAdapter.registerActivityState()
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

    override fun onPause() {
        donationAdapter.registerActivityState().onPaused()
        super.onPause()
    }

}