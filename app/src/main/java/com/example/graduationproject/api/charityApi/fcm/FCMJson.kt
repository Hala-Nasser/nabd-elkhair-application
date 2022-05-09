package com.example.graduationproject.api.charityApi.fcm

data class FCMJson(
    val `data`: Data,
    val message: String,
    val status: Boolean
)