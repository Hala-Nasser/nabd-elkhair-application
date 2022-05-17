package com.example.graduationproject.donor.fragments

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.DonationType
import com.example.graduationproject.api.donorApi.donationType.DonationTypeJson
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.donor.adapters.lastCheckedPos
import com.example.graduationproject.donor.adapters.typeSelected
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.dialog_item.view.*
import kotlinx.android.synthetic.main.fragment_about_charity.*
import kotlinx.android.synthetic.main.fragment_about_charity.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutCharityFragment : Fragment() {

    lateinit var dialog : Dialog
    lateinit var v :View
    var campaign_donation_type= mutableListOf<DonationType>()
    var charity_id =  0
    var charity_name :String? =  ""
    var charity_image :String? =  ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_about_charity, container, false)

        val b = arguments
        if (b != null) {
            var description = b.getString("charity_description")
           // var donation_type_id = b.getStringArrayList("charity_donation_type")
            var phone = b.getInt("charity_phone")
            charity_id = b.getInt("charity_id", 0)
            charity_name = b.getString("charity_name")
            charity_image = b.getString("charity_image")
            //var facebook = b.getString("charity_facebook")

            getDonationType(charity_id)

            root.charity_description.text = description
            root.charity_phone.text = phone.toString()
            //root.charity_facebook.text = facebook

            root.charity_donate.setOnClickListener {
                getDialog()

                v.close.setOnClickListener {
                    lastCheckedPos = -1
                    typeSelected = null
                    dialog.dismiss()
                }
                v.choose.setOnClickListener {
                    Log.e("item selected position", lastCheckedPos.toString())
                    if (typeSelected != null){
                        if (typeSelected!!.name == "مال"){
                            val fragment = FirstStepViewDonationFragment()
                            val b=Bundle()
                            b.putInt("charity_id", charity_id)
                            b.putInt("donation_type_id", typeSelected!!.id)
                            b.putString("campaign_name", charity_name)
                            b.putString("campaign_image", charity_image)
                            fragment.arguments=b
                            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,fragment).addToBackStack(null).commit()
                        }else{

                            val fragment = SecondStepViewDonationManualFragment()
                            val b=Bundle()
                            //b.putInt("charity_id", campaign_charity.id)
                            b.putInt("charity_id", charity_id)
                            b.putInt("donation_type_id", typeSelected!!.id)
                            b.putString("campaign_name", charity_name)
                            b.putString("campaign_image", charity_image)
                            b.putString("previous_fragment","charityDetails")
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
        }

        return root
    }

    fun getDonationType(id:Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getCharityDonationTypes(id)

        response.enqueue(object : Callback<DonationTypeJson> {
            override fun onResponse(call: Call<DonationTypeJson>, response: Response<DonationTypeJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    campaign_donation_type = data as MutableList<DonationType>
                    rv_charity_donation_type.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
                    rv_charity_donation_type.setHasFixedSize(true)
                    val donationTypeAdapter =
                        DonationTypeAdapter(activity, data, "about charity")
                    rv_charity_donation_type.adapter = donationTypeAdapter
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<DonationTypeJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
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