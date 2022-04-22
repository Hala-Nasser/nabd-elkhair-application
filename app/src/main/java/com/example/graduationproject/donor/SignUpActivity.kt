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


class SignUpActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener {

    lateinit var spinner: Spinner
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

        spinner = findViewById(R.id.location_spinner)
        spinner.onItemSelectedListener = this
        if (intent=="donor"){
            ArrayAdapter.createFromResource(
                this,
                R.array.donor_location_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }else{
            ArrayAdapter.createFromResource(
                this,
                R.array.charity_location_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }


    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        spinner.setSelection(p2)
        user_address = p0!!.getItemAtPosition(p2).toString()
        (spinner.getChildAt(0) as TextView).setTextColor(resources.getColor(R.color.black_transparent_62))

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        spinner.setSelection(0)
        (spinner.getChildAt(0) as TextView).setTextColor(resources.getColor(R.color.black_transparent_62))

    }

    private fun CheckAllFields(): Boolean {
        if (!Validation().validateUsername(username, username_layout, findViewById(R.id.parent_layout))) return false

        if (!Validation().validateEmail(email, email_layout, findViewById(R.id.parent_layout))) return false

        if (!Validation().validatePhoneNumber(phone, phone_layout, findViewById(R.id.parent_layout))) return false

        if (!Validation().validatePassword(password, password_layout, findViewById(R.id.parent_layout))) return false

        if (!Validation().validateConfirmPassword(password, password_layout, findViewById(R.id.parent_layout))) return false
        return true
//        if (user_email.trim().isEmpty()) {
//            email.error = "الايميل مطلوب"
//            return false
//        }
//        if (!user_email.isValidEmail()){
//            email.error = "الايميل غير صحيح"
//            return false
//        }
//        if (user_password.isEmpty()) {
//            password.error = "كلمة المرور مطلوبة"
//            return false
//        }
//        return true
    }



}