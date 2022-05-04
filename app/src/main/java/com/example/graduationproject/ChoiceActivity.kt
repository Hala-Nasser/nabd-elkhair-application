package com.example.graduationproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import com.example.graduationproject.charity.activites.CharityMainActivity
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.donor.SignUpActivity

class ChoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        findViewById<AppCompatButton>(R.id.donor).setOnClickListener {
            GeneralChanges().prepareFadeTransitionWithData(this, SignUpActivity(),"signup","donor")
            Log.e("choice", "donor")
        }

        findViewById<AppCompatButton>(R.id.charity).setOnClickListener {
            GeneralChanges().prepareFadeTransitionWithData(this, SignUpActivity(),"signup","charity")
        }
    }
}