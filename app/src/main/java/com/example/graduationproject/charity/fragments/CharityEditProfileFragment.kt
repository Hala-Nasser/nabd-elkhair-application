package com.example.graduationproject.charity.fragments

import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.charityApi.fcm.FCMJson
import com.example.graduationproject.api.donorApi.donationType.DonationTypeJson
import com.example.graduationproject.classes.FileUtil
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.fragment_charity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_charity_edit_profile.rv_complete_signup_donation_type
import kotlinx.android.synthetic.main.fragment_charity_edit_profile.time_from
import kotlinx.android.synthetic.main.fragment_charity_edit_profile.time_to
import kotlinx.android.synthetic.main.fragment_charity_edit_profile.view.*
import kotlinx.android.synthetic.main.fragment_donation_received.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import kotlin.collections.ArrayList


class CharityEditProfileFragment : Fragment() {
    var isAllFieldsChecked = false
    var imageURI: Uri? = null
    var progressDialog: ProgressDialog? = null
    var token = ""
    val myCalendar: Calendar = Calendar.getInstance()
    lateinit var user_image: String
    private lateinit var donationTypeAdapter: DonationTypeAdapter

    companion object {
        var donationTypes = listOf<Int>()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_edit_profile, container, false)
        requireActivity().charity_nav_bottom.visibility = View.GONE

        val sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("charity_token", "")!!
        progressDialog = ProgressDialog(activity)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
        profile()


        donationTypeAdapter =
            DonationTypeAdapter(requireActivity(), null, "CompleteSignup")

        root.charity_save.setOnClickListener {
            var user_name = root.charity_username.text.toString()
            var user_phone = root.charity_phone.text.toString()
            var user_address = root.charity_location.text.toString()
            var charity_email = root.charity_email.text.toString()
            var charity_about = root.charity_about.text.toString()
            val openTime = "${root.time_from.text}" + "-" + "${root.time_to.text}"

            isAllFieldsChecked = CheckAllFields()

            if (isAllFieldsChecked) {

                progressDialog = ProgressDialog(activity)
                GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
                updateProfile(
                    charity_email,
                    user_name,
                    user_phone,
                    user_address,
                    openTime,
                    charity_about
                )

            }

        }

        root.choose_open_time.setOnClickListener {
            TimePickerDialog(
                requireContext(), onTimeSetListener(root.time_from), myCalendar
                    .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),
                true
            ).show()

        }

        root.choose_close_time.setOnClickListener {
            TimePickerDialog(
                requireContext(), onTimeSetListener(root.time_to), myCalendar
                    .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        root.charity_eProfile_image.setOnClickListener {
            PickImageDialog.build(PickSetup())
                .setOnPickResult { r ->
                    imageURI = r.uri
                    charity_eProfile_image.setImageBitmap(r.bitmap)
                }
                .setOnPickCancel {
                }.show(requireActivity().supportFragmentManager)
        }

        root.charity_back_settings_icon.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return root
    }

    private fun CheckAllFields(): Boolean {
        if (!Validation().validateUsername(charity_username, charity_username_layout)) return false

        if (!Validation().validatePhoneNumber(charity_phone, charity_phone_layout)) return false
        if (!Validation().validateEmail(charity_email, charity_email_layout)) return false

        if (!Validation().validateAboutCharity(charity_about, charity_about_layout)) return false

        if (!Validation().validateLocation(charity_location, charity_location_layout)) return false
        return true
    }

    private fun onTimeSetListener(txt: TextView): TimePickerDialog.OnTimeSetListener {
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

    fun profile() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityProfile("Bearer $token")

        response.enqueue(object : Callback<FCMJson> {
            override fun onResponse(call: Call<FCMJson>, response: Response<FCMJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data
                    Picasso.get().load(RetrofitInstance.IMAGE_URL + data.image)
                        .into(charity_eProfile_image)
                    charity_username.setText(data.name)
                    charity_email.setText(data.email)
                    charity_email.setText(data.email)
                    charity_phone.setText(data.phone.toString())
                    charity_about.setText(data.about)
                    charity_location.setText(data.address)
                    val lstValues = ArrayList<String>()
                    var strSep = data.open_time.split("-")
                    for (element in strSep) {
                        lstValues.add(element)
                    }
                    time_from.text = lstValues[0]
                    time_to.text = lstValues[1]

                    donationTypes = data.donationTypes
                    user_image = data.image
                    Log.e("profile m", donationTypes.toString())
                    getDonationType()
                    GeneralChanges().hideDialog(progressDialog!!)
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<FCMJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }

    fun getDonationType() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getGeneralDonationType()

        response.enqueue(object : Callback<DonationTypeJson> {
            override fun onResponse(
                call: Call<DonationTypeJson>,
                response: Response<DonationTypeJson>
            ) {
                if (response.isSuccessful) {

                    val data = response.body()

                    if (data!!.status) {
                        Log.e("data", data.data.toString())
                        rv_complete_signup_donation_type.layoutManager =
                            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                        rv_complete_signup_donation_type.setHasFixedSize(true)
                        donationTypeAdapter =
                            DonationTypeAdapter(requireActivity(), data.data, "EditProfile")
                        rv_complete_signup_donation_type.adapter = donationTypeAdapter
                    } else {
                        Log.e("is successful", "status false")
                    }

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<DonationTypeJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

    fun updateProfile(
        email: String,
        name: String,
        phone: String,
        location: String,
        openTime: String,
        about: String
    ) {

        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("name", name)
            .addFormDataPart("email", email)
            .addFormDataPart("phone", phone)
            .addFormDataPart("address", location)
            .addFormDataPart("about", about)
            .addFormDataPart("open_time", openTime)
            .addFormDataPart("activation_status", "0")
        for (i in donationTypeAdapter.list) {
            body.addFormDataPart("donationTypes[]", i.toString())
        }


        if (imageURI != null) {
            Log.e("imgUrl", imageURI.toString())
            body.addFormDataPart(
                "image", File(FileUtil.getPath(imageURI!!, requireContext())).extension,
                RequestBody.create(
                    "application/octet-stream".toMediaTypeOrNull(),
                    File(FileUtil.getPath(imageURI!!, requireContext()))
                )
            )
        }

        var requestBody = body.build()
        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityUpdateProfile("Bearer $token", requestBody)

        response.enqueue(object : Callback<FCMJson> {
            override fun onResponse(call: Call<FCMJson>, response: Response<FCMJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.status) {

                        val sharedPref = requireActivity().getSharedPreferences(
                            "sharedPref",
                            Context.MODE_PRIVATE
                        )
                        val editor = sharedPref.edit()
                        editor.putString("about", data.data.about)
                        editor.apply()
                        GeneralChanges().hideDialog(progressDialog!!)
                        requireActivity().supportFragmentManager.beginTransaction().replace(
                            R.id.charityContainer,
                            ProfileFragment()
                        ).commit()

                    } else {
                        GeneralChanges().hideDialog(progressDialog!!)
                        Validation().showSnackBar(eProfile_parent_layout, data.message)
                    }

                } else {
                    GeneralChanges().hideDialog(progressDialog!!)
                    Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<FCMJson>, t: Throwable) {
                GeneralChanges().hideDialog(progressDialog!!)
                Log.e("failure", t.message!!)
            }
        })
    }

}