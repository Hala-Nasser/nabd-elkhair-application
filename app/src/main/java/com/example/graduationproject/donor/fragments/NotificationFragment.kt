package com.example.graduationproject.donor.fragments

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
import com.example.graduationproject.api.donorApi.charities.CharitiesJson
import com.example.graduationproject.api.donorApi.notifications.NotificationJson
import com.example.graduationproject.donor.adapters.CharitiesAdapter
import com.example.graduationproject.donor.adapters.NotificationAdapter
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_notification.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_notification, container, false)

        val sharedPref= requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

        val user_id = sharedPref.getInt("user_id", 0)

        getNotifications(user_id)

        return root
    }

    fun getNotifications(id: Int) {

        Log.e("noti", "enter")

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getNotifications(id)

        response.enqueue(object : Callback<NotificationJson> {
            override fun onResponse(call: Call<NotificationJson>, response: Response<NotificationJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    Log.e("data", data.toString())
                    if (data.isEmpty()){
                        rv_notifications.visibility = View.GONE
                        no_notification.visibility = View.VISIBLE
                    }else{
                        rv_notifications.visibility = View.VISIBLE
                        no_notification.visibility = View.GONE

                        rv_notifications.layoutManager = LinearLayoutManager(activity,
                            RecyclerView.VERTICAL,false)
                        rv_notifications.setHasFixedSize(true)
                        val notificationAdapter =
                            NotificationAdapter(activity, data)
                        rv_notifications.adapter = notificationAdapter
                    }

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<NotificationJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }


}