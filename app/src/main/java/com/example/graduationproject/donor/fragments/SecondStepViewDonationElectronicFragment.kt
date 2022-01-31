package com.example.graduationproject.donor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.Button
import com.example.graduationproject.R
import kotlinx.android.synthetic.main.fragment_first_step_view_donation.view.radioGroup
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import kotlinx.android.synthetic.main.bottom_dialog_item.view.*

import android.view.LayoutInflater
import kotlinx.android.synthetic.main.fragment_second_step_view_donation_electronic.view.*


class SecondStepViewDonationElectronicFragment : Fragment() {

    var selected_type : String? = null
    lateinit var dialog :Dialog
    lateinit var v :View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_second_step_view_donation_electronic, container, false)

        val radioGroup=root.radioGroup


        radioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.radio_paypal -> {
                    root.radio_paypal.setBackgroundResource(R.drawable.checked_radio_button)
                    root.radio_card.setBackgroundResource(R.drawable.unchecked_radio_button)
                    selected_type = "paypal"
                }
                R.id.radio_card -> {
                    root.radio_card.setBackgroundResource(R.drawable.checked_radio_button)
                    root.radio_paypal.setBackgroundResource(R.drawable.unchecked_radio_button)
                    selected_type = "card"
                }
            }
        }

        root.confirm_donation.setOnClickListener {
            showDialog()
            v.close.setOnClickListener {
                dialog.dismiss()
            }
            v.confirm_electronic.setOnClickListener {
                dialog.dismiss()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,ConfirmationFragment()).addToBackStack(null).commit()
            }

        }

        return root
    }

    private fun showDialog() {
        dialog = Dialog(this.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        v= layoutInflater.inflate(R.layout.bottom_dialog_item,null)
        dialog.setContentView(v)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.setCancelable(false)
        dialog.show()

    }

}