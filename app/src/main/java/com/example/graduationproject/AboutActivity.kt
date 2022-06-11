package com.example.graduationproject

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.graduationproject.api.donorApi.staticPages.StaticPagesJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.fragment_capmaigns_according_to_donation_type.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutActivity : AppCompatActivity() {
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneralChanges().setStatusBarTransparent(this)
        setContentView(R.layout.activity_about)

        progressDialog = ProgressDialog(this)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
        getStaticPage(1)

        back.setOnClickListener {
            onBackPressed()
        }
    }


    fun getStaticPage(id: Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getStaticPages(id)

        response.enqueue(object : Callback<StaticPagesJson> {
            override fun onResponse(
                call: Call<StaticPagesJson>,
                response: Response<StaticPagesJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data
                    content.text = data.content
                    GeneralChanges().hideDialog(progressDialog!!)
                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }
            }

            override fun onFailure(call: Call<StaticPagesJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }


}