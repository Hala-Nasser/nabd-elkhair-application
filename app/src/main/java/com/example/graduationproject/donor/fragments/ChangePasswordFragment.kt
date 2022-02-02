package com.example.graduationproject.donor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import kotlinx.android.synthetic.main.fragment_change_password.view.*

class ChangePasswordFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_change_password, container, false)

        root.save.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,SettingsFragment()).commit()
        }

        root.back.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,SettingsFragment()).commit()
        }

        return root
    }


}