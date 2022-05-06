package com.example.graduationproject.donor

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.resetPassword.ResetPasswordJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_reset_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordActivity : AppCompatActivity() {

    var isAllFieldsChecked = false
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        save.setOnClickListener {

            isAllFieldsChecked = CheckAllFields()

            if (isAllFieldsChecked) {
                progressDialog = ProgressDialog(this)
                GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
                val code = code.text.toString()
                val password = password.text.toString()
                val confirm_password = confirm_password.text.toString()
                resetPassword(code, password, confirm_password)
            }
        }

        back.setOnClickListener{
            onBackPressed()
        }

    }

    fun resetPassword(code: String, password: String, confirm_password: String) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.resetPassword(code, password, confirm_password)

        response.enqueue(object : Callback<ResetPasswordJson> {
            override fun onResponse(
                call: Call<ResetPasswordJson>,
                response: Response<ResetPasswordJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("TAG", data!!.status.toString())
                    if (data.status) {
                        GeneralChanges().prepareFadeTransition(
                            this@ResetPasswordActivity,
                            SignInActivity()
                        )
                        GeneralChanges().hideDialog(progressDialog!!)
                    }
                    else {
                        Validation().showSnackBar(parent_layout, data.message)
                        GeneralChanges().hideDialog(progressDialog!!)
                    }
                } else {
                    Log.e("errorBody", response.message())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<ResetPasswordJson>, t: Throwable) {
                Log.e("on failure reset pass", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)

            }
        })

    }


    private fun CheckAllFields(): Boolean {
        if (!Validation().validateCode(code, code_layout)) return false

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