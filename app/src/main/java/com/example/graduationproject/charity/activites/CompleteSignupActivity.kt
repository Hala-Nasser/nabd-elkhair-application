package com.example.graduationproject.charity.activites

import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.charityApi.login.LoginJson
import com.example.graduationproject.api.charityApi.register.RegisterJson
import com.example.graduationproject.api.donorApi.donationType.DonationTypeJson
import com.example.graduationproject.classes.FileUtil
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.donor.SignInActivity
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.network.RetrofitInstance
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
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

class CompleteSignupActivity : AppCompatActivity() {
    private lateinit var donationTypeAdapter: DonationTypeAdapter
    var imageURI: Uri? = null
    var progressDialog: ProgressDialog? = null
    val myCalendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charity_complete_signup)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        donationTypeAdapter =
            DonationTypeAdapter(this@CompleteSignupActivity, null, "CompleteSignup")

        getDonationType()


        charity_sign_up.setOnClickListener {
            GeneralChanges().prepareFadeTransition(this, PaymentsMethodActivity())
        }


        charity_sign_up.setOnClickListener {
            if (Validation().validateAboutCharity(
                    et_charity_about,
                    about_parent
                )
            ) {
            if (imageURI != null) {
                progressDialog = ProgressDialog(this)
                GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
                registerToApp()
            } else {
                Validation().showSnackBar(charity_parent_layout, "يرجى اختيار الصورة الشخصية")
            }
        }
        }


        choose_open_time.setOnClickListener {
            TimePickerDialog(
                this, onTimeSetListener(time_from), myCalendar
                    .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),
                true
            ).show()

        }

        choose_close_time.setOnClickListener {
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
                    charity_image.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    charity_image.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                }
                .setOnPickCancel {
                }.show(supportFragmentManager)
        }

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


    private fun registerToApp() {
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val phone = intent.getStringExtra("phone")
        val address = intent.getStringExtra("address")
        val password = intent.getStringExtra("password")
        val about = et_charity_about.text.toString()
        val openTime = "${time_from.text}" + "-" + "${time_to.text}"

        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("name", name!!)
            .addFormDataPart("email", email!!)
            .addFormDataPart("phone", phone!!)
            .addFormDataPart("password", password!!)
            .addFormDataPart("address", address!!)
            .addFormDataPart("about", about)
            .addFormDataPart(
                "image", File(FileUtil.getPath(imageURI!!, this)!!).extension,
                RequestBody.create(
                    "application/octet-stream".toMediaTypeOrNull(),
                    File(FileUtil.getPath(imageURI!!, this)!!)
                )
            )
            .addFormDataPart("open_time", openTime)
            .addFormDataPart("activation_status", "0")
        for (i in donationTypeAdapter.list) {
            body.addFormDataPart("donationTypes[]", i.toString())
        }
        var requestBody = body.build()
        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityRegister(requestBody)

        response.enqueue(object : Callback<RegisterJson> {
            override fun onResponse(call: Call<RegisterJson>, response: Response<RegisterJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("registerToApp", data.toString())

                    if (data!!.status) {

                        val user_id = data.data.id
                        val sharedPref = this@CompleteSignupActivity.getSharedPreferences(
                            "sharedPref", Context.MODE_PRIVATE
                        )

                        val editor = sharedPref.edit()
                        editor.putString("from", "charity")
                        editor.putInt("charity_id", user_id)
                        editor.apply()
                        GeneralChanges().prepareFadeTransition(
                            this@CompleteSignupActivity,
                            PaymentsMethodActivity()
                        )


                    } else {
                        Validation().showSnackBar(
                            findViewById(R.id.charity_parent_layout),
                            data.message
                        )
                        GeneralChanges().hideDialog(progressDialog!!)
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
                        rv_complete_signup_donation_type.layoutManager = LinearLayoutManager(
                            this@CompleteSignupActivity,
                            RecyclerView.HORIZONTAL,
                            false
                        )
                        rv_complete_signup_donation_type.setHasFixedSize(true)
                        donationTypeAdapter =
                            DonationTypeAdapter(
                                this@CompleteSignupActivity,
                                data.data,
                                "CompleteSignup"
                            )
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
    fun charityLoginToApp(user_email:String,user_password:String) {

        val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("email", user_email)
            .addFormDataPart("password", user_password)
            .build()

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityLogIn(body)

        response.enqueue(object : Callback<LoginJson> {
            override fun onResponse(
                call: Call<LoginJson>,
                response: Response<LoginJson>
            ) {

                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("Charity", data.toString())
                    val sharedPref = this@CompleteSignupActivity.getSharedPreferences(
                        "sharedPref", Context.MODE_PRIVATE
                    )
                    val editor = sharedPref.edit()
                    if (data!!.status) {

                        val user_id = data.data.id

                        editor.putString("from", "charity")
                        editor.putInt("charity_id", user_id)
                        editor.putString("charity_image", data.data.image)
                        editor.putString("charity_token", data.data.token)
                        Log.e("Charity id in signin", user_id.toString())
                        editor.apply()
                        GeneralChanges().hideDialog(progressDialog!!)

                            GeneralChanges().prepareFadeTransition(
                                this@CompleteSignupActivity,
                                CharityMainActivity()
                            )

                    } else {
                        GeneralChanges().hideDialog(progressDialog!!)
                        if (data.message == "تعذر تسجيل الدخول بسبب تعطيل حسابك"){

                        }else{
                            Validation().showSnackBar(findViewById(R.id.charity_parent_layout), data.message)
                        }

                    }

                } else {
                    GeneralChanges().hideDialog(progressDialog!!)
                    Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
                    Log.e("errorBody", response.body().toString())
                }

            }

            override fun onFailure(call: Call<LoginJson>, t: Throwable) {
                Log.e("on failure sign in", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })

    }

}