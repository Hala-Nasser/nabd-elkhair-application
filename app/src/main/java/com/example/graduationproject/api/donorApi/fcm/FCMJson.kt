package com.example.graduationproject.api.donorApi.fcm

data class FCMJson(
    val `data`: Data,
    val message: String,
    val status: Boolean
)