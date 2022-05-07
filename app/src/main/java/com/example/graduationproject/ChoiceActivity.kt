package com.example.graduationproject

import android.content.Context
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

        val sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

        val editor = sharedPref.edit()

        findViewById<AppCompatButton>(R.id.donor).setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, SignUpActivity())
            editor.putBoolean("isDonor", true)
            editor.apply()
            Log.e("is donor", sharedPref.getBoolean("isDonor", false).toString())
        }

        findViewById<AppCompatButton>(R.id.charity).setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, SignUpActivity())
            editor.putBoolean("isDonor", false)
            editor.apply()
            Log.e("is donor", sharedPref.getBoolean("isDonor", false).toString())
        }
    }
}