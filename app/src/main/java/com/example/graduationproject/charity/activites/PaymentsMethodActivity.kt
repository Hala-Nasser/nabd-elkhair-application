package com.example.graduationproject.charity.activites

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.charity.fragments.*
import com.example.graduationproject.classes.GeneralChanges
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*
import kotlinx.android.synthetic.main.activity_payments_method.*
import kotlinx.android.synthetic.main.fragment_charity_home.*
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import kotlinx.android.synthetic.main.tab_content.*

class PaymentsMethodActivity : AppCompatActivity() , View.OnLongClickListener, View.OnClickListener{
    var jawalPalChecked = false
    var visaChecked = false
    var payPalChecked = false
    var longClick = true
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments_method)

        payPal.setOnClickListener(this)
        visa.setOnClickListener(this)
        jawalPal.setOnClickListener(this)

        payPal.setOnLongClickListener(this)
        visa.setOnLongClickListener(this)
        jawalPal.setOnLongClickListener(this)

        charity_payments_save.setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, CharityMainActivity())
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        sectionsPagerAdapter.addFragments(PayPalFragment())
        sectionsPagerAdapter.addFragments(VisaFragment())
        sectionsPagerAdapter.addFragments(JawalPalFragment())
         paymentLinksPager.adapter = sectionsPagerAdapter

    }



    override fun onLongClick(p0: View?): Boolean {

        if (longClick){
            longClick = false
            when(p0!!.id) {
                R.id.payPal -> {
                    jawalPalChecked = false
                    visaChecked = false
                    payPalChecked = true
                    paymentLinksPager.currentItem = 0
                    changePaymentItem(payPalChecked,payPal,paypal_checked)
                }
                R.id.visa -> {
                    jawalPalChecked = false
                    visaChecked = true
                    payPalChecked = false
                    paymentLinksPager.currentItem = 1
                    changePaymentItem(visaChecked,visa,visa_checked)
                }
                R.id.jawalPal -> {
                    jawalPalChecked = true
                    visaChecked = false
                    payPalChecked = false
                    paymentLinksPager.currentItem = 2
                    changePaymentItem(jawalPalChecked,jawalPal,jawalPal_checked)
                }
            }
        }else{
            longClick = true
            when(p0!!.id) {
                R.id.payPal -> {
                    jawalPalChecked = false
                    visaChecked = false
                    payPalChecked = false
                    paymentLinksPager.currentItem = 0
                    changePaymentItem(payPalChecked,payPal,paypal_checked)
                }
                R.id.visa -> {
                    jawalPalChecked = false
                    visaChecked = false
                    payPalChecked = false
                    paymentLinksPager.currentItem = 1
                    changePaymentItem(visaChecked,visa,visa_checked)
                }
                R.id.jawalPal -> {
                    jawalPalChecked = false
                    visaChecked = false
                    payPalChecked = false
                    paymentLinksPager.currentItem = 2
                    changePaymentItem(jawalPalChecked,jawalPal,jawalPal_checked)
                }
            }
        }

       return true
    }

    override fun onClick(p0: View?) {

        when(p0!!.id) {
            R.id.payPal -> {
                paymentLinksPager.currentItem = 0
            }
            R.id.visa -> {
                paymentLinksPager.currentItem = 1
            }
            R.id.jawalPal -> {
                paymentLinksPager.currentItem = 2
            }
        }
    }


    private fun changePaymentItem(checked:Boolean,cardView: MaterialCardView,image:ImageView) {
        if (checked) {
            cardView.apply {
                strokeColor = resources.getColor(R.color.app_color)
                strokeWidth = 2
                cardElevation = 8f
            }
            image.visibility = View.VISIBLE
        }else {
            cardView.apply {
                strokeColor = resources.getColor(R.color.payment_color)
                strokeWidth = 2
                cardElevation = 0f
            }
            image.visibility = View.GONE
        }
    }
}