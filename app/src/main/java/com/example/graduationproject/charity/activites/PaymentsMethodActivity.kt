package com.example.graduationproject.charity.activites

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.api.donorApi.changePassword.ChangePasswordJson
import com.example.graduationproject.api.donorApi.paymentLinks.PaymentLinksJson
import com.example.graduationproject.charity.fragments.*
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*
import kotlinx.android.synthetic.main.activity_payments_method.*
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_charity_home.*
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import kotlinx.android.synthetic.main.fragment_jawal_pal.*
import kotlinx.android.synthetic.main.fragment_jawal_pal.view.*
import kotlinx.android.synthetic.main.fragment_pay_pal.*
import kotlinx.android.synthetic.main.fragment_pay_pal.view.*
import kotlinx.android.synthetic.main.fragment_visa.*
import kotlinx.android.synthetic.main.fragment_visa.view.*
import kotlinx.android.synthetic.main.tab_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.FieldPosition

class PaymentsMethodActivity : AppCompatActivity() ,View.OnClickListener{
    var payPalClick = false
    var visaClick = false
    var jawalClick = false
    var progressDialog: ProgressDialog? = null
    lateinit var sharedPref: SharedPreferences
    var id = 0
    var paypal_link = ""
    var visa_link = ""
    var jawalPal_link = ""
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments_method)

        sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        id = sharedPref.getInt("charity_id", 0)


        payPal.setOnClickListener(this)
        visa.setOnClickListener(this)
        jawalPal.setOnClickListener(this)

        charity_payments_save.setOnClickListener {
            progressDialog = ProgressDialog(this)
            GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
            paypal_link = if (payPalClick) {
                paymentLinksPager.currentItem = 0
                payPal_txt.text.toString()
            }else ""
            visa_link = if (visaClick) {
                paymentLinksPager.currentItem = 1
                visa_txt.text.toString()
            }else ""
            jawalPal_link = if (jawalClick) {
                paymentLinksPager.currentItem = 2
                jawalPal_txt.text.toString()
            }else ""
            Log.e("links","$paypal_link,$visa_link,$jawalPal_link")
            addPaymentLinks(paypal_link,visa_link,jawalPal_link)
        }

        ignoreLinks.setOnClickListener {
            getAlertDialog()
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        sectionsPagerAdapter.addFragments(PayPalFragment())
        sectionsPagerAdapter.addFragments(VisaFragment())
        sectionsPagerAdapter.addFragments(JawalPalFragment())
        paymentLinksPager.adapter = sectionsPagerAdapter
    }

    fun addPaymentLinks(paypal_link: String?, visa_link: String?, creditCard_link: String?) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.addPaymentLinks(id,
            paypal_link!!, visa_link!!, creditCard_link!!)

        response.enqueue(object : Callback<PaymentLinksJson> {
            override fun onResponse(
                call: Call<PaymentLinksJson>,
                response: Response<PaymentLinksJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.status){
                        Log.e("payment Links",data.toString())
                        GeneralChanges().hideDialog(progressDialog!!)
                        GeneralChanges().prepareFadeTransition(this@PaymentsMethodActivity, CharityMainActivity())

                    }
                    else {
                        GeneralChanges().hideDialog(progressDialog!!)
                        Validation().showSnackBar(payment_layout, data.message)
                    }
                } else {
                    Log.e("errorBody", response.message())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<PaymentLinksJson>, t: Throwable) {
                Log.e("on failure change pass", t.toString())
                GeneralChanges().hideDialog(progressDialog!!)

            }
        })

    }


    fun getAlertDialog(){
        var alertDialog= AlertDialog.Builder(this)
        alertDialog.setTitle("انتباه")
        alertDialog.setMessage("هذه الشاشة لن تظهر ثانية بالتالي ستقتصر التبرعات الواصلة إليك على اليدوية فقط. يمكنك لاحقا الذهاب للإعدادت لضبط بيانات الدفع الخاصة بك ")
        alertDialog.setCancelable(false)
        alertDialog.setIcon(R.drawable.ic_warning)

        alertDialog.setPositiveButton("تجاهل") { dialogInterface, i ->
            GeneralChanges().prepareFadeTransition(this, CharityMainActivity())
        }

        alertDialog.setNegativeButton("حسناً") { dialogInterface, i ->
            Log.e("ok","ok")
        }
        var alertDialogCreate = alertDialog.create()
        alertDialogCreate.window!!.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        alertDialogCreate.show()
    }

    override fun onClick(p0: View?) {

            when(p0!!.id) {
                R.id.payPal -> {
                    payPalClick = !payPalClick
                    changePaymentItem(payPalClick, 0,payPal, paypal_checked)
                }
                R.id.visa -> {
                    visaClick = !visaClick
                        changePaymentItem(visaClick, 1,visa, visa_checked)
                }
                R.id.jawalPal -> {
                    jawalClick = !jawalClick
                        changePaymentItem(jawalClick, 2,jawalPal, jawalPal_checked)
                }
            }
    }




    private fun changePaymentItem(checked:Boolean,position: Int,cardView: MaterialCardView,image:ImageView) {
        if (checked) {
            cardView.apply {
                strokeColor = resources.getColor(R.color.app_color)
                strokeWidth = 2
                cardElevation = 8f
            }
            image.visibility = View.VISIBLE
            paymentLinksPager.currentItem = position
        }else {
            cardView.apply {
                strokeColor = resources.getColor(R.color.payment_color)
                strokeWidth = 2
                cardElevation = 0f
            }
            image.visibility = View.INVISIBLE
        }
    }

}