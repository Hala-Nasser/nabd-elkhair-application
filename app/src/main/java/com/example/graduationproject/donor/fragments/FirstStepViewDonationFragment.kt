package com.example.graduationproject.donor.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.graduationproject.R
import kotlinx.android.synthetic.main.fragment_first_step_view_donation.view.*


class FirstStepViewDonationFragment : Fragment() {

    var selected_type : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_first_step_view_donation, container, false)

        val radioGroup=root.radioGroup


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
            if (selected_type != null && selected_type == "electronic"){
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,SecondStepViewDonationFragment()).addToBackStack(null).commit()
            }
        }

        return root
    }

}