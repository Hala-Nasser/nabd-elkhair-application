package com.example.graduationproject.donor

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
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
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import org.json.JSONObject


class CompleteSignUpActivity : AppCompatActivity() {

    lateinit var image: ImageView
//    private var progressDialog: ProgressDialog?=null
//    private var fileURI: Uri? = null
//    private val PICK_IMAGE_REQUEST = 111
    var imageURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_sign_up)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        image = findViewById(R.id.image)


        sign_up.setOnClickListener {
            Log.e("image click sign up", "image is: $imageURI")

            if (imageURI != null){
                var path = imageURI!!.path
                if (path != null){
                    var file = File((imageURI!!.path)!!)
                    Log.e("file click sign up", "file is: $file")
                    registerToApp()

                }
            }


        }

//        choose_image.setOnClickListener {
//            val intent = Intent()
//            intent.action = Intent.ACTION_PICK
//            intent.type = "image/*"
//            startActivityForResult(intent, PICK_IMAGE_REQUEST)
//        }

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

        back.setOnClickListener {
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
                            Log.e("id in signup", user_id.toString())
                            editor.apply()

                            GeneralChanges().prepareFadeTransition(
                                this@CompleteSignUpActivity,
                                DonorMainActivity()
                            )

                        }

                    } else {
                        Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
//                        Log.e("errorBody", response.body().toString())
                    }

                }

                override fun onFailure(call: Call<RegisterJson>, t: Throwable) {
                    Log.e("failure", t.message!!)
                }
            })
        }

}