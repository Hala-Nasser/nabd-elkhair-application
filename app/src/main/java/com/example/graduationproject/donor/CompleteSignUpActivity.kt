package com.example.graduationproject.donor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.Exception


class CompleteSignUpActivity : AppCompatActivity() {
    val PICK_IMAGE_REQUEST = 111
    var imageURI: Uri? = null
    lateinit var image: ImageView
    lateinit var base64String: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_sign_up)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        image = findViewById(R.id.image)



        findViewById<AppCompatButton>(R.id.sign_up).setOnClickListener {
            registerToApp()
            // api لفحص الرجستر و التخزين
            // بدي اخد منها ايدي اليوزر
            // بدي اخزن الايدي بالشيرد بريفيرنس


        }

        choose_image.setOnClickListener {
            PickImageDialog.build(PickSetup())
                .setOnPickResult { r ->
                    imageURI = r.uri
                    image.setImageBitmap(r.bitmap)
                    // uploadImage(r.uri)
                }
                .setOnPickCancel{
                }.show(supportFragmentManager)
        }
//        findViewById<CardView>(R.id.choose_image).setOnClickListener {
//            val intent = Intent()
//            intent.action = Intent.ACTION_PICK
//            intent.type = "image/*"
//            startActivityForResult(intent, PICK_IMAGE_REQUEST)
//            Thread.sleep(1000)
//            image.layoutParams.height = MATCH_PARENT
//            image.layoutParams.width = MATCH_PARENT
//            val param = image.layoutParams as ViewGroup.MarginLayoutParams
//            image.layoutParams = param
//        }

        findViewById<ImageView>(R.id.back).setOnClickListener {
            onBackPressed()
        }

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
//            imageURI = data!!.data
//            Log.e("TAG", imageURI.toString())
//            image.setImageURI(imageURI)
//        }
//    }

    fun registerToApp() {

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
                    File(FileUtil.getPath(imageURI!!, this))
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
                    GeneralChanges().prepareFadeTransition(this@CompleteSignUpActivity, DonorMainActivity())
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    Log.e("errorBody", jsonObj.getString("msg"))
                }

            }
            override fun onFailure(call: Call<DonorJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })

    }

    private fun sendImage(image: Bitmap?){
        val outputStream = ByteArrayOutputStream()
        image!!.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)

         base64String = android.util.Base64.encodeToString(outputStream.toByteArray(),
            android.util.Base64.DEFAULT)

    }
}