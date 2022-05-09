package com.example.graduationproject.donor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import kotlinx.android.synthetic.main.fragment_about_charity.view.*

class AboutCharityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_about_charity, container, false)

        val b = arguments
        if (b != null) {
            var description = b.getString("charity_description")
            //b.putString("charity_donation_type", charity_donation_type)
            var phone = b.getInt("charity_phone")
            //var facebook = b.getString("charity_facebook")

            root.charity_description.text = description
            root.charity_phone.text = phone.toString()
            //root.charity_facebook.text = facebook
        }

        return root
    }

}