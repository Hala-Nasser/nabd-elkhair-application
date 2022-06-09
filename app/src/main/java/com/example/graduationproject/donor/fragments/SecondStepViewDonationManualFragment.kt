package com.example.graduationproject.donor.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.addDonation.AddDonationJson
import com.example.graduationproject.api.donorApi.complaint.ComplaintJson
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.donor.models.DonationType
import com.example.graduationproject.network.RetrofitInstance
import com.github.florent37.expansionpanel.ExpansionLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bottom_dialog_item.view.close
import kotlinx.android.synthetic.main.bottom_dialog_item_manual.view.*
import kotlinx.android.synthetic.main.campaign_item_in_donation_screen.view.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_second_step_view_donation_manual.*
import kotlinx.android.synthetic.main.fragment_second_step_view_donation_manual.view.*
import net.cachapa.expandablelayout.ExpandableLayout
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class SecondStepViewDonationManualFragment : Fragment() {

    lateinit var dialog: BottomSheetDialog
    lateinit var v: View
    var previous_fragment = ""
    var selected_type: String? = null
    var item_image = ""
    var item_name = ""
    val myCalendar: Calendar = Calendar.getInstance()
    var date = ""
    var token = ""

    var district = ""
    var city = ""
    var address = ""
    var phone = ""
    var time = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =
            inflater.inflate(R.layout.fragment_second_step_view_donation_manual, container, false)

        val sharedPref= requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("user_token", "")!!

        val locale = Locale("ar", "SA")
        // EEEE، d MMMM y
        val myFormat = "d MMMM"
        val sdf = SimpleDateFormat(myFormat, locale)
        date = sdf.format(myCalendar.time)
        root.date.text = date

        val b = arguments
        if (b != null) {
            previous_fragment = b.getString("previous_fragment")!!
            item_name = b.getString("campaign_name")!!
            item_image = b.getString("campaign_image")!!
        }
        Log.e("hala image","image: $item_image")
        Log.e("hala name","name: $item_name")
        Log.e("previous fragment", previous_fragment)

        if (previous_fragment == "campaignDetails" || previous_fragment == "charityDetails") {
            root.progress_linear.visibility = View.GONE
        }
        val radioGroup = root.radioGroup

        root.residential_layout.isActivated = false
        root.city_layout.isActivated = false
        root.address_layout.isActivated = false
        root.phone_layout.isActivated = false


        root.residential_layout.setOnClickListener {
            if (root.residential_layout.isActivated) {
                root.expandable_layout_1.isExpanded = !root.expandable_layout_1.isExpanded
            }
        }

        root.expandable_layout_1.setOnExpansionUpdateListener { _, _ ->
            layoutStyle(
                root.image1,
                R.drawable.residential_district,
                R.drawable.residential_district_grey,
                root.title1,
                root.sub_title1,
                root.indicator1,
                root.expandable_layout_1,
                root.expandable_layout_2,
                root.expandable_layout_3,
                root.expandable_layout_4,
                root.expandable_layout_5
            )

        }

        radioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.north -> {
                    selected_type = "الشمال"
                }
                R.id.south -> {
                    selected_type = "الجنوب"
                }
                R.id.middle -> {
                    selected_type = "الوسطى"
                }
                R.id.gaza -> {
                    selected_type = "غزة"
                }
            }
            root.residential_layout.isActivated = true
            root.city_layout.isActivated = true
        }

        root.city_layout.setOnClickListener {
            if (root.city_layout.isActivated) {
                root.expandable_layout_2.isExpanded = !root.expandable_layout_2.isExpanded
            }
        }

        root.expandable_layout_2.setOnExpansionUpdateListener { _, _ ->
            layoutStyle(
                root.image2,
                R.drawable.city,
                R.drawable.city_grey,
                root.title2,
                root.sub_title2,
                root.indicator2,
                root.expandable_layout_2,
                root.expandable_layout_1,
                root.expandable_layout_3,
                root.expandable_layout_4,
                root.expandable_layout_5
            )

        }

        root.city_edit_text.addTextChangedListener {
            if (root.city_edit_text.text.isNotEmpty() && root.city_edit_text.text.toString() != " ") {
                root.address_layout.isActivated = true
            }
        }

        root.address_layout.setOnClickListener {
            if (root.address_layout.isActivated) {
                root.expandable_layout_3.isExpanded = !root.expandable_layout_3.isExpanded
            }
        }

        root.expandable_layout_3.setOnExpansionUpdateListener { _, _ ->
            layoutStyle(
                root.image3,
                R.drawable.location,
                R.drawable.location_grey,
                root.title3,
                root.sub_title3,
                root.indicator3,
                root.expandable_layout_3,
                root.expandable_layout_1,
                root.expandable_layout_2,
                root.expandable_layout_4,
                root.expandable_layout_5
            )

        }

        root.address_edit_text.addTextChangedListener {
            if (root.address_edit_text.text.isNotEmpty() && root.address_edit_text.text.toString() != " ") {
                root.phone_layout.isActivated = true
            }
        }

        root.phone_layout.setOnClickListener {
            if (root.phone_layout.isActivated) {
                root.expandable_layout_4.isExpanded = !root.expandable_layout_4.isExpanded
            }
        }

        root.expandable_layout_4.setOnExpansionUpdateListener { _, _ ->
            layoutStyle(
                root.image4,
                R.drawable.phone,
                R.drawable.phone_grey,
                root.title4,
                root.sub_title4,
                root.indicator4,
                root.expandable_layout_4,
                root.expandable_layout_1,
                root.expandable_layout_2,
                root.expandable_layout_3,
                root.expandable_layout_5
            )

        }

        root.phone_edit_text.addTextChangedListener {
            if (root.phone_edit_text.text.isNotEmpty() && root.phone_edit_text.text.toString() != " ") {
                root.time_layout.isActivated = true
            }
        }

        root.time_layout.setOnClickListener {
            if (root.time_layout.isActivated) {
                root.expandable_layout_5.isExpanded = !root.expandable_layout_5.isExpanded
            }
        }

        root.expandable_layout_5.setOnExpansionUpdateListener { _, _ ->
            layoutStyle(
                root.image5,
                R.drawable.time,
                R.drawable.time_grey,
                root.title5,
                root.sub_title5,
                root.indicator5,
                root.expandable_layout_5,
                root.expandable_layout_1,
                root.expandable_layout_2,
                root.expandable_layout_3,
                root.expandable_layout_4
            )

        }

        root.choose_time.setOnClickListener {
            TimePickerDialog(
                requireActivity(), onTimeSetListener(root.time_text), myCalendar
                    .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        root.choose_date.setOnClickListener{

            DatePickerDialog(requireActivity(), onDateSetListener(root.date),myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show()

        }

        root.confirm_donation.setOnClickListener {
            showDialog()
            v.close.setOnClickListener {
                dialog.dismiss()
            }
            v.confirm.setOnClickListener {
                // donation api
                val b = arguments
                if (b != null){
                    var charity_id = b.getInt("charity_id")
                    var donation_type_id = b.getInt("donation_type_id")
                    var campaign_id = b.getInt("campaign_id")

                    addDonation(charity_id, campaign_id, donation_type_id, phone,district, city, address, time)
                }

            }
        }

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return root
    }

    private fun onTimeSetListener(txt:TextView): TimePickerDialog.OnTimeSetListener {
        val openTime =
            TimePickerDialog.OnTimeSetListener { _, hours, minutes ->
                myCalendar.set(Calendar.HOUR_OF_DAY, hours)
                myCalendar.set(Calendar.MINUTE, minutes)
                val locale = Locale("ar", "SA")

                val myFormat = "h:mm a"
                val sdf = SimpleDateFormat(myFormat, locale)
                txt.text = sdf.format(myCalendar.time)

            }
        return openTime
    }


    private fun onDateSetListener(txt:TextView): DatePickerDialog.OnDateSetListener {
        val date =
            DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->

                val locale = Locale("ar", "SA")
                // EEEE، d MMMM y
                val myFormat = "d MMMM"
                val sdf = SimpleDateFormat(myFormat, locale)
                date = sdf.format(Date(i, i2, i3))
                txt.text = date
            }

        return date
    }



    private fun showDialog() {
        dialog = BottomSheetDialog(this.requireContext())
        v = layoutInflater.inflate(R.layout.bottom_dialog_item_manual, null)

        Picasso.get().load(RetrofitInstance.IMAGE_URL + item_image).into(v.item.campaign_image)
        v.item.campaign_name.text = item_name

        district = selected_type!!
        city = city_edit_text.text.toString()
        address = address_edit_text.text.toString()
        phone = phone_edit_text.text.toString()
        time = "$date  " + time_text.text.toString()

        v.residential_district.text = district
        v.city.text = city
        v.location.text = address
        v.phone_number.text = phone
        v.r_time.text = time

        dialog.setContentView(v)
        dialog.setCanceledOnTouchOutside(false)

//        if (previous_fragment == "campaignDetails"){
//            v.amount_linear.visibility = View.GONE
//            v.amount_view.visibility = View.GONE
//        }

        dialog.show()

    }

    fun layoutStyle(
        image: ImageView,
        trueImageResource: Int,
        falseImageResource: Int,
        title: TextView,
        subTitle: TextView,
        indicator: ImageView,
        layout: ExpandableLayout,
        layout2: ExpandableLayout,
        layout3: ExpandableLayout,
        layout4: ExpandableLayout,
        layout5: ExpandableLayout
    ) {

        if (layout.isExpanded) {
            image.setImageResource(trueImageResource)
            title.setTextColor(resources.getColor(R.color.app_color))
            subTitle.setTextColor(resources.getColor(R.color.black))
            indicator.setImageResource(R.drawable.ic_arrow_down)
            layout2.isExpanded = false
            layout3.isExpanded = false
            layout4.isExpanded = false
            layout5.isExpanded = false
        } else {
            image.setImageResource(falseImageResource)
            title.setTextColor(resources.getColor(R.color.light_grey))
            subTitle.setTextColor(resources.getColor(R.color.light_grey))
            indicator.setImageResource(R.drawable.ic_arrow_up)
        }

    }


    fun addDonation(charity_id:Int, campaign_id:Int, donation_type_id:Int, donor_phone:String, donor_district:String, donor_city:String, donor_address:String, date_time:String) {
        var body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("charity_id", charity_id.toString())
            .addFormDataPart("donation_type_id", donation_type_id.toString())
            .addFormDataPart("donor_phone", donor_phone)
            .addFormDataPart("donor_district", donor_district)
            .addFormDataPart("donor_city", donor_city)
            .addFormDataPart("donor_address", donor_address)
            .addFormDataPart("date_time", date_time)
                if (campaign_id != 0){
                    body.addFormDataPart("campaign_id", campaign_id.toString())
                }
            var bodyRequest = body.build()

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.addDonation(bodyRequest, "Bearer $token")

        response.enqueue(object : Callback<AddDonationJson> {
            override fun onResponse(call: Call<AddDonationJson>, response: Response<AddDonationJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.status) {
                        dialog.dismiss()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, ConfirmationFragment())
                            .commit()

                    }else{
                        Validation().showSnackBar(parent_layout, data.message)
                    }

                } else {
                    Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<AddDonationJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }


}