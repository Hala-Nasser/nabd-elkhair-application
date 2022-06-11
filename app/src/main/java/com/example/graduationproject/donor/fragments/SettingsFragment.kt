package com.example.graduationproject.donor.fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.AboutActivity
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.logout.LogoutJson
import com.example.graduationproject.api.donorApi.profile.ProfileJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.donor.SignInActivity
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsFragment : Fragment() {

    var token = ""
    var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        val sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val user_id = sharedPref.getInt("user_id", 0)
        token = sharedPref.getString("user_token", "")!!

        profile(user_id)

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        root.edit_profile.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, EditProfileFragment()).addToBackStack(null).commit()
        }

        root.change_password.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, ChangePasswordFragment()).addToBackStack(null).commit()
        }

        root.notification_switch.setOnCheckedChangeListener { compoundButton, b ->
            if (root.notification_switch.isChecked) {
                enableNotification(user_id)
                Log.e("noti", "enable")
                Log.e("check", root.notification_switch.isChecked.toString())

            } else {
                disableNotification(user_id)
                Log.e("noti", "disable")
                Log.e("check", root.notification_switch.isChecked.toString())

            }
        }

        root.privacy_policy.setOnClickListener {
            val i = Intent(requireActivity(), AboutActivity()::class.java)
            startActivity(i)
        }

        root.logout.setOnClickListener {
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

    fun profile(id: Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.profile(id)

        response.enqueue(object : Callback<ProfileJson> {
            override fun onResponse(call: Call<ProfileJson>, response: Response<ProfileJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data
                    notification_switch.isChecked = data.notification_status != 0

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<ProfileJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

    fun enableNotification(id: Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.enableNotification(id)

        response.enqueue(object : Callback<ProfileJson> {
            override fun onResponse(call: Call<ProfileJson>, response: Response<ProfileJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<ProfileJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

    fun disableNotification(id: Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.disableNotification(id)

        response.enqueue(object : Callback<ProfileJson> {
            override fun onResponse(call: Call<ProfileJson>, response: Response<ProfileJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<ProfileJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }


    fun logout() {

        val retrofitInstance =
            RetrofitInstance.create()
        Log.e("token", token)
        val response = retrofitInstance.logout("Bearer $token")

        response.enqueue(object : Callback<LogoutJson> {
            override fun onResponse(
                call: Call<LogoutJson>,
                response: Response<LogoutJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.status) {
                        val i = Intent(requireActivity(), SignInActivity()::class.java)
                        startActivity(i)
                        requireActivity().finish()
                        GeneralChanges().hideDialog(progressDialog!!)
                    } else {
                        Validation().showSnackBar(parent_layout, data.message)
                        GeneralChanges().hideDialog(progressDialog!!)
                    }
                } else {
                    Log.e("errorBody", response.message())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<LogoutJson>, t: Throwable) {
                Log.e("on failure change pass", t.toString())
                GeneralChanges().hideDialog(progressDialog!!)

            }
        })

    }
}