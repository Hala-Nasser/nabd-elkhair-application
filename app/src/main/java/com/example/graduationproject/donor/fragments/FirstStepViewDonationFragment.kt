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
import com.example.graduationproject.donor.adapters.typeSelected
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
    var charity_id = 0
    var donation_type_id = 0
    var campaign_id = 0
    var campaign_name = ""
    var campaign_image = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_first_step_view_donation, container, false)


        val radioGroup = root.radioGroup

        root.radio_electronic.visibility = View.GONE

        val b = arguments
        if (b != null) {
            charity_id = b.getInt("charity_id")
            donation_type_id = b.getInt("donation_type_id")
            campaign_id = b.getInt("campaign_id", 0)
            getPaymentLinks(charity_id)

            campaign_name = b.getString("campaign_name")!!
            campaign_image = b.getString("campaign_image")!!

            root.campaign_name.text = campaign_name
            Picasso.get().load(RetrofitInstance.IMAGE_URL + campaign_image)
                .into(root.campaign_image)
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
            val fragment = SecondStepViewDonationElectronicFragment()
            bundle.putString("previous_fragment", "firstStep")

            fragment.arguments = bundle
            if (selected_type != null && selected_type == "electronic") {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, fragment)
                    .addToBackStack(null).commit()
            } else if (selected_type != null && selected_type == "manual") {
                val fragment = SecondStepViewDonationManualFragment()
                bundle.putInt("charity_id", charity_id)
                bundle.putInt("donation_type_id", donation_type_id)
                bundle.putInt("campaign_id", campaign_id)
                bundle.putString("campaign_name", campaign_name)
                bundle.putString("campaign_image", campaign_image)

                fragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, fragment)
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
            override fun onResponse(
                call: Call<PaymentLinksJson>,
                response: Response<PaymentLinksJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    Log.e("data", data.toString())
                    if (data != null) {
                        radio_electronic.visibility = View.VISIBLE
                        if (data.paypal_link != null) {
                            bundle.putString("paypal", data.paypal_link)
                        }
                        if (data.visa_link != null) {
                            bundle.putString("visa", data.visa_link)
                        }
                        if (data.creditcard_link != null) {
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