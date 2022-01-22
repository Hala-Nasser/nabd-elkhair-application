package com.example.graduationproject.donor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.R

class SignUpActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        findViewById<AppCompatButton>(R.id.next).setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, CompleteSignUpActivity())
        }

        findViewById<TextView>(R.id.sign_in).setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, SignInActivity())
        }

        spinner = findViewById(R.id.location_spinner)
        spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
                this,
                R.array.donor_location_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        spinner.setSelection(p2)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        spinner.setSelection(0)
    }
}