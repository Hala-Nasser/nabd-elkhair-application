package com.example.graduationproject.api.fcm

import com.google.gson.annotations.SerializedName

data class FCM(

    @SerializedName("user_id")
    val user_id: Int,

    @SerializedName("fcm")
    val fcm: String
)