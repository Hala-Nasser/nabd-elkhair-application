package com.example.graduationproject.donor

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.fcm.FCMJson
import com.example.graduationproject.api.donorApi.forgotPassword.ForgotPasswordJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_forgot_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        GeneralChanges().fadeTransition(this)

        val sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val isDonor = sharedPref.getBoolean("isDonor", false)

        send.setOnClickListener {
            if (Validation().validateEmail(email, email_layout)) {
                if (isDonor) {
                    forgotPassword(email.text.toString())
                } else {
                    charityForgotPassword(email.text.toString())
                }
            }
        }

        findViewById<ImageView>(R.id.back).setOnClickListener {
            onBackPressed()
        }
    }

    fun forgotPassword(email: String) {

        Log.e("hala", "enter")

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.forgotPassword(email)

        response.enqueue(object : Callback<ForgotPasswordJson> {
            override fun onResponse(
                call: Call<ForgotPasswordJson>,
                response: Response<ForgotPasswordJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("TAG", data!!.status.toString())
                    if (data.status)
                        GeneralChanges().prepareFadeTransition(
                            this@ForgotPasswordActivity,
                            ResetPasswordActivity()
                        )
                    else
                        Validation().showSnackBar(parent_layout, data.message)
                } else {
                    Log.e("errorBody", response.message())
                }
            }

            override fun onFailure(call: Call<ForgotPasswordJson>, t: Throwable) {
                Log.e("TAG", t.message!!)
            }
        })

    }

    fun charityForgotPassword(email: String) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityForgotPassword(email)

        response.enqueue(object : Callback<ForgotPasswordJson> {
            override fun onResponse(
                call: Call<ForgotPasswordJson>,
                response: Response<ForgotPasswordJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("TAG", data!!.status.toString())
                    if (data.status)
                        GeneralChanges().prepareFadeTransition(
                            this@ForgotPasswordActivity,
                            ResetPasswordActivity()
                        )
                    else
                        Validation().showSnackBar(parent_layout, data.message)
                } else {
                    Log.e("errorBody", response.message())
                }
            }

            override fun onFailure(call: Call<ForgotPasswordJson>, t: Throwable) {
                Log.e("TAG", t.message!!)
            }
        })

    }
}