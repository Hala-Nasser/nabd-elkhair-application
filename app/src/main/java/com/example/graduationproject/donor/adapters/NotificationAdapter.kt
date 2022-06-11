package com.example.graduationproject.donor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.models.Notification
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.donor_notification_item.view.*

class NotificationAdapter(var activity: Context?, var data: List<Notification>) :
    RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.image
        val title = itemView.title
        val content = itemView.content

        fun initialize(data: Notification?) {
            Picasso.get().load(RetrofitInstance.IMAGE_URL + data!!.notification_image).into(image)
            title.text = data.notification_title
            content.text = data.notification_content
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.MyViewHolder {
        var View: View =
            LayoutInflater.from(activity).inflate(R.layout.donor_notification_item, parent, false)
        val myHolder: NotificationAdapter.MyViewHolder = NotificationAdapter.MyViewHolder(View)
        return myHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: NotificationAdapter.MyViewHolder, position: Int) {
        holder.initialize(data[position])

    }


}