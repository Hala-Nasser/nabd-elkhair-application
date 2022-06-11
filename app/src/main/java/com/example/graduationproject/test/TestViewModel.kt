package com.example.graduationproject.test

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.charityApi.campaign.CampaignJson
import com.example.graduationproject.api.donorApi.addDonation.AddDonationJson
import com.example.graduationproject.api.donorApi.charities.CharitiesJson
import com.example.graduationproject.api.donorApi.login.LoginJson
import com.example.graduationproject.charity.fragments.HomeFragment
import com.example.graduationproject.classes.FileUtil
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.donor.adapters.CharitiesAdapter
import com.example.graduationproject.donor.fragments.ConfirmationFragment
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.fragment_add_campaign.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Response.error
import java.io.File


class TestViewModel : ViewModel() {

    private val result = MutableLiveData<Resource<LoginJson>?>()
    private val addDonationResult = MutableLiveData<Resource<AddDonationJson>?>()
    private val addCampaignResult = MutableLiveData<Resource<CampaignJson>?>()
    private val addCharityResult = MutableLiveData<Resource<CharitiesJson>?>()


    fun fetchSignin(user_email: String, user_password: String) {
        val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("email", user_email)
            .addFormDataPart("password", user_password)
            .build()

        val apiHelper =
            RetrofitInstance.create()
        apiHelper.logIn(body)
            .enqueue(object : Callback<LoginJson> {
                override fun onFailure(call: Call<LoginJson>, t: Throwable) {
                    result.postValue(Resource.error("Something went wrong!", null))
                }

                override fun onResponse(call: Call<LoginJson>, response: Response<LoginJson>) {
                    result.postValue(Resource.success(response.body()))
                }
            })
    }


    fun getDetail(): LiveData<Resource<LoginJson>?> {
        return result
    }


    fun AddDonation(
        activity: Activity,
        charity_id: Int,
        campaign_id: Int,
        donation_type_id: Int,
        donor_phone: String,
        donor_district: String,
        donor_city: String,
        donor_address: String,
        date_time: String
    ) {
        var body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("charity_id", charity_id.toString())
            .addFormDataPart("donation_type_id", donation_type_id.toString())
            .addFormDataPart("donor_phone", donor_phone)
            .addFormDataPart("donor_district", donor_district)
            .addFormDataPart("donor_city", donor_city)
            .addFormDataPart("donor_address", donor_address)
            .addFormDataPart("date_time", date_time)

        if (campaign_id != 0) {
            body.addFormDataPart("campaign_id", campaign_id.toString())
        }
        var bodyRequest = body.build()

        val sharedPref = activity.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val token = sharedPref.getString("user_token", "")!!


        val retrofitInstance =
            RetrofitInstance.create()
        retrofitInstance.addDonation(
            bodyRequest,
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiYmMwNDM3YTM4NzdiNWY1ODg4ZjI2YjEwODc1NTcyNDQ4ZGMyODc3NmUxNThlZmUxOTUzM2M5NjAzNDIwY2QyMzZlODg1MjdiNDMyMjQ4YjciLCJpYXQiOjE2NTQ2ODk1MjYuNzg0MzU3LCJuYmYiOjE2NTQ2ODk1MjYuNzg0MzY1LCJleHAiOjE2ODYyMjU1MjYuNzE2NjUsInN1YiI6IjEiLCJzY29wZXMiOlsiZG9ub3IiXX0.f8GrYIX22p6ExG6Y_2nXhtmVMIsqkughPtjQ8eM_QJ_xUiH8UaNqWh_niTA6HNrbBS72p__nGdWTukjUDUAp4U8FcMnb82BhpXKkRdVGHT9wiZ4nnCQjKs4IYri50eiHH6v3MMweDxbfQ-eYT7kPjSDPjhwyiw4-vyBjAzsvAHl4cfK6dQobcXhbAyTNNaHg8-CxNnKoOQct9yRO5gfPmPI-VIW2ssgTGhh-o7jPJdRwMNqQVOYZNZQZGmwVVEz2PrPaMUct2jty_FDnrBLaHV50Bmrxi4g9IlUbjB_37tmqomUHG6J1QFgec1W_cGQhj-P33sij8O4HUUKJkIJ9NGPGDsbWzzCO_pgMJIVHC-EDTKpywd0WLM4ImOlQf-gj1ElSQX7v_bKE_f80mcQsfO4h9POhw1jBgtdZxPtBL1Wc-lGSsiU8_mLgYeAzK5Mui4vmZEgqaIYY0JdGpAkVpTitNm59JgKHadybIdD_QoDqGAKgLSUHcax2UpqNnsOBl5R5cdDQAaWdwja3Ey0VG9qKm4ck7fCZhq1IHQlkOnXVK7VZOZEzezt4aCvTX545w5Kqx428-COHkNL4fOZrkgE_Z3sIJpNHKsAh6PNd2ImQNQHEV32BSbsAUBs9ql0FRVTTIj__geqGVF9WEYsMwtbUNZa1vp0mLfyjZMUqCI8"
        )

            .enqueue(object : Callback<AddDonationJson> {
                override fun onFailure(call: Call<AddDonationJson>, t: Throwable) {
                    addDonationResult.postValue(Resource.error("Something went wrong!", null))
                }

                override fun onResponse(
                    call: Call<AddDonationJson>,
                    response: Response<AddDonationJson>
                ) {
                    addDonationResult.postValue(Resource.success(response.body()))
                }
            })
    }

    fun getDonationDetail(): LiveData<Resource<AddDonationJson>?> {
        return addDonationResult
    }


    fun AddCampaign(
        activity: Activity,
        name: String,
        desc: String,
        date: String,
        time: String,
        donationType: String,
        imageURI: Uri
    ) {

        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("name", name)
            .addFormDataPart("description", desc)
            .addFormDataPart("expiry_date", date)
            .addFormDataPart("expiry_time", time)
            .addFormDataPart("donation_type_id", donationType)
            .addFormDataPart(
                "image", File(FileUtil.getPath(imageURI!!, activity)).extension,
                RequestBody.create(
                    "application/octet-stream".toMediaTypeOrNull(),
                    File(FileUtil.getPath(imageURI!!, activity))
                )
            )
            .build()

        val sharedPref = activity.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val token = sharedPref.getString("user_token", "")!!


        val retrofitInstance =
            RetrofitInstance.create()
        retrofitInstance.addCampaign(
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiYmMwNDM3YTM4NzdiNWY1ODg4ZjI2YjEwODc1NTcyNDQ4ZGMyODc3NmUxNThlZmUxOTUzM2M5NjAzNDIwY2QyMzZlODg1MjdiNDMyMjQ4YjciLCJpYXQiOjE2NTQ2ODk1MjYuNzg0MzU3LCJuYmYiOjE2NTQ2ODk1MjYuNzg0MzY1LCJleHAiOjE2ODYyMjU1MjYuNzE2NjUsInN1YiI6IjEiLCJzY29wZXMiOlsiZG9ub3IiXX0.f8GrYIX22p6ExG6Y_2nXhtmVMIsqkughPtjQ8eM_QJ_xUiH8UaNqWh_niTA6HNrbBS72p__nGdWTukjUDUAp4U8FcMnb82BhpXKkRdVGHT9wiZ4nnCQjKs4IYri50eiHH6v3MMweDxbfQ-eYT7kPjSDPjhwyiw4-vyBjAzsvAHl4cfK6dQobcXhbAyTNNaHg8-CxNnKoOQct9yRO5gfPmPI-VIW2ssgTGhh-o7jPJdRwMNqQVOYZNZQZGmwVVEz2PrPaMUct2jty_FDnrBLaHV50Bmrxi4g9IlUbjB_37tmqomUHG6J1QFgec1W_cGQhj-P33sij8O4HUUKJkIJ9NGPGDsbWzzCO_pgMJIVHC-EDTKpywd0WLM4ImOlQf-gj1ElSQX7v_bKE_f80mcQsfO4h9POhw1jBgtdZxPtBL1Wc-lGSsiU8_mLgYeAzK5Mui4vmZEgqaIYY0JdGpAkVpTitNm59JgKHadybIdD_QoDqGAKgLSUHcax2UpqNnsOBl5R5cdDQAaWdwja3Ey0VG9qKm4ck7fCZhq1IHQlkOnXVK7VZOZEzezt4aCvTX545w5Kqx428-COHkNL4fOZrkgE_Z3sIJpNHKsAh6PNd2ImQNQHEV32BSbsAUBs9ql0FRVTTIj__geqGVF9WEYsMwtbUNZa1vp0mLfyjZMUqCI8",
            body
        )

            .enqueue(object : Callback<CampaignJson> {
                override fun onFailure(call: Call<CampaignJson>, t: Throwable) {
                    addCampaignResult.postValue(Resource.error("Something went wrong!", null))
                }

                override fun onResponse(
                    call: Call<CampaignJson>,
                    response: Response<CampaignJson>
                ) {
                    addCampaignResult.postValue(Resource.success(response.body()))
                }
            })
    }

    fun getCampaignDetail(): LiveData<Resource<CampaignJson>?> {
        return addCampaignResult
    }


    fun getCharities() {

        val apiHelper =
            RetrofitInstance.create()
        apiHelper.getCharities()
            .enqueue(object : Callback<CharitiesJson> {
                override fun onFailure(call: Call<CharitiesJson>, t: Throwable) {
                    addCharityResult.postValue(Resource.error("Something went wrong!", null))
                }

                override fun onResponse(
                    call: Call<CharitiesJson>,
                    response: Response<CharitiesJson>
                ) {
                    addCharityResult.postValue(Resource.success(response.body()))
                }
            })
    }

    fun getCharityDetail(): LiveData<Resource<CharitiesJson>?> {
        return addCharityResult
    }
}