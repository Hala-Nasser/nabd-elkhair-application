package com.example.graduationproject.donor.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.paymentLinks.PaymentLinksJson
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.campaign_item_in_donation_screen.view.*
import kotlinx.android.synthetic.main.fragment_first_step_view_donation.*
import kotlinx.android.synthetic.main.fragment_first_step_view_donation.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_second_step_view_donation_electronic.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirstStepViewDonationFragment : Fragment() {

    var selected_type: String? = null
    var bundle = Bundle()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_first_step_view_donation, container, false)


        val radioGroup = root.radioGroup

        root.radio_electronic.visibility = View.GONE

        val b = arguments
        if (b != null){
            var charity_id = b.getInt("charity_id")
            getPaymentLinks(charity_id)

            var campaign_name = b.getString("campaign_name")
            var campaign_image = b.getString("campaign_image")

            root.campaign_name.text = campaign_name
            Picasso.get().load(RetrofitInstance.IMAGE_URL+campaign_image).into(root.campaign_image)
        }


        radioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.radio_electronic -> {
                    root.radio_electronic.setBackgroundResource(R.drawable.checked_radio_button)
                    root.radio_manual.setBackgroundResource(R.drawable.unchecked_radio_button)
                    selected_type = "electronic"
                }
                R.id.radio_manual -> {
                    root.radio_manual.setBackgroundResource(R.drawable.checked_radio_button)
                    root.radio_electronic.setBackgroundResource(R.drawable.unchecked_radio_button)
                    selected_type = "manual"
                }
            }
        }

        root.next.setOnClickListener {
//            val b = arguments
            val fragment = SecondStepViewDonationElectronicFragment()
            //bundle.putInt("charity_id", charity_id)
            fragment.arguments=bundle
            if (selected_type != null && selected_type == "electronic") {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, fragment)
                    .addToBackStack(null).commit()
            } else if (selected_type != null && selected_type == "manual") {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, SecondStepViewDonationManualFragment())
                    .addToBackStack(null).commit()
            }
        }

        return root
    }

    fun getPaymentLinks(id: Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getPaymentLinks(id)

        response.enqueue(object : Callback<PaymentLinksJson> {
            override fun onResponse(call: Call<PaymentLinksJson>, response: Response<PaymentLinksJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    Log.e("data", data.toString())
                    if (data != null){
                        radio_electronic.visibility = View.VISIBLE
                        if (data.paypal_link != null){
                            bundle.putString("paypal", data.paypal_link)
                        }
                        if (data.visa_link != null){
                            bundle.putString("visa", data.visa_link)
                        }
                        if (data.creditcard_link != null){
                            bundle.putString("card", data.creditcard_link)
                        }
                    }

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<PaymentLinksJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

}