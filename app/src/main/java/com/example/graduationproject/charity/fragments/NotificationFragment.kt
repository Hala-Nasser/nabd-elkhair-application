package com.example.graduationproject.charity.fragments

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
import com.example.graduationproject.api.donorApi.notifications.NotificationJson
import com.example.graduationproject.donor.adapters.NotificationAdapter
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_charity_notification.*
import kotlinx.android.synthetic.main.fragment_charity_notification.view.*
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_notification.rv_notifications
import kotlinx.android.synthetic.main.fragment_notification.view.*
import kotlinx.android.synthetic.main.fragment_notification.view.profile_image
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_notification, container, false)
        val sharedPref= requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val user_image = sharedPref.getString("charity_image", "")
        Picasso.get().load(RetrofitInstance.IMAGE_URL+user_image).into(root.charity_notification_image)

        val user_id = sharedPref.getInt("charity_id", 0)

        getNotifications(user_id)
        return root
    }


    fun getNotifications(id: Int) {

        Log.e("noti", "enter")

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getCharityNotifications(id)

        response.enqueue(object : Callback<NotificationJson> {
            override fun onResponse(call: Call<NotificationJson>, response: Response<NotificationJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    Log.e("data", data.toString())
                    rv_charity_notification.layoutManager = LinearLayoutManager(activity,
                        RecyclerView.VERTICAL,false)
                    rv_charity_notification.setHasFixedSize(true)
                    val notificationAdapter =
                        NotificationAdapter(activity, data)
                    rv_charity_notification.adapter = notificationAdapter

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