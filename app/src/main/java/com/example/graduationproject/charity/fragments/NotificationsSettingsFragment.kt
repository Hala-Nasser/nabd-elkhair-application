package com.example.graduationproject.charity.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.PrivacyPolicyActivity
import com.example.graduationproject.R
import com.example.graduationproject.api.charityApi.fcm.FCMJson
import com.example.graduationproject.api.charityApi.logout.LogoutJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.donor.SignInActivity
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.fragment_charity_profile.*
import kotlinx.android.synthetic.main.fragment_general_settings.view.*
import kotlinx.android.synthetic.main.fragment_general_settings.view.general_settings_sign_out
import kotlinx.android.synthetic.main.fragment_notifications_settings.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationsSettingsFragment : Fragment() {
    var progressDialog: ProgressDialog? = null
    var token = ""
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_notifications_settings, container, false)

        sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("charity_token", "")!!

        root.notification_settings_privacy_policy.setOnClickListener {
            var i = Intent(requireContext(), PrivacyPolicyActivity::class.java)
            startActivity(i)
        }

        root.notification_settings_sign_out.setOnClickListener {
            progressDialog = ProgressDialog(activity)
            GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
            logout()
        }

        return root
    }



    fun logout() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityLogout("Bearer $token")

        response.enqueue(object : Callback<LogoutJson> {
            override fun onResponse(call: Call<LogoutJson>, response: Response<LogoutJson>) {
                if (response.isSuccessful) {
                    Log.e("Logout",response.message())
                    val i = Intent(requireActivity(), SignInActivity()::class.java)
                    i.putExtra("Donor",false)
                    startActivity(i)
                    requireActivity().finish()
                    GeneralChanges().hideDialog(progressDialog!!)
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<LogoutJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }

}