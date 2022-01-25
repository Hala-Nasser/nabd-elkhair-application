package com.example.graduationproject.donor.fragments

import android.app.ActionBar
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.donor.models.Charity
import com.example.graduationproject.donor.models.DonationType
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*
import kotlinx.android.synthetic.main.dialog_item.view.*
import kotlinx.android.synthetic.main.donation_type_item.view.*
import kotlinx.android.synthetic.main.fragment_campaign_details.view.*

class CampaignDetailsFragment : Fragment() {

    var campaign_name = ""
    var campaign_image = 0
    var campaign_date = ""
    lateinit var campaign_charity: Charity
    lateinit var dialog :Dialog
    lateinit var v :View
    //var donation_type_clicked = false

    private lateinit var  donationTypeList: MutableList<DonationType>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_campaign_details, container, false)

        val b = arguments
        if (b != null) {
            campaign_name = b.getString("campaign_name")!!
            campaign_image = b.getInt("campaign_image")
            campaign_date = b.getString("campaign_date")!!
            campaign_charity = b.getParcelable("campaign_charity")!!

        }

        donationTypeList = mutableListOf()
        donationTypeList.add(DonationType(R.drawable.food))
        donationTypeList.add(DonationType(R.drawable.money))
        donationTypeList.add(DonationType(R.drawable.clothes))

        root.donate.setOnClickListener {
            getDialog()

            v.close.setOnClickListener {
                dialog.dismiss()
            }
            v.choose.setOnClickListener {
                dialog.dismiss()
            }

            v.rv_complete_signup_donation_type.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false)
            v.rv_complete_signup_donation_type.setHasFixedSize(true)
            val storyAdapter =
                DonationTypeAdapter(activity, donationTypeList,"CampaignDetailsFragment")
            v.rv_complete_signup_donation_type.adapter = storyAdapter

        }



        return root
    }

    fun getDialog(){
        v= layoutInflater.inflate(R.layout.dialog_item,null)
        dialog = Dialog(this.requireContext())
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(v)
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = ActionBar.LayoutParams.WRAP_CONTENT
        dialog.window!!.setLayout(width, height)
        dialog.setCancelable(false)
        dialog.show()
    }

}