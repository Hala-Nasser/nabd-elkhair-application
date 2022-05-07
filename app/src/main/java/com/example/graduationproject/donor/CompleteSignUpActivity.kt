package com.example.graduationproject.donor

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.loader.content.CursorLoader
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.register.RegisterJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_complete_sign_up.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.RequestBody
import java.io.File
import com.example.graduationproject.classes.FileUtil
import com.example.graduationproject.classes.Validation
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import org.json.JSONObject


class CompleteSignUpActivity : AppCompatActivity() {

    lateinit var image: ImageView
    var imageURI: Uri? = null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_sign_up)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        image = findViewById(R.id.image)


        sign_up.setOnClickListener {

            if (imageURI != null){
                var path = imageURI!!.path
                if (path != null){
                    var file = File((imageURI!!.path)!!)
                    Log.e("file click sign up", "file is: $file")
                    progressDialog = ProgressDialog(this)
                    GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
                    registerToApp()

                }
            }else{
                Validation().showSnackBar(parent_layout, "قم باختيار صورة")
            }

        }


        choose_image.setOnClickListener {
            PickImageDialog.build(PickSetup())
                .setOnPickResult { r ->
                    imageURI = r.uri
                    image.setImageBitmap(r.bitmap)
                }
                .setOnPickCancel{
                }.show(supportFragmentManager)
        }

        back.setOnClickListener {
            onBackPressed()
        }
    }


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
                    "image", File(FileUtil.getPath(imageURI!!, this)).extension ,
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

            response.enqueue(object : Callback<RegisterJson> {
                override fun onResponse(call: Call<RegisterJson>, response: Response<RegisterJson>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        Log.e("registerToApp", data.toString())

                        if (data!!.status) {

                            val user_id = data.data.id
                            val sharedPref = this@CompleteSignUpActivity.getSharedPreferences(
                                "sharedPref", Context.MODE_PRIVATE)

                            val editor = sharedPref.edit()
                            editor.putInt("user_id", user_id)
                            editor.putString("user_image", data.data.image)
                            Log.e("id in signup", user_id.toString())
                            editor.apply()

                            GeneralChanges().hideDialog(progressDialog!!)
                            GeneralChanges().prepareFadeTransition(
                                this@CompleteSignUpActivity,
                                DonorMainActivity()
                            )

                        }else{
                            GeneralChanges().hideDialog(progressDialog!!)
                            Validation().showSnackBar(findViewById(R.id.parent_layout), data.message)
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

}