package com.example.graduationproject.donor

import android.app.ActivityOptions
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
import com.example.graduationproject.donor.models.Donor
import com.example.graduationproject.network.ApiRequests
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class SignUpActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener {

    lateinit var spinner: Spinner
     var address: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        val intent = intent.getStringExtra("signup")


        findViewById<AppCompatButton>(R.id.next).setOnClickListener {

            var name = username.editText!!.text.toString()
            var email = email.editText!!.text.toString()
            var phone = phone.editText!!.text.toString()
            var password = password.editText!!.text.toString()
            var confirm_password = confirm_password.editText!!.text.toString()

            if (intent=="donor"){
                var option = ActivityOptions.makeSceneTransitionAnimation(this)
                var intent = Intent(this, CompleteSignUpActivity::class.java)
                intent.putExtra("name",name)
                intent.putExtra("email",email)
                intent.putExtra("phone",phone)
                intent.putExtra("address",address)
                intent.putExtra("password",password)
                intent.putExtra("confirm_password",confirm_password)
                startActivity(intent,option.toBundle())
            }else{
                GeneralChanges().prepareFadeTransition(this, CompleteSignupActivity())
            }
        }

        findViewById<TextView>(R.id.sign_in).setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, SignInActivity())
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
       address = p0!!.getItemAtPosition(p2).toString()
        (spinner.getChildAt(0) as TextView).setTextColor(resources.getColor(R.color.black_transparent_62))

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        spinner.setSelection(0)
        (spinner.getChildAt(0) as TextView).setTextColor(resources.getColor(R.color.black_transparent_62))

    }



}