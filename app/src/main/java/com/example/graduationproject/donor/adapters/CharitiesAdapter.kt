package com.example.graduationproject.donor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.donor.models.Charity
import kotlinx.android.synthetic.main.charity_item.view.*

class CharitiesAdapter(var activity: Context?, var data :List<Charity>) : RecyclerView.Adapter<CharitiesAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image  =itemView.charity_image
        val name  =itemView.charity_name
        val location  =itemView.charity_location

        fun initialize(data: Charity) {
            image.setImageResource(data.charityImg!!)
            name.text = data.charityName
            location.text = data.charityLocation
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharitiesAdapter.MyViewHolder {
        var View: View = LayoutInflater.from(activity).inflate(R.layout.charity_item ,parent ,false)
        val myHolder: CharitiesAdapter.MyViewHolder = CharitiesAdapter.MyViewHolder(View)
        return myHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CharitiesAdapter.MyViewHolder, position: Int) {
        holder.initialize(data[position])
    }


}