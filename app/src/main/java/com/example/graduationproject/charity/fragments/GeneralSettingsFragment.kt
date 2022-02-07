package com.example.graduationproject.charity.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.PrivacyPolicyActivity
import com.example.graduationproject.R
import com.example.graduationproject.donor.SignInActivity
import com.example.graduationproject.donor.fragments.ChangePasswordFragment
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.fragment_general_settings.view.*
import kotlinx.android.synthetic.main.fragment_settings.view.*


class GeneralSettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_general_settings, container, false)

        root.charity_edit_profile.setOnClickListener {
            var fragment = CharityEditProfileFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.charityContainer, fragment).addToBackStack(null).commit()
        }

        root.charity_change_password.setOnClickListener {
            var bundle = Bundle()
            bundle.putBoolean("Donor", false)
            var fragment = ChangePasswordFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.charityContainer, fragment).addToBackStack(null).commit()
        }

        root.charity_privacy_policy.setOnClickListener {
            var i = Intent(requireContext(),PrivacyPolicyActivity::class.java)
            startActivity(i)
        }


        root.general_settings_sign_out.setOnClickListener {
            val i = Intent(requireActivity(), SignInActivity()::class.java)
            i.putExtra("Donor",false)
            startActivity(i)
            requireActivity().finish()
        }

            return root
    }

}