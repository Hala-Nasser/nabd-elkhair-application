package com.example.graduationproject.charity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.charity.models.Donation
import com.example.graduationproject.charity.models.Notification
import kotlinx.android.synthetic.main.charity_notification_item.view.*

class NotificationAdapter(var activity: Context?, var data :List<Donation>?=null) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>(){

    var campaignName:String?=null
    var campaignImg:Int?=null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image  =itemView.notification_image
        val title  =itemView.notification_title
        val time  =itemView.notification_time
        val card  =itemView.notification_card_view

        fun initialize(data: Notification?) {
                     image.setImageResource(data!!.notificationImg!!)
                     title.text = data.notificationTitle
                     time.text = data.notificationTime
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.MyViewHolder {
        var View: View = LayoutInflater.from(activity).inflate(R.layout.charity_notification_item ,parent ,false)
        val myHolder: NotificationAdapter.MyViewHolder = NotificationAdapter.MyViewHolder(View)
        return myHolder
    }

    override fun getItemCount(): Int {
        return data!!.size
    }


    override fun onBindViewHolder(holder: NotificationAdapter.MyViewHolder, position: Int) {
        //holder.initialize(data!![position].notification)

    }


}