package com.example.graduationproject.charity.fragments

import android.app.AlertDialog
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
import android.widget.Toast
import com.example.graduationproject.AboutActivity
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.forgotPassword.ForgotPasswordJson
import com.example.graduationproject.api.donorApi.logout.LogoutJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.donor.SignInActivity
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.fragment_notifications_settings.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationsSettingsFragment : Fragment() {
    var progressDialog: ProgressDialog? = null
    var token = ""
    lateinit var sharedPref: SharedPreferences
    var notificationStatus = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_notifications_settings, container, false)

        sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("charity_token", "")!!

        root.notification_settings_privacy_policy.setOnClickListener {
            var i = Intent(requireContext(), AboutActivity::class.java)
            startActivity(i)
        }

        root.charity_notification_switch.isChecked =
            sharedPref.getInt("charity_notification", 0) != 0


        root.charity_notification_switch.setOnCheckedChangeListener { compoundButton, b ->
            if (root.charity_notification_switch.isChecked) {
                notificationStatus = 1
                Log.e("check", root.charity_notification_switch.isChecked.toString())

            } else {
                notificationStatus = 0
                Log.e("check", root.charity_notification_switch.isChecked.toString())

            }
            enableNotification()
        }

        root.notification_settings_sign_out.setOnClickListener {
            getAlertDialog()
        }

        return root
    }

    fun getAlertDialog() {
        var alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("تسجيل الخروج")
        alertDialog.setMessage("هل تريد تسجيل الخروج فعلاً!!")
        alertDialog.setCancelable(false)
        alertDialog.setIcon(R.drawable.ic_baseline_login_24)

        alertDialog.setPositiveButton("تسجيل الخروج") { dialogInterface, i ->
            progressDialog = ProgressDialog(activity)
            GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
            logout()
        }

        alertDialog.setNegativeButton("لا,شكراً") { dialogInterface, i ->
            Log.e("ok", "ok")
        }
        var alertDialogCreate = alertDialog.create()
        alertDialogCreate.window!!.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        alertDialogCreate.show()
    }

    fun enableNotification() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.setNotificationStatus("Bearer $token", notificationStatus)

        response.enqueue(object : Callback<ForgotPasswordJson> {
            override fun onResponse(
                call: Call<ForgotPasswordJson>,
                response: Response<ForgotPasswordJson>
            ) {
                val data = response.body()
                if (response.isSuccessful) {
                    Log.e("enableNotification", data!!.message)
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<ForgotPasswordJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

    fun logout() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityLogout("Bearer $token")

        response.enqueue(object : Callback<LogoutJson> {
            override fun onResponse(call: Call<LogoutJson>, response: Response<LogoutJson>) {
                val data = response.body()
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), data!!.message, Toast.LENGTH_SHORT).show()
                    val i = Intent(requireActivity(), SignInActivity()::class.java)
                    i.putExtra("Donor", false)
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