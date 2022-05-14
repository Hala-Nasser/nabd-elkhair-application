package com.example.graduationproject.charity.activites

import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.charityApi.register.RegisterJson
import com.example.graduationproject.api.donorApi.donationType.DonationTypeJson
import com.example.graduationproject.classes.FileUtil
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.network.RetrofitInstance
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*
import kotlinx.android.synthetic.main.activity_complete_sign_up.*
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class CompleteSignupActivity : AppCompatActivity(){
      private lateinit var  donationTypeAdapter: DonationTypeAdapter
    var imageURI: Uri? = null
    var progressDialog: ProgressDialog? = null
    val myCalendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charity_complete_signup)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        donationTypeAdapter =
            DonationTypeAdapter(this@CompleteSignupActivity, null,"CompleteSignup")

        getDonationType()


        charity_sign_up.setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, PaymentsMethodActivity())
        }


        charity_sign_up.setOnClickListener {

            if (imageURI != null){
                    progressDialog = ProgressDialog(this)
                    GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
                    registerToApp()
            }else{
                Validation().showSnackBar(charity_parent_layout, "قم باختيار صورة")
            }

        }


        choose_open_time.setOnClickListener{
            TimePickerDialog(
                this, onTimeSetListener(time_from), myCalendar
                    .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),
                true
            ).show()

        }

        choose_close_time.setOnClickListener{
            TimePickerDialog(
                this, onTimeSetListener(time_to), myCalendar
                    .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),
                true
            ).show()

        }


        choose_charity_image.setOnClickListener {
            PickImageDialog.build(PickSetup())
                .setOnPickResult { r ->
                    imageURI = r.uri
                    charity_image.setImageBitmap(r.bitmap)
                }
                .setOnPickCancel{
                }.show(supportFragmentManager)
        }

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


    fun registerToApp() {

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val phone = intent.getStringExtra("phone")
        val address = intent.getStringExtra("address")
        val password = intent.getStringExtra("password")
        val confirm_password = intent.getStringExtra("confirm_password")
        val about = et_charity_about.text.toString()
        val openTime = "${time_from.text}"+"-"+"${time_to.text}"

        val requestName = RequestBody.create(
            MediaType.parse("multipart/form-data"), name
        )
        val requestEmail = RequestBody.create(
            MediaType.parse("multipart/form-data"), email
        )
        val requestPassword = RequestBody.create(
            MediaType.parse("multipart/form-data"), password
        )
        val requestPhone = RequestBody.create(
            MediaType.parse("multipart/form-data"), phone
        )
        val requestAddress = RequestBody.create(
            MediaType.parse("multipart/form-data"), address
        )
        val requestAbout = RequestBody.create(
            MediaType.parse("multipart/form-data"), about
        )
        val requestOpenTime = RequestBody.create(
            MediaType.parse("multipart/form-data"), openTime
        )
        val requestActivation = RequestBody.create(
            MediaType.parse("multipart/form-data"), "0"
        )

        val file =File(FileUtil.getPath(imageURI!!, this))
        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)

       var image =
            MultipartBody.Part.createFormData("image", file.extension, requestFile)
//        val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
//            .addFormDataPart("name", name)
//            .addFormDataPart("email", email)
//            .addFormDataPart("password", password)
//            .addFormDataPart("phone", phone)
//            .addFormDataPart("address", address)
//            .addFormDataPart("about", about)
//            .addFormDataPart("open_time", openTime)
//            .addFormDataPart(
//                "image", File(FileUtil.getPath(imageURI!!, this)).extension ,
//                RequestBody.create(
//                    MediaType.parse("application/octet-stream"),
//                    File(FileUtil.getPath(imageURI!!, this))
//                )
//            )
//            .addFormDataPart("activation_status", "0")
//            .addFormDataPart("c_password", confirm_password)
//            .addFormDataPart("donationTypes[]", confirm_password)
//            .build()


        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityRegister(requestName,requestEmail,requestPassword,requestPhone,requestAddress,requestAbout,
        requestOpenTime,image,
            requestActivation,donationTypeAdapter.list)

        response.enqueue(object : Callback<RegisterJson> {
            override fun onResponse(call: Call<RegisterJson>, response: Response<RegisterJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("registerToApp", data.toString())

                    if (data!!.status) {

                        val user_id = data.data.id
                        val sharedPref = this@CompleteSignupActivity.getSharedPreferences(
                            "sharedPref", Context.MODE_PRIVATE)

                        val editor = sharedPref.edit()
                        editor.putString("from", "charity")
                        editor.putInt("charity_id", user_id)
                        Log.e("id in signup", user_id.toString())
                        editor.apply()

                        GeneralChanges().hideDialog(progressDialog!!)
                        GeneralChanges().prepareFadeTransition(
                            this@CompleteSignupActivity,
                            CharityMainActivity()
                        )

                    }else{
                        GeneralChanges().hideDialog(progressDialog!!)
                        Validation().showSnackBar(findViewById(R.id.charity_parent_layout), data.message)
                    }

                } else {
                    GeneralChanges().hideDialog(progressDialog!!)
                    Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<RegisterJson>, t: Throwable) {
                GeneralChanges().hideDialog(progressDialog!!)
                Log.e("failure", t.message!!)
            }
        })
    }

    fun getDonationType() {

        Log.e("complete sign up", "enter")

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getGeneralDonationType()

        response.enqueue(object : Callback<DonationTypeJson> {
            override fun onResponse(call: Call<DonationTypeJson>, response: Response<DonationTypeJson>) {
                if (response.isSuccessful) {

                    val data = response.body()
                    //val data = response.body()!!.data

                    if (data!!.status){
                        Log.e("data", data.data.toString())
                        rv_complete_signup_donation_type.layoutManager = LinearLayoutManager(this@CompleteSignupActivity, RecyclerView.HORIZONTAL,false)
                        rv_complete_signup_donation_type.setHasFixedSize(true)
                        donationTypeAdapter =
                            DonationTypeAdapter(this@CompleteSignupActivity, data.data,"CompleteSignup")
                        rv_complete_signup_donation_type.adapter = donationTypeAdapter
                    }else{
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

}