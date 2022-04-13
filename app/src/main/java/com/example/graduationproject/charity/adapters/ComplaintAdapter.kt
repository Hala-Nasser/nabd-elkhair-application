package com.example.graduationproject.charity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R

import com.example.graduationproject.charity.models.Complaint
import kotlinx.android.synthetic.main.payment_item.view.*


class ComplaintAdapter(
    var activity: Context?, var data: List<Complaint>,
) : RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>(){

    class ComplaintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image  =itemView.payment_link_img

        fun initialize(data: Complaint) {
            image.setImageResource(R.drawable.visa)
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComplaintViewHolder{
            var view: View = LayoutInflater.from(activity).inflate(R.layout.complaint_item, parent, false)
            return ComplaintViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {

            holder.initialize(data[position])

    }


}