package com.example.graduationproject.donor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.graduationproject.R
import kotlinx.android.synthetic.main.fragment_first_step_view_donation.view.radioGroup
import android.util.Log
import android.view.*
import kotlinx.android.synthetic.main.bottom_dialog_item.view.*

import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.WebViewFragment
import com.example.graduationproject.api.donorApi.paymentLinks.PaymentLinksJson
import com.example.graduationproject.donor.adapters.NotificationAdapter
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_dialog_item.view.close
import kotlinx.android.synthetic.main.bottom_donation_with_campaign.view.*
import kotlinx.android.synthetic.main.bottom_sheet_manually.view.*
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_second_step_view_donation_electronic.*
import kotlinx.android.synthetic.main.fragment_second_step_view_donation_electronic.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SecondStepViewDonationElectronicFragment : Fragment() {

    var selected_type: String? = null
    lateinit var dialog: BottomSheetDialog
    lateinit var v: View
    var paypal: String? = ""
    var visa: String? = ""
    var card: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(
            R.layout.fragment_second_step_view_donation_electronic,
            container,
            false
        )

        root.radio_visa.visibility = View.GONE
        root.radio_card.visibility = View.GONE
        root.radio_paypal.visibility = View.GONE

        root.donation.visibility = View.GONE

        val b = arguments
        if (b != null) {
            paypal = b.getString("paypal")
            visa = b.getString("visa")
            card = b.getString("card")

            root.donation.visibility = View.VISIBLE

            if (paypal != null && paypal != "") {
                root.radio_paypal.visibility = View.VISIBLE
            }
            if (visa != null && visa != "") {
                root.radio_visa.visibility = View.VISIBLE
            }
            if (card != null && card != "") {
                root.radio_card.visibility = View.VISIBLE
            }
        }

        val radioGroup = root.radioGroup


        radioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.radio_paypal -> {
                    root.radio_paypal.setBackgroundResource(R.drawable.checked_radio_button)
                    root.radio_card.setBackgroundResource(R.drawable.unchecked_radio_button)
                    root.radio_visa.setBackgroundResource(R.drawable.unchecked_radio_button)
                    selected_type = "paypal"
                }
                R.id.radio_card -> {
                    root.radio_card.setBackgroundResource(R.drawable.checked_radio_button)
                    root.radio_paypal.setBackgroundResource(R.drawable.unchecked_radio_button)
                    root.radio_visa.setBackgroundResource(R.drawable.unchecked_radio_button)
                    selected_type = "card"
                }
                R.id.radio_visa -> {
                    root.radio_visa.setBackgroundResource(R.drawable.checked_radio_button)
                    root.radio_card.setBackgroundResource(R.drawable.unchecked_radio_button)
                    root.radio_paypal.setBackgroundResource(R.drawable.unchecked_radio_button)
                    selected_type = "visa"
                }
            }
        }

        root.donation.setOnClickListener {

            val bundle = Bundle()
            val fragment = WebViewFragment()
            when (selected_type) {
                "card" -> {
                    bundle.putString("link", card)
                }
                "visa" -> {
                    bundle.putString("link", visa)
                }
                "paypal" -> {
                    bundle.putString("link", paypal)
                }
            }
            fragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, fragment).addToBackStack(null).commit()

        }

        return root
    }

}