package com.example.graduationproject.donor.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.DonationType
import com.example.graduationproject.charity.fragments.CharityEditProfileFragment.Companion.donationTypes
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import kotlin.math.log

var lastCheckedPos = -1
var typeSelected: DonationType? = null

class DonationTypeAdapter(var activity: Context?, var data: List<DonationType>?, var from: String) :
    RecyclerView.Adapter<DonationTypeAdapter.MyViewHolder>() {

    var list: ArrayList<Int> = ArrayList()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.donation_type_image)
        val title = itemView.findViewById<TextView>(R.id.donation_type_title)
        val cardView = itemView.findViewById<MaterialCardView>(R.id.donation_type_card_view)
        var clicked = true

        fun initialize(data: DonationType) {
            Picasso.get().load(RetrofitInstance.IMAGE_URL + data.image).into(image)
            title.text = data.name

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DonationTypeAdapter.MyViewHolder {
        var view: View =
            LayoutInflater.from(activity).inflate(R.layout.donation_type_item, parent, false)
        val myHolder: MyViewHolder = MyViewHolder(view)
        return myHolder
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(holder: DonationTypeAdapter.MyViewHolder, position: Int) {
        holder.initialize(data!![position])
        when (from) {
            "EditProfile" -> {
                Log.e("types", donationTypes.toString())
                for (element in donationTypes) {
                    if (element == data!![position].id) {
                        holder.clicked = false
                        holder.cardView.apply {
                            strokeColor = resources.getColor(R.color.app_color)
                            cardElevation = 3f
                            setCardBackgroundColor(resources.getColor(R.color.white))
                        }
                        list.add(element)
                    }
                }
                changeCardStyle(
                    holder,
                    R.color.app_color,
                    R.color.white,
                    3f,
                    R.color.white,
                    position
                )

            }
            "CompleteSignup" -> {

                changeCardStyle(
                    holder,
                    R.color.app_color,
                    R.color.white,
                    3f,
                    R.color.white,
                    position
                )

            }
            "CampaignDetailsFragment" -> {
                holder.itemView.setOnClickListener {
                    lastCheckedPos = position
                    typeSelected = data!![position]
                    this.notifyDataSetChanged()
                }

                if (position == lastCheckedPos) {
                    Log.e("onBind", "equal")
                    holder.cardView.apply {
                        strokeColor = resources.getColor(R.color.app_color)
                    }
                } else {
                    holder.cardView.apply {
                        strokeColor = resources.getColor(R.color.white)
                    }
                }
            }
        }

    }


    private fun changeCardStyle(
        holder: MyViewHolder,
        clickedBorderColor: Int,
        unClickedBorderColor: Int,
        clickedCardEvaluation: Float,
        clickedCardBackground: Int,
        position: Int
    ) {
        holder.cardView.setOnClickListener {
            if (holder.clicked) {
                holder.clicked = false
                holder.cardView.apply {
                    strokeColor = resources.getColor(clickedBorderColor)
                    cardElevation = clickedCardEvaluation
                    setCardBackgroundColor(resources.getColor(clickedCardBackground))
                }
                list.add(data!![position].id)
            } else {
                holder.clicked = true
                holder.cardView.apply {
                    strokeColor = resources.getColor(unClickedBorderColor)
                    cardElevation = 3f
                    setCardBackgroundColor(resources.getColor(R.color.white))
                }
                list.remove(data!![position].id)
            }
            Log.e("donationTypes", list.toString())

        }
    }

}
