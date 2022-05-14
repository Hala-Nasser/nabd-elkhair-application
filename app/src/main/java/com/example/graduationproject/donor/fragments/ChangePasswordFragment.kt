package com.example.graduationproject.donor.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.changePassword.ChangePasswordJson
import com.example.graduationproject.charity.fragments.CharitySettingsFragment
import com.example.graduationproject.charity.fragments.GeneralSettingsFragment
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.donor.SignInActivity
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_change_password.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordFragment : Fragment() {

    var isAllFieldsChecked = false
    var progressDialog: ProgressDialog? = null
    var token = ""
    var cToken = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_change_password, container, false)

        val sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        var isDonor = sharedPref.getBoolean("isDonor", false)
        token = sharedPref.getString("user_token", "")!!
        cToken = sharedPref.getString("charity_token", "")!!


            root.save.setOnClickListener {
                isAllFieldsChecked = CheckAllFields()

                if (isAllFieldsChecked) {
                    progressDialog = ProgressDialog(activity)
                    GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
                    val old_password = old_password.text.toString()
                    val password = password.text.toString()
                    val confirm_password = confirm_password.text.toString()
                    /*if (old_password == password){
                        GeneralChanges().hideDialog(progressDialog!!)
                        Validation().showSnackBar(root.parent_layout, "يجب ان تكون كلمة المرور الجديدة مختلفة عن القديمة")
                    }else{*/
                    if (isDonor) {
                        changePassword(old_password, password, confirm_password)
                    }else{
                        charityChangePassword(old_password, password, confirm_password)
                    }
                    //}
                }
            }


        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }


        return root
    }

    fun changePassword(new_password: String, password: String, confirm_password: String) {

        val retrofitInstance =
            RetrofitInstance.create()
        Log.e("token", token)
        val response = retrofitInstance.changePassword("Bearer $token", new_password, password, confirm_password)

        response.enqueue(object : Callback<ChangePasswordJson> {
            override fun onResponse(
                call: Call<ChangePasswordJson>,
                response: Response<ChangePasswordJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.status){
                        activity!!.onBackPressed()
                        GeneralChanges().hideDialog(progressDialog!!)
                    }
                    else {
                        GeneralChanges().hideDialog(progressDialog!!)
                        Validation().showSnackBar(parent_layout, data.message)
                    }
                } else {
                    Log.e("errorBody", response.message())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<ChangePasswordJson>, t: Throwable) {
                Log.e("on failure change pass", t.toString())
                GeneralChanges().hideDialog(progressDialog!!)

            }
        })

    }

    fun charityChangePassword(password: String, new_password: String, confirm_password: String) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityChangePassword("Bearer $cToken", password, new_password, confirm_password)

        response.enqueue(object : Callback<ChangePasswordJson> {
            override fun onResponse(
                call: Call<ChangePasswordJson>,
                response: Response<ChangePasswordJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.status){
                        activity!!.onBackPressed()
                        GeneralChanges().hideDialog(progressDialog!!)
                    }
                    else {
                        GeneralChanges().hideDialog(progressDialog!!)
                        Validation().showSnackBar(parent_layout, data.message)
                    }
                } else {
                    Log.e("errorBody", response.message())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<ChangePasswordJson>, t: Throwable) {
                Log.e("on failure change pass", t.toString())
                GeneralChanges().hideDialog(progressDialog!!)

            }
        })

    }

    private fun CheckAllFields(): Boolean {

        if (!Validation().validatePassword(old_password, old_password_layout)) return false
        if (!Validation().validatePassword(password, password_layout)) return false

        if (!Validation().validateConfirmPassword(
                confirm_password,
                confirm_password_layout,
                password.text.toString()
            )
        ) return false
        return true
    }


}