package com.example.graduationproject.donor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.graduationproject.R
import com.example.graduationproject.api.donorLogin.loginJson
import com.example.graduationproject.api.fcm.fcmJson
import com.example.graduationproject.charity.activites.CharityMainActivity
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.network.RetrofitInstance
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sign_in.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    lateinit var user_email:String
    lateinit var user_password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

         var from = intent.getBooleanExtra("Donor",true)
          if (from){
              findViewById<AppCompatButton>(R.id.sign_in).setOnClickListener {

                  user_email = email.text.toString()
                  user_password = password.text.toString()
                  // api لفحص الرجستر و التخزين
                  // بدي اخد منها ايدي اليوزر
                  // بدي اخزن الايدي بالشيرد بريفيرنس
                  loginToApp()

              }
          }else{
              findViewById<AppCompatButton>(R.id.sign_in).setOnClickListener {
                  GeneralChanges().prepareFadeTransition(this, CharityMainActivity())
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

        response.enqueue(object : Callback<loginJson> {
            override fun onResponse(call: Call<loginJson>, response: Response<loginJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("TAG", data.toString())

                    if (data!!.success) {

                        val user_id = data.data.id
                        val sharedPref = this@SignInActivity.getSharedPreferences(
                            "sharedPref", Context.MODE_PRIVATE)

                        val editor = sharedPref.edit()
                        editor.putInt("user_id", user_id)
                        Log.e("id in signin", user_id.toString())
                        editor.apply()

                        GeneralChanges().prepareFadeTransition(
                            this@SignInActivity,
                            DonorMainActivity()
                        )


                    }
                    // else {
//                        Toast.makeText(this@SignInActivity, data., Toast.LENGTH_LONG)
//                            .show()
//                    }
                }else{
                    Log.e("errorBody", response.message())
                }

            }

            override fun onFailure(call: Call<loginJson>, t: Throwable) {
                Log.e("TAG", t.message!!)
            }
        })

    }



}