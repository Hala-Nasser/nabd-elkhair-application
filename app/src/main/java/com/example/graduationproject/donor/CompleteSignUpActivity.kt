package com.example.graduationproject.donor

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
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
//import com.vansuita.pickimage.bundle.PickSetup
//import com.vansuita.pickimage.dialog.PickImageDialog
import kotlinx.android.synthetic.main.activity_complete_sign_up.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import com.example.graduationproject.classes.FileUtil
import com.example.graduationproject.classes.PathUtil
import com.example.graduationproject.classes.UriPathHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import org.json.JSONObject
import java.lang.Exception


class CompleteSignUpActivity : AppCompatActivity() {

    lateinit var image: ImageView
    lateinit var base64String: String

    private var progressDialog: ProgressDialog?=null
    private var fileURI: Uri? = null
    private val PICK_IMAGE_REQUEST = 111
    var imageURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_sign_up)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        image = findViewById(R.id.image)



        findViewById<AppCompatButton>(R.id.sign_up).setOnClickListener {

            Log.e("image click sign up", "image is: $imageURI")

            if (imageURI != null){
                var path = imageURI!!.path
                if (path != null){
                    var file = File((imageURI!!.path)!!)
                    Log.e("file click sign up", "file is: $file")
                    registerToApp(file)

                }
            }


        }

        choose_image.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        findViewById<ImageView>(R.id.back).setOnClickListener {
            onBackPressed()
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

    fun registerToApp(file: File) {

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val phone = intent.getStringExtra("phone")
        val address = intent.getStringExtra("address")
        val password = intent.getStringExtra("password")
        val confirm_password = intent.getStringExtra("confirm_password")

            val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("name", name)
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
                .addFormDataPart("phone", phone)
                .addFormDataPart("location", address)
                .addFormDataPart(
                    "image", "donor images",
                    RequestBody.create(
                        MediaType.parse("application/octet-stream"),
                        file
                    )
                )
                .addFormDataPart("activation_status", "0")
                .addFormDataPart("c_password", confirm_password)
                .build()


            val retrofitInstance =
                RetrofitInstance.create()
            val response = retrofitInstance.donorRegister(body)

            response.enqueue(object : Callback<DonorJson> {
                override fun onResponse(call: Call<DonorJson>, response: Response<DonorJson>) {
                    if (response.isSuccessful) {
                        Log.e("registerToApp", response.body()!!.toString())
                        // api لفحص الرجستر و التخزين
                        // بدي اخد منها ايدي اليوزر
                        // بدي اخزن الايدي بالشيرد بريفيرنس
                        val user_id = response.body()!!.id
                        val sharedPref = this@CompleteSignUpActivity.getSharedPreferences(
                            "sharedPref", Context.MODE_PRIVATE)

                        val editor = sharedPref.edit()
                        editor.putInt("user_id", user_id)
                        editor.apply()

                        GeneralChanges().prepareFadeTransition(
                            this@CompleteSignUpActivity,
                            DonorMainActivity()
                        )
                    } else {
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        Log.e("errorBody", jsonObj.getString("msg"))
                    }

                }

                override fun onFailure(call: Call<DonorJson>, t: Throwable) {
                    Log.e("failure", t.message!!)
                }
            })
        }


}