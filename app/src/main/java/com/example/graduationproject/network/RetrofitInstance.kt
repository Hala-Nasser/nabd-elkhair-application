package com.example.graduationproject.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        val BASE_URL = "http://192.168.1.137:80/api/"
        val IMAGE_URL = "http://192.168.1.137:80/storage/uploads/images/"

        var gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        fun create() : ApiRequests {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiRequests::class.java)

        }

    }
}