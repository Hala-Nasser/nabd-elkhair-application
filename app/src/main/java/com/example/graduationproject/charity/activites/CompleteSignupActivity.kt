package com.example.graduationproject.charity.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.donor.DonorMainActivity
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.donor.models.DonationType
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*

class CompleteSignupActivity : AppCompatActivity() {
      private lateinit var  donationTypeList: MutableList<DonationType>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charity_complete_signup)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        charity_sign_up.setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, CharityMainActivity())
        }
        donationTypeList = mutableListOf()
        donationTypeList.add(DonationType(R.drawable.kitchen_utensils))
        donationTypeList.add(DonationType(R.drawable.clothes))
        donationTypeList.add(DonationType(R.drawable.food))
        donationTypeList.add(DonationType(R.drawable.money))
        donationTypeList.add(DonationType(R.drawable.furniture))

        rv_complete_signup_donation_type.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
        rv_complete_signup_donation_type.setHasFixedSize(true)
        val storyAdapter =
            DonationTypeAdapter(this, donationTypeList,"CompleteSignup")
        rv_complete_signup_donation_type.adapter = storyAdapter
    }
}