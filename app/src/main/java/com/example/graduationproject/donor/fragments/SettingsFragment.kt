package com.example.graduationproject.donor.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.PrivacyPolicyActivity
import com.example.graduationproject.R
import com.example.graduationproject.donor.SignInActivity
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        root.edit_profile.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,EditProfileFragment()).addToBackStack(null).commit()
        }

        root.change_password.setOnClickListener {
            var bundle = Bundle()
            bundle.putBoolean("Donor", true)
            var fragment = ChangePasswordFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, fragment).addToBackStack(null).commit()
        }

        root.privacy_policy.setOnClickListener {
            val i = Intent(requireActivity(), PrivacyPolicyActivity()::class.java)
            startActivity(i)
        }

        root.sign_out.setOnClickListener {
            val i = Intent(requireActivity(), SignInActivity()::class.java)
            i.putExtra("Donor",true)
            startActivity(i)
            requireActivity().finish()
        }

        return root
    }

}