package com.example.graduationproject.donor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.category.Data
import com.squareup.picasso.Picasso

class DonationTypeAdapter (var activity: Context?, var data :List<Data>) : RecyclerView.Adapter<DonationTypeAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image  =itemView.findViewById<ImageView>(R.id.donation_type_image)
        val cardView  =itemView.findViewById<CardView>(R.id.donation_type_card_view)


        fun initialize(data: Data) {
            Picasso.get().load("http://192.168.203.17/storage/uploads/images/"+data.image).into(image)

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationTypeAdapter.MyViewHolder {
        var View: View = LayoutInflater.from(activity).inflate(R.layout.donation_type_item ,parent ,false)
        val myHolder:MyViewHolder = MyViewHolder(View)
        return myHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DonationTypeAdapter.MyViewHolder, position: Int) {
        // holder.photo.setImageResource(data[position].photo)
        holder.initialize(data[position])
    }
}