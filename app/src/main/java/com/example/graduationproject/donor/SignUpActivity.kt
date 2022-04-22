package com.example.graduationproject.donor

import android.app.ActivityOptions
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.R
import com.example.graduationproject.charity.activites.CompleteSignupActivity
import com.example.graduationproject.classes.Validation
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.email
import kotlinx.android.synthetic.main.activity_sign_up.password


class SignUpActivity : AppCompatActivity(){

    var user_address: String?=null
    var isAllFieldsChecked = false

    lateinit var user_name:String
    lateinit var user_email:String
    lateinit var user_phone:String
    lateinit var user_password:String
    lateinit var user_confirm_password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        val intent = intent.getStringExtra("signup")


        findViewById<AppCompatButton>(R.id.next).setOnClickListener {

            user_name = username.text.toString()
            user_email = email.text.toString()
            user_phone = phone.text.toString()
            user_password = password.text.toString()
            user_confirm_password = confirm_password.text.toString()

            isAllFieldsChecked = CheckAllFields()

            if (isAllFieldsChecked) {
                if (intent=="donor"){
                    var option = ActivityOptions.makeSceneTransitionAnimation(this)
                    var intent = Intent(this, CompleteSignUpActivity::class.java)
                    intent.putExtra("name",user_name)
                    intent.putExtra("email",user_email)
                    intent.putExtra("phone",user_phone)
                    intent.putExtra("address",user_address)
                    intent.putExtra("password",user_password)
                    intent.putExtra("confirm_password",user_confirm_password)
                    startActivity(intent,option.toBundle())
                }else{
                    GeneralChanges().prepareFadeTransition(this, CompleteSignupActivity())
                }
            }


        }

        findViewById<TextView>(R.id.sign_in).setOnClickListener {
            if (intent=="donor") {
                GeneralChanges().prepareFadeTransitionWithData(
                    this,
                    SignInActivity(),
                    "signup",
                    "donor"
                )
            }else{
                GeneralChanges().prepareFadeTransitionWithData(
                    this,
                    SignInActivity(),
                    "signup",
                    "charity"
                )
            }
        }


        if (intent=="donor"){
            val locations = resources.getStringArray(R.array.donor_location_array)
            val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, locations)
            autoCompleteTextView.setAdapter(arrayAdapter)
            user_address = autoCompleteTextView.text.toString()

        }else{
            val locations = resources.getStringArray(R.array.charity_location_array)
            val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, locations)
            autoCompleteTextView.setAdapter(arrayAdapter)
        }


    }

    private fun CheckAllFields(): Boolean {
        if (!Validation().validateUsername(username, username_layout, findViewById(R.id.parent_layout))) return false

        if (!Validation().validateEmail(email, email_layout, findViewById(R.id.parent_layout))) return false

        if (!Validation().validatePhoneNumber(phone, phone_layout, findViewById(R.id.parent_layout))) return false

        if (!Validation().validatePassword(password, password_layout, findViewById(R.id.parent_layout))) return false

        if (!Validation().validateConfirmPassword(confirm_password, confirm_password_layout, findViewById(R.id.parent_layout), password.text.toString())) return false

        if (!Validation().validateLocation(autoCompleteTextView, location_layout)) return false
        return true
    }



}