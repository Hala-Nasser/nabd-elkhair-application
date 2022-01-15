package com.example.graduationproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.example.graduationproject.charity.CharityMainActivity
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.donor.SignUpActivity

class ChoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        findViewById<AppCompatButton>(R.id.donor).setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, SignUpActivity())
        }

        findViewById<AppCompatButton>(R.id.charity).setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, CharityMainActivity())
        }
    }
}