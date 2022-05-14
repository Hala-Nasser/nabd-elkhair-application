package com.example.graduationproject.charity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R

import android.app.Activity
import android.app.ProgressDialog
import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import com.example.graduationproject.api.charityApi.paymentLinks.PaymentLinksJson
import com.example.graduationproject.charity.activites.CharityMainActivity
import com.example.graduationproject.charity.fragments.CharitySettingsFragment
import com.example.graduationproject.charity.fragments.PaymentSettingsFragment
import com.example.graduationproject.charity.models.PaymentLink
import com.example.graduationproject.classes.FileUtil
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.donor.ForgotPasswordActivity
import com.example.graduationproject.donor.fragments.ChangePasswordFragment
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bd_payment_item.view.*
import kotlinx.android.synthetic.main.bottom_sheet_manually.view.*
import kotlinx.android.synthetic.main.bottom_sheet_manually.view.close
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_jawal_pal.view.*
import kotlinx.android.synthetic.main.payment_item.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class PaymentLinksAdapter(
    var activity: Context?, var data: List<PaymentLink>, var fragment: FragmentManager
) : RecyclerView.Adapter<PaymentLinksAdapter.PaymentLinksViewHolder>(){
    var progressDialog: ProgressDialog? = null
    class PaymentLinksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image  =itemView.payment_link_img
        val checked  =itemView.payment_checked
        val card  =itemView.payment_card

        fun initialize(data: PaymentLink) {
            image.setImageResource(data.image!!)
            if(data.link == null){
                checked.visibility = View.INVISIBLE
                card.apply {
                    strokeColor = resources.getColor(R.color.grey_details)
                }
            }else{
                checked.visibility = View.VISIBLE

                card.apply {
                    strokeColor = resources.getColor(R.color.app_color)
                }
            }
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentLinksViewHolder{
            var view: View = LayoutInflater.from(activity).inflate(R.layout.payment_item, parent, false)
            return PaymentLinksViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PaymentLinksViewHolder, position: Int) {

        holder.initialize(data[position])
             holder.itemView.setOnClickListener {
                 bottomSheet(activity as Activity, position)
             }



    }


    fun bottomSheet(activity: Activity, position: Int){
        var view = activity.layoutInflater.inflate(R.layout.bd_payment_item, null)
        var bottomSheetDialog = BottomSheetDialog(activity)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.setCanceledOnTouchOutside(false)
        view.close.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        view.payPal_txt.setText(data[position].link)
        view.title_payment.text = data[position].name

        view.edit_payment.setOnClickListener {
            progressDialog = ProgressDialog(activity)
            GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
            updatePaymentLinks(view.payPal_txt.text.toString(),position,bottomSheetDialog)
        }

        view.copyPin.setOnClickListener {
            val clipboard =
                activity.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("تم نسخ الرسالة", view.payPal_txt.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(activity,"تم نسخ النص", Toast.LENGTH_SHORT).show()
        }

        bottomSheetDialog.show()
    }

    fun updatePaymentLinks(link:String,position: Int,bottomSheetDialog: BottomSheetDialog) {

      var sharedPref = activity!!.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
       var token = sharedPref.getString("charity_token", "")!!


        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("id", data[position].id.toString())

        when (position) {
            0 -> {
                body.addFormDataPart("paypal_link", link)
            }
            1 -> {
                body.addFormDataPart("visa_link", link)
            }
            2 -> {
                body.addFormDataPart("creditcard_link", link)
            }
        }


        var requestBody = body.build()
        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.updatePaymentLinks("Bearer $token",
            requestBody)

        response.enqueue(object : Callback<PaymentLinksJson> {
            override fun onResponse(
                call: Call<PaymentLinksJson>,
                response: Response<PaymentLinksJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.status){
                        Log.e("payment Links",data.toString())
                        var bundle = Bundle()
                        bundle.putString("from", "PaymentSetting")
                        var f = CharitySettingsFragment()
                        f.arguments = bundle
                        fragment.beginTransaction()
                            .replace(R.id.charityContainer, f).addToBackStack(null).commit()
                        GeneralChanges().hideDialog(progressDialog!!)
                        bottomSheetDialog.dismiss()
                    }
                    else {
                        GeneralChanges().hideDialog(progressDialog!!)
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

}