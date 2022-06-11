package com.example.graduationproject.donor.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.mydonations.Data
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.donation_item.view.*

class MyDonationAdapter(
    var activity: Context?, var data: List<Data>,
    var clickListener: onDonationItemClickListener
) : RecyclerView.Adapter<MyDonationAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image = itemView.image
        val name = itemView.name
        val date = itemView.donation_date


        fun initialize(data: Data, action: onDonationItemClickListener) {

            if (data.campaign_details != null) {
                Picasso.get().load(RetrofitInstance.IMAGE_URL + data.campaign_details.image)
                    .into(image)
                name.text = data.campaign_details.name
            } else {
                Picasso.get().load(RetrofitInstance.IMAGE_URL + data.charity_details.image)
                    .into(image)
                name.text = data.charity_details.name
            }
            date.text = data.date_time

            itemView.setOnClickListener {
                action.onItemClick(data, adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyDonationAdapter.MyViewHolder {
        var view: View =
            LayoutInflater.from(activity).inflate(R.layout.donation_item, parent, false)
        val myHolder: MyViewHolder = MyViewHolder(view)
        return myHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyDonationAdapter.MyViewHolder, position: Int) {
        Log.e("data in adapter", data.toString())
        holder.initialize(data[position], clickListener)


    }

    interface onDonationItemClickListener {
        fun onItemClick(data: Data, position: Int)
    }

}