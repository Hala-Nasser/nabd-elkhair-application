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
import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.DonationType
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.donor.adapters.lastCheckedPos
import com.example.graduationproject.donor.adapters.typeSelected
import com.example.graduationproject.models.Charity
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.charity_item.view.*
import kotlinx.android.synthetic.main.dialog_item.view.*
import kotlinx.android.synthetic.main.donation_type_item.view.*
import kotlinx.android.synthetic.main.fragment_campaign_details.view.*
import java.util.ArrayList

class CampaignDetailsFragment : Fragment() {

    var campaign_id = 0
    var campaign_name = ""
    var campaign_description = ""
    var campaign_image = ""
    var campaign_date = ""
    lateinit var campaign_charity: Charity
    lateinit var dialog: Dialog
    lateinit var v: View

    var campaign_donation_type = mutableListOf<DonationType>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_campaign_details, container, false)

        val b = arguments
        if (b != null) {
            campaign_id = b.getInt("campaign_id")
            campaign_name = b.getString("campaign_name")!!
            campaign_description = b.getString("campaign_description")!!
            campaign_image = b.getString("campaign_image")!!
            campaign_date = b.getString("campaign_date")!!
            campaign_charity = b.getParcelable("campaign_charity")!!
            campaign_donation_type = mutableListOf()
            campaign_donation_type = b.getParcelableArrayList("campaign_donation_type")!!
            Log.e("DT get from bundle", campaign_donation_type.toString())


            root.campaign_name.text = campaign_name
            root.campaign_description.text = campaign_description
            Picasso.get().load(RetrofitInstance.IMAGE_URL + campaign_image)
                .into(root.campaign_image)
            root.campaign_date.text = campaign_date
            Picasso.get().load(RetrofitInstance.IMAGE_URL + campaign_charity.image)
                .into(root.campaign_charity.charity_image)
            root.campaign_charity.charity_name.text = campaign_charity.name
            root.campaign_charity.charity_location.text = campaign_charity.address

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
                if (typeSelected != null) {
                    if (typeSelected!!.name == "مال") {
                        val fragment = FirstStepViewDonationFragment()
                        val b = Bundle()
                        b.putInt("charity_id", campaign_charity.id)
                        b.putInt("donation_type_id", typeSelected!!.id)
                        b.putInt("campaign_id", campaign_id)
                        b.putString("campaign_name", campaign_name)
                        b.putString("campaign_image", campaign_image)
                        fragment.arguments = b
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, fragment).addToBackStack(null).commit()
                    } else {

                        val fragment = SecondStepViewDonationManualFragment()
                        val b = Bundle()
                        b.putInt("charity_id", campaign_charity.id)
                        b.putInt("donation_type_id", typeSelected!!.id)
                        b.putInt("campaign_id", campaign_id)
                        b.putString("campaign_image", campaign_image)
                        b.putString("campaign_name", campaign_name)
                        b.putString("previous_fragment", "campaignDetails")
                        fragment.arguments = b
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, fragment).addToBackStack(null).commit()

                    }
                    lastCheckedPos = -1
                    typeSelected = null
                    dialog.dismiss()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "قم باختيار نوع التبرع أولاً",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

            v.rv_donation_type.layoutManager =
                LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            v.rv_donation_type.setHasFixedSize(true)
            val donationAdapter =
                DonationTypeAdapter(activity, campaign_donation_type, "CampaignDetailsFragment")
            v.rv_donation_type.adapter = donationAdapter

        }

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return root
    }

    fun getDialog() {
        v = layoutInflater.inflate(R.layout.dialog_item, null)
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