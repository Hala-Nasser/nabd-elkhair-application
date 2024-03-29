package com.example.graduationproject.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    // 192.168.43.120
    //192.168.1.137
    companion object {
        val BASE_URL = "http://192.168.1.18:80/api/"
        val IMAGE_URL = "http://192.168.1.18:80/storage/uploads/images/"

        fun create(): ApiRequests {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiRequests::class.java)

        }

    }
}