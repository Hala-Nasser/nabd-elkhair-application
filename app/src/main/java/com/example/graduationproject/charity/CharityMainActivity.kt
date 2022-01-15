package com.example.graduationproject.charity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.R

class CharityMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charity_main)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)
    }
}