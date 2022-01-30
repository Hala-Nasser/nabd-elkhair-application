package com.example.graduationproject.donor.fragments

import android.app.ActionBar
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.donor.adapters.lastCheckedPos
import com.example.graduationproject.donor.adapters.typeSelected
import com.example.graduationproject.donor.models.Charity
import com.example.graduationproject.donor.models.DonationType
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.charity_item.view.*
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

    var  campaign_donation_type= mutableListOf<DonationType>()



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
            val donation_type : DonationType = b.getParcelable("campaign_donation_type")!!
            campaign_donation_type.add(donation_type)
            if (donation_type.photo != R.drawable.money){
                campaign_donation_type.add(DonationType(R.drawable.money))
            }

            root.campaign_name.text = campaign_name
            root.campaign_image.setImageResource(campaign_image)
            root.campaign_date.text = campaign_date
            root.campaign_charity.charity_image.setImageResource(campaign_charity.charityImg!!)
            root.campaign_charity.charity_name.text = campaign_charity.charityName
            root.campaign_charity.charity_location.text = campaign_charity.charityLocation

        }

        root.donate.setOnClickListener {
            getDialog()

            v.close.setOnClickListener {
                lastCheckedPos = -1
                typeSelected = null
                dialog.dismiss()
            }
            v.choose.setOnClickListener {
                Log.e("item selected position", lastCheckedPos.toString())
                if (typeSelected != null){
                    if (typeSelected!!.photo == R.drawable.money){
                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,FirstStepViewDonationFragment()).addToBackStack(null).commit()
                    }else if (typeSelected!!.photo == R.drawable.clothes || typeSelected!!.photo == R.drawable.food){

                        val fragment = SecondStepViewDonationManualFragment()
                        val b=Bundle()
                        b.putString("previous_fragment","campaignDetails")
                        fragment.arguments=b
                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,fragment).addToBackStack(null).commit()

                    }
                    lastCheckedPos = -1
                    typeSelected = null
                    dialog.dismiss()
                }else{
                    Toast.makeText(requireActivity(),"قم باختيار نوع التبرع أولاً", Toast.LENGTH_LONG).show()
                }

            }



            v.rv_donation_type.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false)
            v.rv_donation_type.setHasFixedSize(true)
            val donationAdapter =
                DonationTypeAdapter(activity, campaign_donation_type,"CampaignDetailsFragment")
            v.rv_donation_type.adapter = donationAdapter

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