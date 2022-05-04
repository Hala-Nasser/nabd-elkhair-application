package com.example.graduationproject

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.loader.content.CursorLoader
import com.example.graduationproject.api.donorApi.login.LoginJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.donor.DonorMainActivity
import com.example.graduationproject.models.FileInfo
import com.example.graduationproject.network.ApiRequests
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_image.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.net.URI

class ImageActivity : AppCompatActivity() {

    lateinit var fileService : ApiRequests
    lateinit var btnChooseFile : Button
    lateinit var btnUploadFile : Button
    lateinit var imagePath : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        btnChooseFile = btn_choose_file
        btnUploadFile = btn_upload_file


        btnChooseFile.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

//        btnUploadFile.setOnClickListener {
//            var file = File(imagePath)
//            var requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
//            var body = MultipartBody.Part.createFormData("file", file.name, requestBody)
//            var call = fileService.donorRegister()
//            call!!.enqueue(object : Callback<FileInfo> {
//
//                override fun onResponse(call: Call<FileInfo>, response: Response<FileInfo>) {
//                    if (response.isSuccessful) {
//                        val data = response.body()
//                        Log.e("image uploaded", data.toString())
//
//                    } else {
//                        Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
//                        Log.e("errorBody", response.body().toString())
//                    }
//
//                }
//
//                override fun onFailure(call: Call<FileInfo>, t: Throwable) {
//                    Log.e("on failure upload image", t.message!!)
//
//                }
//            })
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_OK){
            if (data == null){
                Toast.makeText(this, "failed", Toast.LENGTH_LONG).show()
                return
            }

            var imageUri = data.data
            imagePath = getRealPathFromUri(imageUri!!)
        }
    }

    fun getRealPathFromUri(uri: Uri):String{
        var projection = arrayOf(
            MediaStore.Images.Media.DATA
        )
        var loader = CursorLoader(this, uri, projection, null, null, null)

        var cursor = loader.loadInBackground()
        var columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        var result = cursor.getString(columnIndex)
        cursor.close()
        return result

    }
}



