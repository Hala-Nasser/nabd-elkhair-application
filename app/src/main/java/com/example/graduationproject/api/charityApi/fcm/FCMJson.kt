package com.example.graduationproject.api.charityApi.fcm

import com.example.graduationproject.models.Charity

data class FCMJson(
    val `data`: Charity,
    val message: String,
    val status: Boolean
)