package com.example.graduationproject.charity.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import kotlinx.android.synthetic.main.fragment_charity_info.view.*


class AboutCharityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_info, container, false)

        val sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val about = sharedPref.getString("about", "")!!

        root.profile_charity_about.text = about

        return root
    }


}