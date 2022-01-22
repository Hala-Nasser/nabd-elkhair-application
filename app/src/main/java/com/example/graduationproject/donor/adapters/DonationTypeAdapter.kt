package com.example.graduationproject.donor.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.category.Data
import com.example.graduationproject.donor.models.DonationType
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class DonationTypeAdapter (var activity: Context?, var data :List<DonationType>, var from: String) : RecyclerView.Adapter<DonationTypeAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image  =itemView.findViewById<ImageView>(R.id.donation_type_image)
        val cardView  =itemView.findViewById<MaterialCardView>(R.id.donation_type_card_view)
        var clicked = true

        fun initialize(data: DonationType) {
            //Picasso.get().load("http://192.168.203.17/storage/uploads/images/"+data.image).into(image)
            image.setImageResource(data.photo!!)
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
        when(from){
            "DonorHome" -> {
                changeCardStyle(holder,R.color.black,R.color.app_color,R.color.app_color,0f,R.color.app_color)
                holder.cardView.apply {
                    strokeColor = resources.getColor(R.color.app_color)
                }
            }
            "CharityHome" -> {
                changeCardStyle(holder,R.color.black,R.color.app_color,R.color.app_color,0f,R.color.app_color)
                holder.cardView.apply {
                    strokeColor = resources.getColor(R.color.app_color)
                }
            }
            "CompleteSignup" -> {
                changeCardStyle(holder,R.color.app_color,R.color.app_color,R.color.white,10f,R.color.white)

            }
        }
    }

    private fun changeCardStyle(holder: MyViewHolder,
                                tintColor: Int,
                                clickedBorderColor:Int,
                                unClickedBorderColor:Int,
                                clickedCardEvaluation:Float,
                                clickedCardBackground:Int,
    ){
        DrawableCompat.setTint(
            DrawableCompat.wrap(holder.image.drawable),
            ContextCompat.getColor(activity!!, tintColor)
        )
        holder.cardView.setOnClickListener {
            if (holder.clicked) {
                holder.clicked = false
                holder.cardView.apply {
                    strokeColor = resources.getColor(clickedBorderColor)
                    cardElevation = clickedCardEvaluation
                    setCardBackgroundColor(resources.getColor(clickedCardBackground))
                }
            } else {
                holder.clicked = true
                holder.cardView.apply {
                    strokeColor = resources.getColor(unClickedBorderColor)
                    cardElevation = 0f
                    setCardBackgroundColor(resources.getColor(R.color.white))
                }
            }
        }
    }
}