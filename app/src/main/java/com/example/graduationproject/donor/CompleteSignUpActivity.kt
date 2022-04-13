package com.example.graduationproject.donor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.example.graduationproject.R
import com.example.graduationproject.api.donor.DonorJson
import com.example.graduationproject.charity.activites.CompleteSignupActivity
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.donor.models.Donor
import com.example.graduationproject.network.ApiRequests
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class CompleteSignUpActivity : AppCompatActivity() {
    val PICK_IMAGE_REQUEST = 111
    var imageURI: Uri? = null
    lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_sign_up)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        image = findViewById(R.id.image)


        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val phone = intent.getStringExtra("phone")
        val address = intent.getStringExtra("address")
        val password = intent.getStringExtra("password")
        val confirm_password = intent.getStringExtra("confirm_password")


        findViewById<AppCompatButton>(R.id.sign_up).setOnClickListener {
          var  donor = Donor(name,email,phone,address,imageURI.toString(),0,password,confirm_password)
          registerToApp(donor,this)

        }

        findViewById<CardView>(R.id.choose_image).setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
            Thread.sleep(1000)
            image.layoutParams.height = MATCH_PARENT
            image.layoutParams.width = MATCH_PARENT
            val param = image.layoutParams as ViewGroup.MarginLayoutParams
            image.layoutParams = param
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageURI = data!!.data
            Log.e("TAG", imageURI.toString())
            image.setImageURI(imageURI)
        }
    }

    fun registerToApp(user: Donor, activity: Activity) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.donorRegister(user)

        response.enqueue(object : Callback<DonorJson> {
            override fun onResponse(call: Call<DonorJson>, response: Response<DonorJson>) {
                if (response.isSuccessful) {
                    Log.e("TAG", response.body().toString())
                    GeneralChanges().prepareFadeTransition(this@CompleteSignUpActivity, DonorMainActivity())

                }

            }

            override fun onFailure(call: Call<DonorJson>, t: Throwable) {
                Log.e("TAG", t.message!!)
            }
        })

    }
}