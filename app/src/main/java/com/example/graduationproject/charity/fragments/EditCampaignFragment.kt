package com.example.graduationproject.charity.fragments

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
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


class EditCampaignFragment : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_edit_campaign, container, false)

         getDate(root)
         getTime(root)

        return root
    }

    private fun getDate(root:View){
        val calendar = Calendar.getInstance()

        root.edit_date_picker.init(calendar[Calendar.YEAR],
            calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH],
            OnDateChangedListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val locale = Locale("ar", "SA")

                val myFormat = "EEEE، d MMMM y"
                val sdf = SimpleDateFormat(myFormat, locale)
                //root.set_date.text = sdf.format(calendar.time)
            })

    }

    private fun getTime(root: View){
        root.edit_time_picker.setOnTimeChangedListener(OnTimeChangedListener { view, hourOfDay, minute -> //use getFocusedChild() helps partly
            if (view.focusedChild != null) {
                val initialCalendar = Calendar.getInstance()
                initialCalendar[Calendar.HOUR_OF_DAY] = hourOfDay
                initialCalendar[Calendar.MINUTE] = minute
                initialCalendar[Calendar.SECOND] = 0

                val locale = Locale("ar", "SA")

                val myFormat = "h:mm a"
                val sdf = SimpleDateFormat(myFormat, locale)
               // root.set_time.text = sdf.format(initialCalendar.time)
            }
        })
    }

}