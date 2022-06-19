package com.example.graduationproject.donor

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.login.LoginJson
import com.example.graduationproject.api.charityApi.login.LoginJson as CharityLoginJson
import com.example.graduationproject.charity.activites.CharityMainActivity
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_sign_in.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.Gravity
import android.view.View
import android.view.ViewGroup

import android.widget.FrameLayout
import com.example.graduationproject.charity.activites.CompleteSignupActivity
import com.example.graduationproject.charity.activites.PaymentsMethodActivity
import com.example.graduationproject.classes.Validation
import com.google.android.material.snackbar.BaseTransientBottomBar

import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_sign_in.password
import kotlinx.android.synthetic.main.activity_sign_in.password_layout
import kotlinx.android.synthetic.main.fragment_second_step_view_donation_manual.*
import java.text.Format
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.List


class SignInActivity : AppCompatActivity() {
    lateinit var user_email: String
    lateinit var user_password: String
    var isAllFieldsChecked = false
    var isFirstLogin = "false"
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)


        if (intent.getStringExtra("previous") != null){
            Validation().showSnackBar(findViewById(R.id.parent_layout), "تم التسجيل بنجاح. انتظر تفعيل حسابك")
        }

        val sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val isDonor = sharedPref.getBoolean("isDonor", false)


        findViewById<AppCompatButton>(R.id.sign_in).setOnClickListener {

            user_email = email.text.toString()
            user_password = password.text.toString()

            isAllFieldsChecked = CheckAllFields()

            if (isAllFieldsChecked) {
                progressDialog = ProgressDialog(this)
                GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
                if (isDonor) {
                    loginToApp()

                } else {
                    charityLoginToApp()
                }
            }


        }



        findViewById<TextView>(R.id.sign_up).setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, SignUpActivity())
        }

        findViewById<TextView>(R.id.forgot_password).setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, ForgotPasswordActivity())
        }
    }

    fun loginToApp() {
        val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("email", user_email)
            .addFormDataPart("password", user_password)
            .build()
        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.logIn(body)

        response.enqueue(object : Callback<LoginJson> {
            override fun onResponse(call: Call<LoginJson>, response: Response<LoginJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("TAG", data.toString())
                    if (data!!.status) {
                        val user_id = data.data.id
                        val sharedPref = this@SignInActivity.getSharedPreferences(
                            "sharedPref", Context.MODE_PRIVATE
                        )

                        val editor = sharedPref.edit()
                        editor.putString("from", "donor")
                        editor.putInt("user_id", user_id)
                        editor.putString("user_token", data.data.token)
                        editor.putString("user_image", data.data.image)
                        Log.e("id in signin", user_id.toString())
                        editor.apply()
                        GeneralChanges().hideDialog(progressDialog!!)
                        GeneralChanges().prepareFadeTransition(
                            this@SignInActivity,
                            DonorMainActivity()
                        )

                    } else {
                        GeneralChanges().hideDialog(progressDialog!!)
                        Validation().showSnackBar(findViewById(R.id.parent_layout), data.message)

                    }

                } else {
                    GeneralChanges().hideDialog(progressDialog!!)
                    Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
                    Log.e("errorBody", response.body().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<LoginJson>, t: Throwable) {
                Log.e("on failure sign in", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)

            }
        })

    }

    fun charityLoginToApp() {

        val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("email", user_email)
            .addFormDataPart("password", user_password)
            .build()

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityLogIn(body)

        response.enqueue(object : Callback<CharityLoginJson> {
            override fun onResponse(
                call: Call<CharityLoginJson>,
                response: Response<CharityLoginJson>
            ) {

                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("Charity", data.toString())

                    if (data!!.status) {

                        val user_id = data.data.id
                        val sharedPref = this@SignInActivity.getSharedPreferences(
                            "sharedPref", Context.MODE_PRIVATE
                        )

                        val editor = sharedPref.edit()
                        editor.putString("from", "charity")
                        editor.putInt("charity_id", user_id)
                        editor.putString("charity_token", data.data.token)
                        Log.e("Charity id in signin", user_id.toString())
                        editor.apply()

                        GeneralChanges().hideDialog(progressDialog!!)
                            GeneralChanges().prepareFadeTransition(
                                this@SignInActivity,
                                CharityMainActivity()
                            )
                    } else {
                        GeneralChanges().hideDialog(progressDialog!!)
                        Validation().showSnackBar(findViewById(R.id.parent_layout), data.message)

                    }

                } else {
                    GeneralChanges().hideDialog(progressDialog!!)
                    Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
                    Log.e("errorBody", response.body().toString())
                }

            }

            override fun onFailure(call: Call<CharityLoginJson>, t: Throwable) {
                Log.e("on failure sign in", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })

    }

    private fun CheckAllFields(): Boolean {
        if (!Validation().validateEmail(email, email_layout)) return false

        if (!Validation().validatePassword(password, password_layout)) return false
        return true
    }


}