package com.example.graduationproject.charity.fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.AboutActivity
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.logout.LogoutJson
import com.example.graduationproject.api.donorApi.paymentLinks.PaymentLinksJson
import com.example.graduationproject.charity.activites.PaymentsMethodActivity
import com.example.graduationproject.charity.adapters.PaymentLinksAdapter
import com.example.graduationproject.charity.models.PaymentLink
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.donor.SignInActivity
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.fragment_payment_settings.*
import kotlinx.android.synthetic.main.fragment_payment_settings.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PaymentSettingsFragment : Fragment() {

    var progressDialog: ProgressDialog? = null
    var token = ""
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_payment_settings, container, false)

        sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("charity_token", "")!!

        root.payment_settings_privacy_policy.setOnClickListener {
            var i = Intent(requireContext(), AboutActivity::class.java)
            startActivity(i)
        }

        root.payment_settings_sign_out.setOnClickListener {
            getAlertDialog()
        }

        root.add_payment_link.setOnClickListener {
            var i = Intent(requireContext(), PaymentsMethodActivity::class.java)
            startActivity(i)
        }

        getPaymentLinks()
        return root
    }

    fun getAlertDialog() {
        var alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("تسجيل الخروج")
        alertDialog.setMessage("هل تريد تسجيل الخروج فعلاً!!")
        alertDialog.setCancelable(false)
        alertDialog.setIcon(R.drawable.ic_baseline_login_24)

        alertDialog.setPositiveButton("تسجيل الخروج") { dialogInterface, i ->
            progressDialog = ProgressDialog(activity)
            GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
            logout()
        }

        alertDialog.setNegativeButton("لا,شكراً") { dialogInterface, i ->
            Log.e("ok", "ok")
        }
        var alertDialogCreate = alertDialog.create()
        alertDialogCreate.window!!.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        alertDialogCreate.show()
    }

    fun logout() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityLogout("Bearer $token")

        response.enqueue(object : Callback<LogoutJson> {
            override fun onResponse(call: Call<LogoutJson>, response: Response<LogoutJson>) {
                if (response.isSuccessful) {
                    Log.e("Logout", response.message())
                    val i = Intent(requireActivity(), SignInActivity()::class.java)
                    i.putExtra("Donor", false)
                    startActivity(i)
                    requireActivity().finish()
                    GeneralChanges().hideDialog(progressDialog!!)
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<LogoutJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }

    fun getPaymentLinks() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getPaymentLinks("Bearer $token")

        response.enqueue(object : Callback<PaymentLinksJson> {
            override fun onResponse(
                call: Call<PaymentLinksJson>,
                response: Response<PaymentLinksJson>
            ) {
                var list = ArrayList<PaymentLink>()
                if (response.isSuccessful) {

                    val data = response.body()
                    //val data = response.body()!!.data

                    if (data!!.status) {
                        if (data.data == null) {
                            rv_payments_link.visibility = View.GONE
                            empty_payment.visibility = View.VISIBLE
                        } else {
                            rv_payments_link.visibility = View.VISIBLE
                            empty_payment.visibility = View.GONE
                            Log.e("data", data.data.toString())
                            list.add(
                                PaymentLink(
                                    data.data.id,
                                    data.data.charity_id,
                                    "PayPal",
                                    R.drawable.paypal_logo,
                                    data.data.paypal_link
                                )
                            )
                            list.add(
                                PaymentLink(
                                    data.data.id,
                                    data.data.charity_id,
                                    "Visa",
                                    R.drawable.visa,
                                    data.data.visa_link
                                )
                            )
                            list.add(
                                PaymentLink(
                                    data.data.id,
                                    data.data.charity_id,
                                    "Master Card",
                                    R.drawable.new_master_card,
                                    data.data.creditcard_link
                                )
                            )

                            rv_payments_link.layoutManager = LinearLayoutManager(
                                requireContext(),
                                RecyclerView.HORIZONTAL,
                                false
                            )
                            rv_payments_link.setHasFixedSize(true)
                            var paymentLinksAdapter =
                                PaymentLinksAdapter(
                                    requireActivity(),
                                    list,
                                    requireActivity().supportFragmentManager
                                )
                            rv_payments_link.adapter = paymentLinksAdapter
                        }
                    } else {
                        Log.e("is successful", "status false")
                    }


                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<PaymentLinksJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }
}