package com.example.graduationproject.donor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.graduationproject.R
import com.example.graduationproject.charity.activites.CharityMainActivity
import com.example.graduationproject.classes.GeneralChanges

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

         var from = intent.getBooleanExtra("Donor",true)
          if (from){
              findViewById<AppCompatButton>(R.id.sign_in).setOnClickListener {
                  // api لفحص الرجستر و التخزين
                  // بدي اخد منها ايدي اليوزر
                  // بدي اخزن الايدي بالشيرد بريفيرنس
                  GeneralChanges().prepareFadeTransition(this, DonorMainActivity())
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
}