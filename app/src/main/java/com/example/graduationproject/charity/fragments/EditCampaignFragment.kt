package com.example.graduationproject.charity.fragments

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.DatePicker.OnDateChangedListener
import androidx.annotation.RequiresApi
import com.example.graduationproject.R
import kotlinx.android.synthetic.main.fragment_edit_campaign.*
import kotlinx.android.synthetic.main.fragment_edit_campaign.view.*
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import android.widget.TimePicker
import android.widget.TimePicker.OnTimeChangedListener
import com.example.graduationproject.api.charityApi.campaign.CampaignJson
import com.example.graduationproject.classes.FileUtil
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.fragment_add_campaign.*
import kotlinx.android.synthetic.main.fragment_charity_edit_profile.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.ParseException


class EditCampaignFragment : Fragment() {
    var time =""
    var date =""
    var campaignId =0
    var token=""
    var progressDialog: ProgressDialog? = null
    lateinit var b:Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val languageToLoad = "ar"

        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_edit_campaign, container, false)

        var sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("charity_token", "")!!

         b = requireArguments()

        campaignId = b.getInt("campaignId")
        date =  b.getString("campaignDate")!!
        time =  b.getString("campaignTime")!!

        getDate(root)
        getTime(root)

        root.update_campaign.setOnClickListener {
            Log.e("date",date+time)
            progressDialog = ProgressDialog(requireContext())
            GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
            updateCampaign()
        }

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return root
    }

    private fun getDate(root:View){
        val calendar = Calendar.getInstance()
        var year = GeneralChanges().getFromCalendar("EEEE، d MMMM y",date!!,Calendar.YEAR)
        var day = GeneralChanges().getFromCalendar("EEEE، d MMMM y",date,Calendar.DAY_OF_MONTH)
        var month = GeneralChanges().getFromCalendar("EEEE، d MMMM y",date,Calendar.MONTH)
        root.edit_date_picker.init(year,month,day,
            OnDateChangedListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val locale = Locale("ar", "SA")

                val myFormat = "EEEE، d MMMM y"
                val sdf = SimpleDateFormat(myFormat, locale)
                date = sdf.format(calendar.time)
            })

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTime(root: View){

        var hours = GeneralChanges().getFromCalendar("h:mm a",time,Calendar.HOUR_OF_DAY)
        var minutes = GeneralChanges().getFromCalendar("h:mm a",time,Calendar.MINUTE)
        root.edit_time_picker.hour = hours
        root.edit_time_picker.minute = minutes
        root.edit_time_picker.setOnTimeChangedListener(OnTimeChangedListener { view, hourOfDay, minute -> //use getFocusedChild() helps partly
                val initialCalendar = Calendar.getInstance()
                initialCalendar[Calendar.HOUR_OF_DAY] = hourOfDay
                initialCalendar[Calendar.MINUTE] = minute
                initialCalendar[Calendar.SECOND] = 0

                val locale = Locale("ar", "SA")

                val myFormat = "h:mm a"
                val sdf = SimpleDateFormat(myFormat, locale)
               time = sdf.format(initialCalendar.time)
        })
    }


    fun updateCampaign() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.updateCampaign("Bearer $token",campaignId,date,time)

        response.enqueue(object : Callback<CampaignJson> {
            override fun onResponse(call: Call<CampaignJson>, response: Response<CampaignJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("Update Campaign", data.toString())
                    if (data!!.status) {

                        GeneralChanges().hideDialog(progressDialog!!)
                        var bundle = Bundle()
                        bundle.putBoolean("addCampaign",false)
                        var fragment = HomeFragment()
                        fragment.arguments = bundle
                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.charityContainer,fragment).commit()
//                        var bundle = Bundle()
//                        bundle.putString("from", "EditCampaign")
//                        bundle.putString("date",data.data.expiry_date)
//                        bundle.putString("time",data.data.expiry_time)
//                        var fragment = CharityCampaignDetailsFragment()
//                        fragment.arguments = bundle
//                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.charityContainer,fragment).addToBackStack(null).commit()

                    }else{
                        GeneralChanges().hideDialog(progressDialog!!)
                        Validation().showSnackBar(add_campaign_layout, data.message)
                    }

                } else {
                    GeneralChanges().hideDialog(progressDialog!!)
                    Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<CampaignJson>, t: Throwable) {
                GeneralChanges().hideDialog(progressDialog!!)
                Log.e("failure", t.message!!)
            }
        })
    }

}