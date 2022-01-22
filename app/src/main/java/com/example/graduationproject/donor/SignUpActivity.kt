package com.example.graduationproject.donor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.R
import com.example.graduationproject.charity.activites.CompleteSignupActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        val intent = intent.getStringExtra("signup")

        findViewById<AppCompatButton>(R.id.next).setOnClickListener {
            if (intent=="donor"){
                GeneralChanges().prepareFadeTransition(this, CompleteSignUpActivity())
            }else{
                GeneralChanges().prepareFadeTransition(this, CompleteSignupActivity())
            }
        }

        findViewById<TextView>(R.id.sign_in).setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, SignInActivity())
        }
    }
}